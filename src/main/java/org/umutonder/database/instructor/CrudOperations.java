package org.umutonder.database.instructor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import org.umutonder.database.instructor.entity.*;

import javax.persistence.NoResultException;
import java.util.List;

public class CrudOperations {
    private final SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Instructor.class)
            .addAnnotatedClass(InstructorDetail.class)
            .addAnnotatedClass(Course.class)
            .addAnnotatedClass(Review.class)
            .addAnnotatedClass(Student.class)
            .buildSessionFactory();

    public void addInstructor(String firstName, String lastName, String email, String youtubeChannel, String hobby) {
        Session session = getCurrentSession();

        Instructor instructor = new Instructor(firstName, lastName, email);
        InstructorDetail instructorDetail = new InstructorDetail(youtubeChannel, hobby);
        instructor.setInstructorDetail(instructorDetail);

        session.beginTransaction();

        session.save(instructor);

        commitTransaction(session);

        System.out.println("Instructor added successfully");
    }

    public void addCourse(String title, int instructorId) {
        try {
            Session session = getCurrentSession();

            session.beginTransaction();

            Instructor instructor = get(instructorId, Instructor.class, session);
            Course course = new Course(title);
            instructor.addCourse(course);

            session.save(course);

            session.getTransaction().commit();

            System.out.println("Course added successfully");
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to add course to non-exist instructor");
        }
    }

    public void addReview(String comment, int courseId) {
        try {
            Session session = getCurrentSession();

            session.beginTransaction();

            Course course = get(courseId, Course.class, session);
            Review review = new Review(comment);
            course.addReview(review);

            session.save(review);

            session.getTransaction().commit();

            System.out.println("Review added successfully");
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to add review to non-exist course");
        }
    }

    public void addStudent(String firstName, String lastName, String email, int... courseId) {
        Student student = new Student(firstName, lastName, email);

        Session session = getCurrentSession();
        session.beginTransaction();
        session.save(student);

        for (int id : courseId) {
            Course course = get(id, Course.class, session);
            course.addStudent(student);
        }

        commitTransaction(session);

        System.out.println("Student added successfully");
    }


    public void readInstructors() {
        Session session = getCurrentSession();

        session.beginTransaction();

        List instructors = session.createQuery("from Instructor").getResultList(); // List'e generic eklemeden nasil calisiyor?
        printList(instructors);

        commitTransaction(session);
    }

    public void readInstructorDetail(int id) {
        try {
            Session session = getCurrentSession();
            session.beginTransaction();

            InstructorDetail instructorDetail = get(id, InstructorDetail.class, session);

            System.out.println(instructorDetail);

            commitTransaction(session);
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to read non-exist id");
        }
    }

    public void readCoursesOfInstructor(int instructorId) {
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

    public void readCourses(int courseId) {
        Session session = getCurrentSession();
        session.beginTransaction();

        Course course = get(courseId, Course.class, session);
        System.out.println(course);
        printList(course.getStudents());

        session.getTransaction().commit();
    }

    public void readReviews(int courseId) {
        try {
            Session session = getCurrentSession();
            session.beginTransaction();

            Course course = get(courseId, Course.class, session);
            printList(course.getReviews());

            session.getTransaction().commit();
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to read non-exist id");
        }
    }

    public void readStudent(int studentId) {
        try {
            Session session = getCurrentSession();
            session.beginTransaction();

            Student student = get(studentId, Student.class, session);
            student.getCourses();

            System.out.println(student);
            printList(student.getCourses());

            commitTransaction(session);
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to read non-exist id");
        }
    }

    public void updateInstructor(int id, String newEmail) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            Instructor instructor = get(id, Instructor.class, session);
            instructor.setEmail(newEmail);

            commitTransaction(session);

            System.out.println("Instructor updated successfully");
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to update non-exist id");
        }
    }

    public void updateStudent(int id, String newEmail) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            Student student = get(id, Student.class, session);
            student.setEmail(newEmail);

            commitTransaction(session);

            System.out.println("Student updated successfully");
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to update non-exist id");
        }
    }

    public void deleteInstructor(int id) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            Instructor instructor = get(id, Instructor.class, session);
            session.delete(instructor);

            commitTransaction(session);

            System.out.println("Instructor deleted successfully");
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println("Attempt to delete non-exist id");
        }
    }

    public void deleteInstructorDetails(int id) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            InstructorDetail instructorDetail = get(id, InstructorDetail.class, session);
            instructorDetail.getInstructor().setInstructorDetail(null);

            session.delete(instructorDetail);
            commitTransaction(session);

            System.out.println("Instructor detail deleted successfully");
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println("Attempt to delete non-exist id");
        }
    }

    public void deleteCourse(int id) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            Course course = get(id, Course.class, session);

            session.delete(course);
            commitTransaction(session);

            System.out.println("Course deleted successfully");
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println("Attempt to delete non-exist id");
        }
    }

    public void deleteReview(int id) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            Review review = get(id, Review.class, session);

            session.delete(review);
            commitTransaction(session);

            System.out.println("Review deleted successfully");
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println("Attempt to delete non-exist id");
        }
    }

    public void deleteStudent(int id) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            Student student = get(id, Student.class, session);
            session.delete(student);

            commitTransaction(session);

            System.out.println("Student deleted successfully");
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println("Attempt to delete non-exist id");
        }
    }

    private void commitTransaction(Session session) {
        session.getTransaction().commit();
    }

    private <T> T get(int id, Class<T> tClass, Session session) {
        return session.get(tClass, id);
    }

    private void printList(List<?> list) {
        for (Object o : list) {
            System.out.println(o);
        }
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
