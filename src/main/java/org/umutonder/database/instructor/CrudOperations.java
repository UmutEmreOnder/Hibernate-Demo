package org.umutonder.database.instructor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import org.umutonder.database.instructor.entity.*;

import javax.persistence.NoResultException;
import java.util.List;

public class CrudOperations {
    private static final SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Instructor.class)
            .addAnnotatedClass(InstructorDetail.class)
            .addAnnotatedClass(Course.class)
            .addAnnotatedClass(Review.class)
            .addAnnotatedClass(Student.class)
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

            Instructor instructor = getInstructor(instructorId, session);
            Course course = new Course(title);
            instructor.addCourse(course);

            session.save(course);

            session.getTransaction().commit();

            System.out.println("Course added successfully");
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to add course to non-exist instructor");
        }
    }

    public static void addReview(String comment, int courseId) {
        try {
            Session session = getCurrentSession();

            session.beginTransaction();

            Course course = getCourse(courseId, session);
            Review review = new Review(comment);
            course.addReview(review);

            session.save(review);

            session.getTransaction().commit();

            System.out.println("Review added successfully");
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to add review to non-exist course");
        }
    }

    public static void addStudent(String firstName, String lastName, String email, int... courseId) {
        Student student = new Student(firstName, lastName, email);

        Session session = getCurrentSession();
        session.beginTransaction();
        session.save(student);

        for (int id : courseId) {
            Course course = getCourse(id, session);
            course.addStudent(student);
        }

        commitTransaction(session);

        System.out.println("Student added successfully");
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

            InstructorDetail instructorDetail = getInstructorDetail(id, session);

            System.out.println(instructorDetail.getInstructor());
            System.out.println(instructorDetail);

            commitTransaction(session);
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to read non-exist id");
        }
    }

    public static void readCoursesOfInstructor(int instructorId) {
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

    public static void readCourses(int courseId) {
        Session session = getCurrentSession();
        session.beginTransaction();

        Course course = getCourse(courseId, session);
        System.out.println(course);
        printList(course.getStudents());

        session.getTransaction().commit();
    }

    public static void readReviews(int courseId) {
        try {
            Session session = getCurrentSession();
            session.beginTransaction();

            Course course = getCourse(courseId, session);
            printList(course.getReviews());

            session.getTransaction().commit();
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to read non-exist id");
        }
    }

    public static void readStudent(int studentId) {
        try {
            Session session = getCurrentSession();
            session.beginTransaction();

            Student student = getStudent(studentId, session);
            student.getCourses();

            System.out.println(student);
            printList(student.getCourses());

            commitTransaction(session);
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

            System.out.println("Instructor updated successfully");
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to update non-exist id");
        }
    }

    public static void updateStudent(int id, String newEmail) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            Student student = getStudent(id, session);
            student.setEmail(newEmail);

            commitTransaction(session);

            System.out.println("Student updated successfully");
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

            Course course = getCourse(id, session);

            session.delete(course);
            commitTransaction(session);

            System.out.println("Course deleted successfully");
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println("Attempt to delete non-exist id");
        }
    }

    public static void deleteReview(int id) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            Review review = getReview(id, session);

            session.delete(review);
            commitTransaction(session);

            System.out.println("Review deleted successfully");
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println("Attempt to delete non-exist id");
        }
    }

    public static void deleteStudent(int id) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            Student student = getStudent(id, session);
            session.delete(student);

            commitTransaction(session);

            System.out.println("Student deleted successfully");
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

    private static Course getCourse(int id, Session session) {
        return session.get(Course.class, id);
    }

    private static Review getReview(int id, Session session) {
        return session.get(Review.class, id);
    }

    private static Student getStudent(int id, Session session) {
        return session.get(Student.class, id);
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
