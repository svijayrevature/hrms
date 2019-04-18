package com.revature.hrms.mssql.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.hrms.mssql.dao.ATDPunchDAO;
import com.revature.hrms.mssql.model.ATDPunch;
import com.revature.hrms.mssql.service.ATDPunchService;
import com.revature.hrms.mysql.model.BiometricLog;

import lombok.Data;

@Data
@Service
public class ATDPunchServiceImpl implements ATDPunchService {

  @Autowired
  ATDPunchDAO atdPunchDAO;

  @Override
  public List<ATDPunch> getAllPunchEntries() {
    return getAtdPunchDAO().getAllPunchEntries();
  }

  @Override
  public List<ATDPunch> getAllPunchEntriesAfterDates(Timestamp date) {
    return getAtdPunchDAO().getAllPunchEntriesNotInDates(date);
  }

  @Override
  public List<ATDPunch> getAllPunchEntriesBeforeDates(Timestamp date, List<String> userCodes) {
    return getAtdPunchDAO().getAllPunchEntriesBeforeDates(date, userCodes);
  }

  @Override
  public List<ATDPunch> getAllPunchEntriesForEmployees(List<BiometricLog> employees) {
    return getAtdPunchDAO().getAllPunchEntriesForEmployees(employees);
  }
}
