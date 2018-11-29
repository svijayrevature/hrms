package com.revature.hrms.mysql.dao.impl;

import com.revature.hrms.mysql.dao.BiometricLogDAO;
import com.revature.hrms.mysql.model.BiometricLog;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Data
@Repository
@Transactional()
public class BiometricLogDAOImpl implements BiometricLogDAO {

    @Autowired
    @Qualifier("mysqlSessionFactory")
    SessionFactory mysqlSessionFactory;

    private Session getCurrentSession() {
        return mysqlSessionFactory.getCurrentSession();
    }

    @Override
    public List<BiometricLog> getAllBiometricLogs() {
        String query = "from BiometricLog";
        return getCurrentSession().createQuery(query).getResultList();
    }

    @Override
    public Integer saveOrUpdateAllBiometricLogs(List<BiometricLog> biometricLogs) {
        for (int i=0;i<biometricLogs.size();i++) {
            getCurrentSession().save(biometricLogs.get(i));
            if(i% 20 == 0) {
            	getCurrentSession().flush();
            	getCurrentSession().clear();
            }
        }
        return biometricLogs.size();
    }
}
