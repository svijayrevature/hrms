package com.revature.hrms.mssql.dao.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.hrms.mssql.dao.ATDPunchDAO;
import com.revature.hrms.mssql.model.ATDPunch;
import com.revature.hrms.mysql.model.BiometricLog;
import com.revature.hrms.util.CalendarUtils;

import lombok.Data;

@Data
@Repository
@Transactional(readOnly = true)
public class ATDPunchDAOImpl implements ATDPunchDAO {

  private static final String SELECT_COLUMNS =
      "select ROW_NUMBER() OVER (order by Edatetime) ID,UserID " + ",Edatetime " + ",IOType "
          + "FROM Mx_AtdPunch a ";

  private static final StringBuilder SELECT_TEMPLATE = new StringBuilder(" select ROW_NUMBER() OVER (order by Edatetime) ID,UserID ,Edatetime ,IOType ") 
  		.append(" FROM Mx_AtdPunch a where UserID = {roleCode} AND Edatetime >= {time} ");
  

  private static final StringBuilder SELECT_TEMPLATE_NO_DATE = new StringBuilder(" select ROW_NUMBER() OVER (order by Edatetime) ID,UserID ,Edatetime ,IOType ") 
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
  public List<ATDPunch> getAllPunchEntries() {
    String query = SELECT_COLUMNS;
    return getCurrentSession().createNativeQuery(query, ATDPunch.class).getResultList();
  }

  @Override
  public List<ATDPunch> getAllPunchEntriesNotInDates(Timestamp date) {
    String queryString = SELECT_COLUMNS + " where a.Edatetime >= :date";
    date = CalendarUtils.resetTimestampTime(date);
    date = CalendarUtils.addFieldToTimestamp(date, Calendar.DAY_OF_MONTH, 1);
    Query query = getCurrentSession().createNativeQuery(queryString, ATDPunch.class)
        .setParameter("date", date);
    return query.getResultList();
  }

  @Override
  public List<ATDPunch> getAllPunchEntriesBeforeDates(Timestamp date, List<String> userCodes) {
    String queryString = SELECT_COLUMNS + " where a.Edatetime < :date and a.UserID in (:codes)";
    date = CalendarUtils.resetTimestampTime(date);
    date = CalendarUtils.addFieldToTimestamp(date, Calendar.DAY_OF_MONTH, 1);
    Query query = getCurrentSession().createNativeQuery(queryString, ATDPunch.class)
        .setParameter("date", date).setParameter("codes", userCodes);
    return query.getResultList();
  }
  
  @Override
  public List<ATDPunch> getAllPunchEntriesForEmployees(List<BiometricLog> employees){
	  String query = "";
	  for(BiometricLog employee:employees) {
		  if(Objects.nonNull(employee.getEntryTimestamp()))
			  query  +=  (SELECT_TEMPLATE.toString().replace("{roleCode}", "'"+employee.getUserId()+"'").replace("{time}", "'"+employee.getEntryTimestamp().toString()+"'"));
		  else
			  query  +=  (SELECT_TEMPLATE_NO_DATE.toString().replace("{roleCode}","'"+ employee.getUserId()+"'"));  
				  if(!employee.equals(employees.get(employees.size()-1))) {
					  query += " UNION "; 
				  }
	  }
	 return getCurrentSession().createNativeQuery(query, ATDPunch.class).getResultList();
  }
}
