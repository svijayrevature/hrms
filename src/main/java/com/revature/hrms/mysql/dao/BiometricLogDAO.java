package com.revature.hrms.mysql.dao;

import java.sql.Timestamp;
import java.util.List;

import com.revature.hrms.mysql.dto.UserReport;
import com.revature.hrms.mysql.model.BiometricLog;

public interface BiometricLogDAO {
  List<BiometricLog> getAllBiometricLogs();

  Integer saveOrUpdateAllBiometricLogs(List<BiometricLog> biometricLogs);

  List<UserReport> getUserLogReportBetweenTheDays(Timestamp startDate, Timestamp endDate);
}
