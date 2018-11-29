package com.revature.hrms.mysql.service;

import com.revature.hrms.mysql.dto.UserReport;
import com.revature.hrms.mysql.model.BiometricLog;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

public interface BiometricLogService {
    List<BiometricLog> getAllBiometricLogs();

    Integer saveOrUpdateAllBiometricLogs(List<BiometricLog> biometricLogs);

    List<UserReport> getUserLogReportBetweenTheDays(Timestamp startDate, Timestamp endDate) throws ParseException;
}
