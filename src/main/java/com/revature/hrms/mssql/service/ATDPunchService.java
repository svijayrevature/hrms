package com.revature.hrms.mssql.service;

import java.sql.Timestamp;
import java.util.List;

import com.revature.hrms.mssql.model.ATDPunch;
import com.revature.hrms.mysql.model.BiometricLog;

public interface ATDPunchService {
  List<ATDPunch> getAllPunchEntries();

  List<ATDPunch> getAllPunchEntriesAfterDates(Timestamp date);

  List<ATDPunch> getAllPunchEntriesBeforeDates(Timestamp date, List<String> userCodes);

  List<ATDPunch> getAllPunchEntriesForEmployees(List<BiometricLog> employees);
}
