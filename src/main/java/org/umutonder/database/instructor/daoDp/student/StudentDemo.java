package org.umutonder.database.instructor.daoDp.student;

import org.umutonder.database.instructor.entity.Review;
import org.umutonder.database.instructor.entity.Student;

public class StudentDemo {
    public static void main(String[] args) {
        StudentDaoImp studentDaoImp = new StudentDaoImp();
        studentDaoImp.add("Ali", "Berkay", "ali.berkay@obss.com.tr", 5, 6, 7);
        studentDaoImp.read(10);
        studentDaoImp.update(10, "umut.onder@obss.com.tr");
        studentDaoImp.delete(13, Student.class);
        studentDaoImp.readAll(Student.class);
    }
}
