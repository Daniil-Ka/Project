package org.basicprogramming.db;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Collection;
import java.util.List;

public class DB {
    private static SessionFactory sessionFactory;

    public static void init() {
        try {
            sessionFactory = new Configuration().
                    configure().
                    buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session openSession() {
        if (sessionFactory == null) {
            init();
        }
        return sessionFactory.openSession();
    }

    public static void addRecord(Object object) {
        try (Session session = openSession()) {
            session.beginTransaction();
            session.persist(object);
            session.getTransaction().commit();
        }
    }

    public static void mergeRecord(Object object) {
        try (Session session = openSession()) {
            session.beginTransaction();
            session.merge(object);
            session.getTransaction().commit();
        }
    }

    public static <T> void addRecords(Collection<T> objects) {
        try (Session session = openSession()) {
            session.beginTransaction();

            for (var object : objects) {
                session.persist(object);
            }

            session.getTransaction().commit();
        }
    }

    public static <T> List<T> loadAllData(Class<T> type) {
        try (Session session = openSession()) {
            session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(type);
            criteria.from(type);
            List<T> data = session.createQuery(criteria).getResultList();

            session.getTransaction().commit();
            return data;
        }
    }
}