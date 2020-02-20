package com.revature.hrms.mysql.dao;

import java.sql.Timestamp;
import java.util.List;

import com.revature.hrms.mysql.dto.UserReport;
import com.revature.hrms.mysql.model.BiometricLog;
import com.revature.hrms.mysql.model.Employee;

public interface BiometricLogDAO {
  List<BiometricLog> getAllBiometricLogs();

  boolean saveOrUpdateBiometricLog(BiometricLog biometricLog);

  List<UserReport> getUserLogReportBetweenTheDays(Timestamp startDate, Timestamp endDate);

  List<Employee> getAllEmployees();

  List<String> getEmployeeCode();
}
