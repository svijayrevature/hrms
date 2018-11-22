package com.revature.hrms.controller;

import com.revature.hrms.mssql.model.ATDPunch;
import com.revature.hrms.mssql.service.ATDPunchService;
import com.revature.hrms.mysql.model.BiometricLog;
import com.revature.hrms.mysql.service.BiometricLogService;
import lombok.Data;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@RestController
public class ReportController {

    @Autowired
    ATDPunchService atdPunchService;
    @Autowired
    BiometricLogService biometricLogService;
    @Autowired
    @Qualifier("mysqlSessionFactory")
    private SessionFactory mysqlSessionFactory;

    @RequestMapping(value = "/syncDatabases")
    public String getSQLReport() {
        List<BiometricLog> biometricLogs = new ArrayList<>();
        List<ATDPunch> atdPunches;
        List<BiometricLog> biometricLogsInDB = getBiometricLogService().getAllBiometricLogs();
        List<Timestamp> dates = biometricLogsInDB.stream().map(BiometricLog::getEntryTimestamp).distinct().collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(dates)) {
            atdPunches = getAtdPunchService().getAllPunchEntriesAfterDates(Collections.max(dates));
        } else {
            atdPunches = getAtdPunchService().getAllPunchEntries();
        }
        for (ATDPunch atdPunch :
                atdPunches) {
            BiometricLog biometricLog = new BiometricLog();
            biometricLog.setEntryTimestamp(atdPunch.getEntryTimestamp());
            biometricLog.setEntryType(atdPunch.getEntryType());
            biometricLog.setUserId(atdPunch.getUserId());
            biometricLogs.add(biometricLog);
        }
        Integer numberOfRecordsSaved = getBiometricLogService().saveOrUpdateAllBiometricLogs(biometricLogs);
        return numberOfRecordsSaved + " Record(s) saved successfully";
    }

}
