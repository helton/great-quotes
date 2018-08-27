package com.ciandt.service;

import com.ciandt.Application;
import com.ciandt.model.User;
import org.apache.catalina.Server;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("UserService")
public class UserServiceImpl implements UserService{

    Logger log = LoggerFactory.getLogger(Server.class);

    @Override
    public User getUser(Integer userId) {
        User user = null;
        Session session = Application.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            user = session.get(User.class, userId);
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            transaction.rollback();
        } finally {
            session.close();
            return user;
        }
    }

    @Override
    public void createUser(User user) {
        Session session = Application.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(user);
            transaction.commit();

        } catch (Exception e) {
            log.error(e.getMessage());
            transaction.rollback();

        } finally {
            session.close();
        }
    }

    @Override
    public List<User> listUsers() {
        List<User> list = new ArrayList<>();
        Session session = Application.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            list = session.createQuery("from User").list();
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            transaction.rollback();
        } finally {
            session.close();
            return list;
        }
    }

    @Override
    public void deleteUser(User user) {
        Session session = Application.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            transaction.rollback();

        } finally {
            session.close();
        }
    }
}
