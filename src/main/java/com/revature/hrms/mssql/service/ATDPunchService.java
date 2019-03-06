package com.revature.hrms.mssql.service;

import java.sql.Timestamp;
import java.util.List;

import com.revature.hrms.mssql.model.ATDPunch;

public interface ATDPunchService {
  List<ATDPunch> getAllPunchEntries();

  List<ATDPunch> getAllPunchEntriesAfterDates(Timestamp date);

List<ATDPunch> getAllPunchEntriesBeforeDates(Timestamp date);
}
