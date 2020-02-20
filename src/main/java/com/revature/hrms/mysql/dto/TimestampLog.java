package com.revature.hrms.mysql.dto;

import java.sql.Timestamp;

import com.revature.hrms.util.TypeConversionUtil;

import lombok.Data;

@Data
public class TimestampLog {
  Timestamp timestamp;
  Boolean type;

  public void setType(Boolean type) {
    this.type = TypeConversionUtil.toBool(type);
  }
}
