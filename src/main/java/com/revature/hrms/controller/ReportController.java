package com.revature.hrms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.hrms.shared.service.ReportService;

import lombok.Data;

@RestController
public class ReportController {

  @Autowired
  private ReportService reportService;

  @RequestMapping(value = "/syncDatabases")
  @Scheduled(cron = "${rates.refresh.cron.log.sync}")
  public String getSQLReport() {
    long numberOfRecordsSaved = reportService.syncDatabase();
    return numberOfRecordsSaved + " Record(s) saved successfully";
  }
}
