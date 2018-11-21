package com.revature.hrms.model.mssql;

import java.sql.Timestamp;

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
        this.entryType = Boolean.valueOf((entryType != null) ? entryType.toString() : null);
    }
}
