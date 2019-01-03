package com.revature.hrms.shared.service.impl;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.revature.hrms.controller.ReportController;
import com.revature.hrms.mssql.model.ATDPunch;
import com.revature.hrms.mssql.service.ATDPunchService;
import com.revature.hrms.mysql.model.BiometricLog;
import com.revature.hrms.mysql.model.Employee;
import com.revature.hrms.mysql.service.BiometricLogService;
import com.revature.hrms.shared.service.ReportService;

import lombok.Data;

@Data
@Service
public class ReportServiceImpl implements ReportService {
  @Autowired
  ATDPunchService atdPunchService;

  @Autowired
  BiometricLogService biometricLogService;

  @Override
  @Scheduled(cron = "${rates.refresh.cron.log.sync}")
  public long syncDatabase() {
    List<ATDPunch> atdPunches;
    List<BiometricLog> biometricLogsInDB = getBiometricLogService().getAllBiometricLogs();
    List<Timestamp> dates = biometricLogsInDB.stream().map(BiometricLog::getEntryTimestamp)
        .distinct().collect(Collectors.toList());
    List<Employee> employees = getBiometricLogService().getAllEmployees();
    if (!CollectionUtils.isEmpty(dates)) {
      atdPunches = getAtdPunchService().getAllPunchEntriesAfterDates(Collections.max(dates));
    } else {
      atdPunches = getAtdPunchService().getAllPunchEntries();
    }
    atdPunches =
        atdPunches
            .stream().filter(atdp -> employees.stream().map(Employee::getCode)
                .collect(Collectors.toList()).contains(atdp.getUserId()))
            .collect(Collectors.toList());
    long numberOfRecordsSaved = 0L;
    for (ATDPunch atdPunch : atdPunches) {
      BiometricLog biometricLog = new BiometricLog();
      biometricLog.setEntryTimestamp(atdPunch.getEntryTimestamp());
      biometricLog.setEntryType(atdPunch.getEntryType());
      biometricLog.setUserId(atdPunch.getUserId());
      getBiometricLogService().saveOrUpdateBiometricLog(biometricLog);
      ++numberOfRecordsSaved;
    }
    return numberOfRecordsSaved;
  }
}
