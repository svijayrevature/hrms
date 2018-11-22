package com.revature.hrms.mysql.service.impl;

import com.revature.hrms.mysql.dao.BiometricLogDAO;
import com.revature.hrms.mysql.model.BiometricLog;
import com.revature.hrms.mysql.service.BiometricLogService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class BiometricLogServiceImpl implements BiometricLogService {

    @Autowired
    BiometricLogDAO biometricLogDAO;

    @Override
    public List<BiometricLog> getAllBiometricLogs() {
        return getBiometricLogDAO().getAllBiometricLogs();
    }

    @Override
    public Integer saveOrUpdateAllBiometricLogs(List<BiometricLog> biometricLogs) {
        return getBiometricLogDAO().saveOrUpdateAllBiometricLogs(biometricLogs);
    }
}
