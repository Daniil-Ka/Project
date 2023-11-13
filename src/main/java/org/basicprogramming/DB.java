package org.basicprogramming;

import org.basicprogramming.db.models.Group;
import org.basicprogramming.db.models.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;

public class DB {
    private static SessionFactory sessionFactory;

    public static void main() {
        try {
            sessionFactory = new Configuration().
                    configure().
                    buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        Session session = sessionFactory.openSession();
//CREATE
        saveStudentRecord(session);
        saveStudentRecord(session);
        //READ
        fetchStudentRecord(session);
        session.close();
    }

    private static void deleteStudentRecord(Session session) {
        int id = 6;
        Student student1 = (Student) session.get(Student.class, id);
        session.beginTransaction();
        session.delete(student1);
        session.getTransaction().commit();
    }

    private static void fetchStudentRecord(Session session) {
        Query query = session.createQuery("FROM Student");
        List<Student> Students = query.list();
        Students.forEach(obj -> System.out.println(obj.getFirstName()));
        System.out.println("Reading student records...");
    }

    private static void saveStudentRecord(Session session) {
        session.beginTransaction();

        var g = new Group();
        g.setGroupName("asd");
        session.save(g);
        session.getTransaction().commit();

        session.beginTransaction();
        Student student1 = new Student();
        student1.setFirstName("Jason");
        student1.setLastName("Roy");
        student1.setGroup(g);

        Student student2 = new Student();
        student2.setFirstName("Jason2");
        student2.setLastName("Roy2");
        student2.setGroup(g);

        session.save(student1);
        session.save(student2);
        session.getTransaction().commit();
    }
}