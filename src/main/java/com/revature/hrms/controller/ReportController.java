package com.revature.hrms.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.hrms.mssql.model.ATDPunch;
import com.revature.hrms.mssql.service.ATDPunchService;
import com.revature.hrms.mysql.dto.UserReport;
import com.revature.hrms.mysql.model.BiometricLog;
import com.revature.hrms.mysql.model.Employee;
import com.revature.hrms.mysql.service.BiometricLogService;
import com.revature.hrms.util.CalendarUtils;

import lombok.Data;

@Data
@RestController
public class ReportController {

  private final static Logger LOGGER = LogManager.getLogger(ReportController.class.getName());

  @Autowired
  ATDPunchService atdPunchService;

  @Autowired
  BiometricLogService biometricLogService;

  @Autowired
  @Qualifier("mysqlSessionFactory")
  private SessionFactory mysqlSessionFactory;

  @RequestMapping(value = "/syncDatabases")
  @Scheduled(cron = "${rates.refresh.cron.log.sync}")
  public void getSQLReport() {
    List<ATDPunch> atdPunches = new ArrayList<>();
    List<BiometricLog> employees = getBiometricLogService().getAllEmployeesWithLatestLogs();
    if (!CollectionUtils.isEmpty(employees)) {
    	atdPunches = getAtdPunchService().getAllPunchEntriesForEmployees(employees);
    } 
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
  }

  @RequestMapping(value = "/getUserReport")
  public List<UserReport> getAllUserReportsBetweenDates(
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate) {
    List<UserReport> userReports = new ArrayList<>();
    try {
      userReports = getBiometricLogService().getUserLogReportBetweenTheDays(
          CalendarUtils.convertStringDateToTimestampForFormat(startDate, "dd-MM-yyyy"),
          CalendarUtils.convertStringDateToTimestampForFormat(endDate, "dd-MM-yyyy"));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return userReports;
  }
}
