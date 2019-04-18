package com.revature.hrms.mssql.dao.impl;

import java.util.List;
import java.util.Objects;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.hrms.mssql.dao.ATDPunchDAO;
import com.revature.hrms.mssql.model.ATDPunch;
import com.revature.hrms.mysql.model.BiometricLog;

import lombok.Data;

@Data
@Repository
@Transactional(readOnly = true)
public class ATDPunchDAOImpl implements ATDPunchDAO {

  private static final StringBuilder SELECT_TEMPLATE =
      new StringBuilder(" select UserID userId ,Edatetime entryTimestamp,IOType entryType ")
          .append(" FROM Mx_AtdPunch a where UserID = {roleCode} AND Edatetime >= {time} ");


  private static final StringBuilder SELECT_TEMPLATE_NO_DATE =
      new StringBuilder(" select UserID userId,Edatetime entryTimestamp,IOType entryType ")
          .append(" FROM Mx_AtdPunch a where UserID = {roleCode} ");

  @Autowired
  @Qualifier("mssqlSessionFactory")
  private SessionFactory mssqlSessionFactory;

  private Session getCurrentSession() {
    Session session = getMssqlSessionFactory().getCurrentSession();
    session.setDefaultReadOnly(true);
    return session;
  }

  @Override
  public List<ATDPunch> getAllPunchEntriesForEmployees(List<BiometricLog> employees) {
    String query = "";
    for (BiometricLog employee : employees) {
      if (Objects.nonNull(employee.getEntryTimestamp()))
        query += (SELECT_TEMPLATE.toString().replace("{roleCode}", "'" + employee.getUserId() + "'")
            .replace("{time}", "'" + employee.getEntryTimestamp().toString() + "'"));
      else
        query += (SELECT_TEMPLATE_NO_DATE.toString().replace("{roleCode}",
            "'" + employee.getUserId() + "'"));
      if (!employee.equals(employees.get(employees.size() - 1))) {
        query += " UNION ";
      }
    }
    return getCurrentSession().createNativeQuery(query)
        .setResultTransformer(Transformers.aliasToBean(ATDPunch.class)).getResultList();
  }
}
