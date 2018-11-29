package com.revature.hrms.mssql.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Mx_UserMst")
public class UserMST {
	@Id
	@Column(name = "UserID")
	private String userId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="EnrollDT")
	private Timestamp enrolledDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getEnrolledDate() {
		return enrolledDate;
	}

	public void setEnrolledDate(Timestamp enrolledDate) {
		this.enrolledDate = enrolledDate;
	}

	
	
	
}
