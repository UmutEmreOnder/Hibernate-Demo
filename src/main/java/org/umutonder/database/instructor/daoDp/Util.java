package org.umutonder.database.instructor.daoDp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.umutonder.database.instructor.entity.*;

import java.util.List;

public class Util {
    private static final SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Instructor.class)
            .addAnnotatedClass(InstructorDetail.class)
            .addAnnotatedClass(Course.class)
            .addAnnotatedClass(Review.class)
            .addAnnotatedClass(Student.class)
            .buildSessionFactory();

    public static void commitTransaction(Session session) {
        session.getTransaction().commit();
    }

    public static Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public static void printList(List<?> list) {
        for (Object o : list) {
            System.out.println(o);
        }
    }
}
