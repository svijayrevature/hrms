package com.revature.hrms.mssql.service;

import com.revature.hrms.mssql.model.ATDPunch;

import java.sql.Timestamp;
import java.util.List;

public interface ATDPunchService {
    List<ATDPunch> getAllPunchEntries();

    List<ATDPunch> getAllPunchEntriesAfterDates(Timestamp date);
}
