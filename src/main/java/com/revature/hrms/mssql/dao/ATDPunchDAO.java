package com.revature.hrms.mssql.dao;

import com.revature.hrms.mssql.model.ATDPunch;

import java.sql.Timestamp;
import java.util.List;

public interface ATDPunchDAO {
    List<ATDPunch> getAllPunchEntries();

    List<ATDPunch> getAllPunchEntriesNotInDates(Timestamp date);
}
