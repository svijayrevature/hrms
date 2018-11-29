package com.revature.hrms.mysql.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "biometric_logs")
public class BiometricLog {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;
  @Column(name = "USER_ID")
  private String userId;
  @Column(name = "RECORD_TIMESTAMP")
  private Timestamp entryTimestamp;
  @Column(name = "RECORD_TYPE")
  private Boolean entryType;
}
