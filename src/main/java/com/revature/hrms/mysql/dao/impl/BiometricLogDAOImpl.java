package com.revature.hrms.mysql.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.hrms.mysql.dao.BiometricLogDAO;
import com.revature.hrms.mysql.dto.UserReport;
import com.revature.hrms.mysql.model.BiometricLog;

import lombok.Data;

@Data
@Repository
@Transactional()
public class BiometricLogDAOImpl implements BiometricLogDAO {

  @Autowired
  @Qualifier("mysqlSessionFactory")
  SessionFactory mysqlSessionFactory;

  private Session getCurrentSession() {
    return getMysqlSessionFactory().getCurrentSession();
  }

  @Override
  public BiometricLog getLatestBiometricLog() {
    String query = "from BiometricLog order by entryTimestamp desc";
    return (BiometricLog) getCurrentSession().createQuery(query).setMaxResults(1).getSingleResult();
  }

  @Override
  public boolean saveOrUpdateBiometricLog(BiometricLog biometricLog) {
    getCurrentSession().save(biometricLog);
    return true;
  }

  @Override
  public List<UserReport> getUserLogReportBetweenTheDays(Timestamp startDate, Timestamp endDate) {
    if (new Date(startDate.getTime()).after(new Date(endDate.getTime()))) {
      Timestamp timestamp = endDate;
      endDate = startDate;
      startDate = timestamp;
    }
    String queryString = "SELECT b.`USER_ID` AS 'userCode',  "
        + "GROUP_CONCAT(b.`RECORD_TIMESTAMP` ) AS 'timestamps',  "
        + " GROUP_CONCAT(b.`RECORD_TYPE`) AS 'timestampTypes', "
        + " st.START AS 'shiftStartTime', st.END  AS 'shiftEndTime' FROM biometric_logs b "
        + " JOIN employees e ON e.`CODE` = b.`USER_ID` "
        + " JOIN shift_timings st ON st.`ID` = e.`SHIFT_ID` "
        + " WHERE DATE(b.`RECORD_TIMESTAMP`) BETWEEN :startDate AND :endDate  "
        + " GROUP BY DATE(b.`RECORD_TIMESTAMP`), b.`USER_ID`  "
        + " ORDER BY b.`USER_ID` ASC, b.`RECORD_TIMESTAMP` DESC";

    @SuppressWarnings("deprecation")
    Query query = getCurrentSession().createNativeQuery(queryString)
        .setResultTransformer(Transformers.aliasToBean(UserReport.class));
    query.setParameter("startDate", startDate);
    query.setParameter("endDate", endDate);
    return query.getResultList();
  }

  @Override
  public List<BiometricLog> getAllEmployeesWithLatestLogs() {
    String query = "SELECT e.CODE userId,b.RECORD_TIMESTAMP entryTimestamp FROM employees e LEFT JOIN " + 
    		"(SELECT user_id, MAX(RECORD_TIMESTAMP) RECORD_TIMESTAMP FROM biometric_logs GROUP BY user_id) b " + 
    		"ON b.USER_ID = e.CODE ORDER BY e.CODE";
    Query qr = getCurrentSession().createNativeQuery(query)
            .setResultTransformer(Transformers.aliasToBean(BiometricLog.class));
    return qr.getResultList(); 
  }

  @Override
  public List<String> getEmployeeCode() {
    String str =
        "SELECT e.`CODE` userCode FROM employees e WHERE e.`CODE` NOT IN (SELECT user_ID FROM biometric_logs b GROUP BY user_ID)";
    Query query = getCurrentSession().createNativeQuery(str)
        .setResultTransformer(Transformers.aliasToBean(UserReport.class));
    List<UserReport> userReports = query.getResultList();
    return userReports.stream().map(user -> user.getUserCode()).collect(Collectors.toList());
  }
}
