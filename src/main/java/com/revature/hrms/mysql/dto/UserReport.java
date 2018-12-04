package com.revature.hrms.mysql.dto;

import java.sql.Time;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserReport {
  List<TimestampLog> timestampLogs;

  private String userCode;

  @JsonIgnore
  private String timestamps;

  @JsonIgnore
  private String timestampTypes;

  private Time shiftStartTime;

  private Time shiftEndTime;

  private double hoursSpent;
}
