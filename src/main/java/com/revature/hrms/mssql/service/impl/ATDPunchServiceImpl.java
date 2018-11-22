package com.revature.hrms.mssql.service.impl;

import com.revature.hrms.mssql.dao.ATDPunchDAO;
import com.revature.hrms.mssql.model.ATDPunch;
import com.revature.hrms.mssql.service.ATDPunchService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

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
}
