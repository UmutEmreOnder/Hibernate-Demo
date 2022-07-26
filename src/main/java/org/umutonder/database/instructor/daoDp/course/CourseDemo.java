package org.umutonder.database.instructor.daoDp.course;

import org.umutonder.database.instructor.entity.Course;

public class CourseDemo {
    public static void main(String[] args) {
        CourseDaoImp courseDaoImp = new CourseDaoImp();
        courseDaoImp.add("Java", 9);
        courseDaoImp.update(5, "C#");
        courseDaoImp.delete(3, Course.class);
        courseDaoImp.read(6);
        courseDaoImp.readAll(Course.class);
        courseDaoImp.readReviews(4);
    }
}
