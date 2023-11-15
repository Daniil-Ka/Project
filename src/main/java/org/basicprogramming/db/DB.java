package org.basicprogramming.db;

import org.basicprogramming.db.models.Group;
import org.basicprogramming.db.models.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
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

    public static void addRecord(Object object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(object);
            session.getTransaction().commit();
        }
    }

    private static void fetchStudentRecord(Session session) {
        Query query = session.createQuery("FROM Student");
        List<Student> Students = query.list();
        Students.forEach(obj -> System.out.println(obj.getFirstName()));
        System.out.println("Reading student records...");
    }
}