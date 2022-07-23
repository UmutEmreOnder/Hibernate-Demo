package org.umutonder.database.instructor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.umutonder.entity.Instructor;
import org.umutonder.entity.InstructorDetail;

import java.util.List;

public class CrudOperations {
    private static final SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Instructor.class)
            .addAnnotatedClass(InstructorDetail.class)
            .buildSessionFactory();

    public static void addInstructor(String firstName, String lastName, String email, String youtubeChannel, String hobby) {
        Session session = getCurrentSession();

        Instructor instructor = new Instructor(firstName, lastName, email);
        InstructorDetail instructorDetail = new InstructorDetail(youtubeChannel, hobby);
        instructor.setInstructorDetail(instructorDetail);

        session.beginTransaction();

        session.save(instructor);

        commitTransaction(session);

        System.out.println("Instructor successfully added");
    }


    public static void readInstructors() {
        Session session = getCurrentSession();

        session.beginTransaction();

        List instructors = session.createQuery("from Instructor").getResultList(); // List'e generic eklemeden nasil calisiyor?
        printList(instructors);

        commitTransaction(session);
    }

    public static void readInstructorDetail(int id) {
        try {
            Session session = getCurrentSession();
            session.beginTransaction();

            InstructorDetail instructorDetail = session.get(InstructorDetail.class, id);

            System.out.println(instructorDetail.getInstructor());
            System.out.println(instructorDetail);

            session.getTransaction().commit();
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to read non-exist id");
        }
    }

    public static void updateInstructor(int id, String newEmail) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            Instructor instructor = getInstructor(id, session);
            instructor.setEmail(newEmail);

            commitTransaction(session);

            System.out.println("Instructor successfully updated");
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to update non-exist id");
        }
    }

    public static void deleteInstructor(int id) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            Instructor instructor = getInstructor(id, session);
            session.delete(instructor);

            commitTransaction(session);

            System.out.println("Instructor successfully deleted");
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println("Attempt to delete non-exist id");
        }
    }

    public static void deleteInstructorDetails(int id) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            InstructorDetail instructorDetail = getInstructorDetail(id, session);
            session.delete(instructorDetail);

            commitTransaction(session);

            System.out.println("Instructor detail successfully deleted");
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println("Attempt to delete non-exist id");
        }
    }

    private static void commitTransaction(Session session) {
        session.getTransaction().commit();
    }

    private static Instructor getInstructor(int id, Session session) {
        return session.get(Instructor.class, id);
    }
    private static InstructorDetail getInstructorDetail(int id, Session session) {
        return session.get(InstructorDetail.class, id);
    }

    private static void printList(List<?> list) {
        for (Object o : list) {
            System.out.println(o);
        }
    }

    private static Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
