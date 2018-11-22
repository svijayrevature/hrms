package com.revature.hrms.mssql.dao.impl;

import com.revature.hrms.mssql.dao.ATDPunchDAO;
import com.revature.hrms.mssql.model.ATDPunch;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;

@Data
@Repository
@Transactional(readOnly = true)
public class ATDPunchDAOImpl implements ATDPunchDAO {

    @Autowired
    @Qualifier("mssqlSessionFactory")
    private SessionFactory mssqlSessionFactory;

    private Session getCurrentSession() {
        Session session = getMssqlSessionFactory().getCurrentSession();
        session.setDefaultReadOnly(true);
        return session;
    }

    @Override
    public List<ATDPunch> getAllPunchEntries() {
        String query = "from ATDPunch";
        List<ATDPunch> data = getCurrentSession().createQuery(query).getResultList();
        return data;
    }

    @Override
    public List<ATDPunch> getAllPunchEntriesNotInDates(Timestamp date) {
        String queryString = "from ATDPunch a where substring(a.entryTimestamp,1,10) > substring(:date,1,10)";
        Query query = getCurrentSession().createQuery(queryString).setParameter("date", date);
        List<ATDPunch> data = query.getResultList();
        return data;
    }
}
