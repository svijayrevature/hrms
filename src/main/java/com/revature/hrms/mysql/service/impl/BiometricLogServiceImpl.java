package com.revature.hrms.mysql.service.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.revature.hrms.mysql.dao.BiometricLogDAO;
import com.revature.hrms.mysql.dto.TimestampLog;
import com.revature.hrms.mysql.dto.UserReport;
import com.revature.hrms.mysql.model.BiometricLog;
import com.revature.hrms.mysql.model.Employee;
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
  public boolean saveOrUpdateBiometricLog(BiometricLog biometricLog) {
    return getBiometricLogDAO().saveOrUpdateBiometricLog(biometricLog);
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
        List<TimestampLog> timestampLogs = new ArrayList<>();
        double hoursSpent = 0;
        Stack<Timestamp> timestampStack = new Stack<>();
        for (int i = 0; i < timeStrings.length; i++) {
          TimestampLog timestampLog = new TimestampLog();
          timestampLog.setTimestamp(CalendarUtils
              .convertStringDateToTimestampForFormat(timeStrings[i], "yyyy-MM-dd hh:mm:ss"));
          timestampLog.setType(TypeConversionUtil.toBool(typeStrings[i]));
          if ((CollectionUtils.isEmpty(timestampLogs)
              || isNotAConsecutiveLogType(timestampLogs, timestampLog))
              && isInBetweenShiftTimings(timestampLog.getTimestamp(), ur.getShiftEndTime())) {
            if (Objects.nonNull(ur.getShiftStartTime())
                && (timestampLog.getTimestamp().after(ur.getShiftStartTime()) || timestampLog
                    .getTimestamp().equals(new Timestamp(ur.getShiftStartTime().getTime())))) {
              timestampLogs.add(timestampLog);
            } else if (Objects.nonNull(ur.getShiftStartTime())
                && timestampLog.getTimestamp().before(ur.getShiftStartTime())) {
              timestampLog.setTimestamp(new Timestamp(ur.getShiftStartTime().getTime()));
              timestampLogs.add(timestampLog);
            }
            if (timestampStack.isEmpty()) {
              timestampStack.push(timestampLog.getTimestamp());
            } else if (i == timeStrings.length - 1 && timeStrings.length % 2 != 0
                && timestampLog.getType().equals(false)) {
              Timestamp ts = new Timestamp(ur.getShiftEndTime().getTime());
              hoursSpent +=
                  CalendarUtils.getHoursDifferenceBtwTimestamps(timestampLog.getTimestamp(), ts);
            } else {
              hoursSpent += CalendarUtils.getHoursDifferenceBtwTimestamps(timestampStack.pop(),
                  timestampLog.getTimestamp());
            }
          }
        }
        ur.setTimestampLogs(timestampLogs);
        ur.setHoursSpent(hoursSpent);
      }
    }
    return userReports;
  }

  private boolean isNotAConsecutiveLogType(List<TimestampLog> timestampLogs,
      TimestampLog timestampLog) {
    return Objects.nonNull(timestampLogs.get(timestampLogs.size() - 1))
        && Objects.nonNull(timestampLogs.get(timestampLogs.size() - 1).getType())
        && Objects.nonNull(timestampLog.getType())
        && !timestampLogs.get(timestampLogs.size() - 1).getType().equals(timestampLog.getType());
  }

  private boolean isInBetweenShiftTimings(Timestamp timestamp, Time shiftEndTime) {
    return Objects.nonNull(shiftEndTime) && timestamp.before(new Timestamp(shiftEndTime.getTime()));
  }

  @Override
  public List<Employee> getAllEmployees() {
    return getBiometricLogDAO().getAllEmployees();
  }
}
