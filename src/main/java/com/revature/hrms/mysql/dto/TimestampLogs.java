package com.revature.hrms.mysql.dto;

import com.revature.hrms.util.TypeConversionUtil;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TimestampLogs {
    Timestamp timestamp;
    Boolean type;

    public void setType(Boolean type) {
        this.type = TypeConversionUtil.toBool(type);
    }
}
