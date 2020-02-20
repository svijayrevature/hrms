package com.revature.hrms.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee {
  @Id
  @Column(name = "CODE")
  private String code;

  @Column(name = "NAME")
  private String name;

  @Column(name = "EMAIL", unique = true)
  private String email;

  @Column(name = "REPORTING_TO")
  private String reportingTo;

  @ManyToOne
  @JoinColumn(name = "SHIFT_ID")
  private ShiftTiming shiftTiming;

  @ManyToOne
  @JoinColumn(name = "DEPARTMENT_ID")
  private Department department;

  @ManyToOne
  @JoinColumn(name = "BRANCH_ID")
  private Branch branch;

  @ManyToOne
  @JoinColumn(name = "DESIGNATION_ID")
  private Designation designation;
}
