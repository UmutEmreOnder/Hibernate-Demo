package org.umutonder.database.instructor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.umutonder.entity.Course;
import org.umutonder.entity.Instructor;
import org.umutonder.entity.InstructorDetail;

import javax.persistence.NoResultException;
import java.util.List;

public class CrudOperations {
    private static final SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Instructor.class)
            .addAnnotatedClass(InstructorDetail.class)
            .addAnnotatedClass(Course.class)
            .buildSessionFactory();

    public static void addInstructor(String firstName, String lastName, String email, String youtubeChannel, String hobby) {
        Session session = getCurrentSession();

        Instructor instructor = new Instructor(firstName, lastName, email);
        InstructorDetail instructorDetail = new InstructorDetail(youtubeChannel, hobby);
        instructor.setInstructorDetail(instructorDetail);

        session.beginTransaction();

        session.save(instructor);

        commitTransaction(session);

        System.out.println("Instructor added successfully");
    }

    public static void addCourse(String title, int instructorId) {
        try {
            Session session = getCurrentSession();

            session.beginTransaction();

            Instructor instructor = session.get(Instructor.class, instructorId);
            Course course = new Course(title);
            instructor.addCourse(course);

            session.save(course);

            session.getTransaction().commit();

            System.out.println("Course added successfully");
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to add course to non-exist instructor");
        }
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

            commitTransaction(session);
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to read non-exist id");
        }
    }

    public static void readCourses(int instructorId) {
        try {
            Session session = getCurrentSession();
            session.beginTransaction();

            Query<Instructor> query = session.createQuery("SELECT i FROM Instructor i JOIN FETCH i.courses WHERE i.id =: theInstructorId", Instructor.class);
            query.setParameter("theInstructorId", instructorId);

            Instructor instructor = query.getSingleResult();

            commitTransaction(session);

            printList(instructor.getCourses());
        } catch (NullPointerException | IllegalStateException | NoResultException exception) {
            System.out.println("Attempt to read non-exist id or the instructor has no courses");
        }
    }

    public static void updateInstructor(int id, String newEmail) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            Instructor instructor = getInstructor(id, session);
            instructor.setEmail(newEmail);

            commitTransaction(session);

            System.out.println("Instructor updated successfully");
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

            System.out.println("Instructor deleted successfully");
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println("Attempt to delete non-exist id");
        }
    }

    public static void deleteInstructorDetails(int id) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            InstructorDetail instructorDetail = getInstructorDetail(id, session);
            instructorDetail.getInstructor().setInstructorDetail(null);

            session.delete(instructorDetail);
            commitTransaction(session);

            System.out.println("Instructor detail deleted successfully");
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println("Attempt to delete non-exist id");
        }
    }

    public static void deleteCourse(int id) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            Course course = session.get(Course.class, id);

            session.delete(course);
            commitTransaction(session);

            System.out.println("Course deleted successfully");
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
