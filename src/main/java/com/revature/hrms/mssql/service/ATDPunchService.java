package com.revature.hrms.mssql.service;

import java.util.List;

import com.revature.hrms.mssql.model.ATDPunch;
import com.revature.hrms.mysql.model.BiometricLog;

public interface ATDPunchService {
  List<ATDPunch> getAllPunchEntriesForEmployees(List<BiometricLog> employees);
}
