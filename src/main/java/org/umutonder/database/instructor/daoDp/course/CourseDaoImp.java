package org.umutonder.database.instructor.daoDp.course;

import org.hibernate.Session;
import org.umutonder.database.instructor.daoDp.Util;
import org.umutonder.database.instructor.entity.Course;
import org.umutonder.database.instructor.entity.Instructor;

public class CourseDaoImp implements CourseDao {
    @Override
    public void read(int id) {
        Session session = Util.getCurrentSession();
        session.beginTransaction();

        Course course = get(id, Course.class, session);
        System.out.println(course);
        Util.printList(course.getStudents());

        Util.commitTransaction(session);
    }

    @Override
    public void add(String title, int instructorId) {
        try {
            Session session = Util.getCurrentSession();
            session.beginTransaction();

            Instructor instructor = get(instructorId, Instructor.class, session);
            Course course = new Course(title);
            instructor.addCourse(course);

            session.save(course);
            Util.commitTransaction(session);

            System.out.println("Course added successfully");
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to add course to non-exist instructor");
        }
    }

    @Override
    public void update(int id, String title) {
        try (Session session = Util.getCurrentSession()) {
            session.beginTransaction();

            Course course = get(id, Course.class, session);
            course.setTitle(title);

            Util.commitTransaction(session);

            System.out.println("Course updated successfully");
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to update non-exist id");
        }
    }

    @Override
    public void readReviews(int courseId) {
        try {
            Session session = Util.getCurrentSession();
            session.beginTransaction();

            Course course = get(courseId, Course.class, session);
            Util.printList(course.getReviews());

            Util.commitTransaction(session);
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to read non-exist id");
        }
    }
}
