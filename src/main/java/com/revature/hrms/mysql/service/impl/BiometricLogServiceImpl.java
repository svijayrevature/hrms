package com.revature.hrms.mysql.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.hrms.mysql.dao.BiometricLogDAO;
import com.revature.hrms.mysql.dto.TimestampLogs;
import com.revature.hrms.mysql.dto.UserReport;
import com.revature.hrms.mysql.model.BiometricLog;
import com.revature.hrms.mysql.service.BiometricLogService;
import com.revature.hrms.util.CalendarUtils;
import com.revature.hrms.util.TypeConversionUtil;

import lombok.Data;

@Data
@Service
public class BiometricLogServiceImpl implements BiometricLogService {

  @Autowired
  BiometricLogDAO biometricLogDAO;

  @Override
  public List<BiometricLog> getAllBiometricLogs() {
    return getBiometricLogDAO().getAllBiometricLogs();
  }

  @Override
  public Integer saveOrUpdateAllBiometricLogs(List<BiometricLog> biometricLogs) {
    return getBiometricLogDAO().saveOrUpdateAllBiometricLogs(biometricLogs);
  }

  @Override
  public List<UserReport> getUserLogReportBetweenTheDays(Timestamp startDate, Timestamp endDate)
      throws ParseException {
    List<UserReport> userReports =
        getBiometricLogDAO().getUserLogReportBetweenTheDays(startDate, endDate);
    for (UserReport ur : userReports) {
      String[] timeStrings = ur.getTimestamps().split(",");
      String[] typeStrings = ur.getTimestampTypes().split(",");
      if (timeStrings.length == typeStrings.length) {
        List<TimestampLogs> timestampLogs = new ArrayList<>();
        for (int i = 0; i < timeStrings.length; i++) {
          TimestampLogs timestampLog = new TimestampLogs();
          timestampLog.setTimestamp(CalendarUtils
              .convertStringDateToTimestampForFormat(timeStrings[i], "yyyy-MM-dd hh:mm:ss"));
          timestampLog.setType(TypeConversionUtil.toBool(typeStrings[i]));
          timestampLogs.add(timestampLog);
        }
        ur.setTimestampLogs(timestampLogs);
      }
    }
    return userReports;
  }
}
