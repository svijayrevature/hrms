package com.revature.hrms.mysql.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public void setEntryType(Boolean entryType) {
		this.entryType = entryType;
	}
    
   
}
