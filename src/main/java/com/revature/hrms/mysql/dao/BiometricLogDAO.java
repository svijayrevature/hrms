package com.revature.hrms.mysql.dao;

import com.revature.hrms.mysql.dto.UserReport;
import com.revature.hrms.mysql.model.BiometricLog;

import java.sql.Timestamp;
import java.util.List;

public interface BiometricLogDAO {
    List<BiometricLog> getAllBiometricLogs();

    Integer saveOrUpdateAllBiometricLogs(List<BiometricLog> biometricLogs);

    List<UserReport> getUserLogReportBetweenTheDays(Timestamp startDate, Timestamp endDate);
}
