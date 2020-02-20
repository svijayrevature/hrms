package com.revature.hrms.mssql.dao;

import java.sql.Timestamp;
import java.util.List;

import com.revature.hrms.mssql.model.ATDPunch;

public interface ATDPunchDAO {
  List<ATDPunch> getAllPunchEntries();

  List<ATDPunch> getAllPunchEntriesNotInDates(Timestamp date);

  List<ATDPunch> getAllPunchEntriesBeforeDates(Timestamp date, List<String> userCodes);
}
