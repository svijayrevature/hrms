package com.revature.hrms.mysql.service;

import com.revature.hrms.mysql.model.BiometricLog;

import java.util.List;

public interface BiometricLogService {
    List<BiometricLog> getAllBiometricLogs();

    Integer saveOrUpdateAllBiometricLogs(List<BiometricLog> biometricLogs);
}
