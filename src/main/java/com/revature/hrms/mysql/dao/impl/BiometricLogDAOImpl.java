package com.revature.hrms.mysql.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
  public List<BiometricLog> getAllBiometricLogs() {
    String query = "from BiometricLog";
    return getCurrentSession().createQuery(query).getResultList();
  }

  @Override
  public Integer saveOrUpdateAllBiometricLogs(List<BiometricLog> biometricLogs) {
    for (BiometricLog biometricLog : biometricLogs) {
      getCurrentSession().save(biometricLog);
    }
    return biometricLogs.size();
  }

  @Override
  public List<UserReport> getUserLogReportBetweenTheDays(Timestamp startDate, Timestamp endDate) {
    if (new Date(startDate.getTime()).after(new Date(endDate.getTime()))) {
      Timestamp timestamp = endDate;
      endDate = startDate;
      startDate = timestamp;
    }
    String queryString = "SELECT b.`USER_ID` AS 'userCode', "
        + "GROUP_CONCAT(b.`RECORD_TIMESTAMP` ) AS 'timestamps', "
        + " GROUP_CONCAT(b.`RECORD_TYPE`) AS 'timestampTypes' FROM biometric_logs b "
        + " WHERE DATE(b.`RECORD_TIMESTAMP`) BETWEEN :startDate AND :endDate "
        + " GROUP BY DATE(b.`RECORD_TIMESTAMP`), b.`USER_ID` "
        + " ORDER BY b.`USER_ID` ASC, b.`RECORD_TIMESTAMP` DESC";

    @SuppressWarnings("deprecation")
    Query query = getCurrentSession().createNativeQuery(queryString)
        .setResultTransformer(Transformers.aliasToBean(UserReport.class));
    query.setParameter("startDate", startDate);
    query.setParameter("endDate", endDate);
    return query.getResultList();
  }
}
