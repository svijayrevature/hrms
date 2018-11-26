package com.revature.hrms.mssql.model;

import com.revature.hrms.util.TypeConversionUtil;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "Mx_AtdPunch")
public class ATDPunch {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "UserID")
    private String userId;

    @Column(name = "Edatetime")
    private Timestamp entryTimestamp;

    @Column(name = "IOType")
    private Boolean entryType;

    public void setEntryType(Object entryType) {
        this.entryType = TypeConversionUtil.toBool(entryType);
    }
}
