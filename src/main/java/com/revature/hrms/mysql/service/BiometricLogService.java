package com.revature.hrms.mysql.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import com.revature.hrms.mysql.dto.UserReport;
import com.revature.hrms.mysql.model.BiometricLog;
import com.revature.hrms.mysql.model.Employee;

public interface BiometricLogService {
	BiometricLog getLatestBiometricLog();

  boolean saveOrUpdateBiometricLog(BiometricLog biometricLog);

  List<UserReport> getUserLogReportBetweenTheDays(Timestamp startDate, Timestamp endDate)
      throws ParseException;

  List<BiometricLog> getAllEmployeesWithLatestLogs();

  List<String> getEmployeeCodes();
}
