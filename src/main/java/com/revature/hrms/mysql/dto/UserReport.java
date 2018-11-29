package com.revature.hrms.mysql.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserReport {
  List<TimestampLogs> timestampLogs;
  private String userCode;
  @JsonIgnore
  private String timestamps;
  @JsonIgnore
  private String timestampTypes;
  private double hoursSpent;
}
