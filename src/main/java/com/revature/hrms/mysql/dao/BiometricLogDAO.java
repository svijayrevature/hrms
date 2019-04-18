package com.revature.hrms.mysql.dao;

import java.sql.Timestamp;
import java.util.List;

import com.revature.hrms.mysql.dto.UserReport;
import com.revature.hrms.mysql.model.BiometricLog;
import com.revature.hrms.mysql.model.Employee;

public interface BiometricLogDAO {
  BiometricLog getLatestBiometricLog();

  boolean saveOrUpdateBiometricLog(BiometricLog biometricLog);

  List<UserReport> getUserLogReportBetweenTheDays(Timestamp startDate, Timestamp endDate);

  List<BiometricLog> getAllEmployeesWithLatestLogs();

  List<String> getEmployeeCode();
}
