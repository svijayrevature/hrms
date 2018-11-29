package com.revature.hrms.mysql.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class UserReport {
    List<TimestampLogs> timestampLogs;
    private String userCode;
    @JsonIgnore
    private String timestamps;
    @JsonIgnore
    private String timestampTypes;
}