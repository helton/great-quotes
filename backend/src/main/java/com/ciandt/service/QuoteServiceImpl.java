package com.ciandt.service;

import com.ciandt.Application;
import com.ciandt.model.Quote;
import org.apache.catalina.Server;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("QuoteService")
public class QuoteServiceImpl implements QuoteService {

    Logger log = LoggerFactory.getLogger(Server.class);

    @Override
    public Quote createQuote(Quote quote) {
        Quote quoteToReturn = null;
        Session session = Application.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Integer id = (Integer) session.save(quote);
            transaction.commit();
            quoteToReturn = readQuote(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            transaction.rollback();
        } finally {
            session.close();
            return quoteToReturn;
        }
    }

    @Override
    public Quote readQuote(Integer id) {
        Quote quote = null;
        Session session = Application.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            quote = session.get(Quote.class, id);
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            transaction.rollback();
        } finally {
            session.close();
            return quote;
        }
    }

    @Override
    public Quote updateQuote(Quote updatedQuote) {
        Quote quoteToReturn = null;
        Session session = Application.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(updatedQuote);
            transaction.commit();
            quoteToReturn = readQuote(updatedQuote.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
            transaction.rollback();
        } finally {
            session.close();
            return quoteToReturn;
        }
    }

    @Override
    public Boolean deleteQuote(Integer id) {
        Boolean returnValue = false;
        Session session = Application.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(readQuote(id));
            transaction.commit();
            returnValue = true;
        } catch (Exception e) {
            log.error(e.getMessage());
            transaction.rollback();
        } finally {
            session.close();
            return returnValue;
        }
    }

    @Override
    public List<Quote> listQuotes() {
        List<Quote> list = new ArrayList<>();
        Session session = Application.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            list = session.createQuery("from Quote").list();
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
    public Boolean performLike(Integer id) {
        Boolean returnValue = false;
        try {
            Quote quote = readQuote(id);
            if (quote != null) {
                quote.setLikes(quote.getLikes() + 1);
                updateQuote(quote);
                returnValue = true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            return returnValue;
        }
    }
}
