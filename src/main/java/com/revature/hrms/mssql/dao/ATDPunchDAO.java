package com.revature.hrms.mssql.dao;

import java.util.List;

import com.revature.hrms.mssql.model.ATDPunch;
import com.revature.hrms.mysql.model.BiometricLog;

public interface ATDPunchDAO {

  List<ATDPunch> getAllPunchEntriesForEmployees(List<BiometricLog> employees);
}
