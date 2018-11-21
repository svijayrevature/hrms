package com.revature.hrms.controller;

import com.revature.hrms.model.mssql.ATDPunch;
import lombok.Data;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@Data
@RestController
@Transactional
public class ReportController {

    @Autowired
    @Qualifier("sqlJdbcTemplate")
    private JdbcTemplate sqlTemplate;

    @Autowired
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate mysqlTemplate;

    @Autowired
    @Qualifier("mssqlSessionFactory")
    private SessionFactory mssqlSessionFactory;

    @Autowired
    @Qualifier("mysqlSessionFactory")
    private SessionFactory mysqlSessionFactory;

    @RequestMapping(value = "/getSQLReport")
    public List<ATDPunch> getSQLReport() {
        String query = "select UserID as userId, Edatetime as entryTimestamp, IOType as entryType from cosec1.dbo.Mx_AtdPunch";
        List resultWithAliasedBean = getMssqlSessionFactory().getCurrentSession().createSQLQuery(query)
                .addScalar("userId")
                .addScalar("entryTimestamp")
                .addScalar("entryType")
                .setResultTransformer(Transformers.aliasToBean(ATDPunch.class))
                .list();
        List<ATDPunch> data = (List<ATDPunch>) resultWithAliasedBean;
        return data;
    }

}
