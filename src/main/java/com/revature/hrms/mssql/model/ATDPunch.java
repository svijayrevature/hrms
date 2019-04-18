package com.revature.hrms.mssql.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.revature.hrms.util.TypeConversionUtil;

import lombok.Data;

public class ATDPunch {

  private String userId;

  private Timestamp entryTimestamp;

  private Boolean entryType;


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Timestamp getEntryTimestamp() {
    return entryTimestamp;
  }

  public void setEntryTimestamp(Timestamp entryTimestamp) {
    this.entryTimestamp = entryTimestamp;
  }

  public Boolean getEntryType() {
    return entryType;
  }

  public void setEntryType(Object entryType) {
    this.entryType = TypeConversionUtil.toBool(entryType);
  }

  @Override
  public String toString() {
    return "ATDPunch [userId=" + userId + ", entryTimestamp=" + entryTimestamp + ", entryType="
        + entryType + "]";
  }


}
