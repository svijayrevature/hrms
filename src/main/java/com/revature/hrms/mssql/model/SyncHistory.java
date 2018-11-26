package com.revature.hrms.mssql.model;

import com.revature.hrms.util.TypeConversionUtil;
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
@Table(name = "sync_history")
public class SyncHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "START_TIMESTAMP")
    private Timestamp startTime;

    @Column(name = "END_TIMESTAMP")
    private Timestamp endTime;

    @Column(name = "IS_SUCCESSFUL")
    private Boolean isSuccess;

    @Column(name = "IS_FINISHED")
    private Boolean isFinished;

    public void setSuccess(Object success) {
        isSuccess = TypeConversionUtil.toBool(success);
    }

    public void setFinished(Boolean finished) {
        isFinished = TypeConversionUtil.toBool(finished);
    }
}
