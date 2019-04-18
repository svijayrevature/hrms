package com.revature.hrms.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.hrms.mssql.model.ATDPunch;
import com.revature.hrms.mssql.service.ATDPunchService;
import com.revature.hrms.mysql.model.BiometricLog;
import com.revature.hrms.mysql.service.BiometricLogService;

import lombok.Data;

@Data
@RestController
public class ReportController {

  private final static Logger LOGGER = LogManager.getLogger(ReportController.class.getName());

  @Autowired
  private ATDPunchService atdPunchService;

  @Autowired
  private BiometricLogService biometricLogService;

  @Autowired
  @Qualifier("mysqlSessionFactory")
  private SessionFactory mysqlSessionFactory;

  @RequestMapping(value = "/syncDatabases")
  @Scheduled(cron = "${rates.refresh.cron.log.sync}")
  public void getSQLReport() {
    LOGGER.info(" Fetching employees from database");
    List<BiometricLog> employees = getBiometricLogService().getAllEmployeesWithLatestLogs();
    if (!CollectionUtils.isEmpty(employees)) {
      LOGGER.info(" Fetching data from server");
      List<ATDPunch> atdPunches = getAtdPunchService().getAllPunchEntriesForEmployees(employees);
      long numberOfRecordsSaved = 0L;
      for (ATDPunch atdPunch : atdPunches) {
        BiometricLog biometricLog = new BiometricLog();
        biometricLog.setEntryTimestamp(atdPunch.getEntryTimestamp());
        biometricLog.setEntryType(atdPunch.getEntryType());
        biometricLog.setUserId(atdPunch.getUserId());
        getBiometricLogService().saveOrUpdateBiometricLog(biometricLog);
        ++numberOfRecordsSaved;
      }
      LOGGER.info(numberOfRecordsSaved + " Record(s) saved successfully");
    } else {
      LOGGER.error("No employees were found.");
    }
  }
}
