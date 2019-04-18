package com.revature.hrms.mssql.service.impl;

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
  private ATDPunchDAO atdPunchDAO;

  @Override
  public List<ATDPunch> getAllPunchEntriesForEmployees(List<BiometricLog> employees) {
    return getAtdPunchDAO().getAllPunchEntriesForEmployees(employees);
  }
}
