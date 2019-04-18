package com.revature.hrms.mssql.dao;

import java.sql.Timestamp;
import java.util.List;

import com.revature.hrms.mssql.model.ATDPunch;
import com.revature.hrms.mysql.model.BiometricLog;

public interface ATDPunchDAO {
  List<ATDPunch> getAllPunchEntries();

  List<ATDPunch> getAllPunchEntriesNotInDates(Timestamp date);

  List<ATDPunch> getAllPunchEntriesBeforeDates(Timestamp date, List<String> userCodes);

List<ATDPunch> getAllPunchEntriesForEmployees(List<BiometricLog> employees);
}
