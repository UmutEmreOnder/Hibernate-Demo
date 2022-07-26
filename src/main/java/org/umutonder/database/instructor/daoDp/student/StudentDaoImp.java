package org.umutonder.database.instructor.daoDp.student;

import org.hibernate.Session;
import org.umutonder.database.instructor.daoDp.Util;
import org.umutonder.database.instructor.entity.Course;
import org.umutonder.database.instructor.entity.Student;

public class StudentDaoImp implements StudentDao {
    @Override
    public void read(int id) {
        Session session = Util.getCurrentSession();
        session.beginTransaction();

        Student student = get(id, Student.class, session);
        System.out.println(student);

        Util.commitTransaction(session);
    }


    @Override
    public void add(String firstName, String lastName, String email, int... courseId) {
        Student student = new Student(firstName, lastName, email);

        Session session = Util.getCurrentSession();
        session.beginTransaction();
        session.save(student);

        for (int id : courseId) {
            Course course = get(id, Course.class, session);
            course.addStudent(student);
        }

        Util.commitTransaction(session);

        System.out.println("Student added successfully");
    }

    @Override
    public void update(int id, String email) {
        try (Session session = Util.getCurrentSession()) {
            session.beginTransaction();

            Student student = get(id, Student.class, session);
            student.setEmail(email);

            Util.commitTransaction(session);

            System.out.println("Student updated successfully");
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to update non-exist id");
        }
    }
}
