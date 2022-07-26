package org.umutonder.database.instructor.daoDp.instructor;

import org.umutonder.database.instructor.entity.Instructor;

public class InstructorDemo {
    public static void main(String[] args) {
        InstructorDaoImp instructorDaoImp = new InstructorDaoImp();
        instructorDaoImp.add("Berker", "Gurcu", "berker.gurcu@obss.com.tr", "bgurcu", "architect");
        instructorDaoImp.read(8);
        instructorDaoImp.update(8, "updated@gmail.com");
        instructorDaoImp.delete(4, Instructor.class);
        instructorDaoImp.readAll(Instructor.class);
        instructorDaoImp.readCourses(9);
    }
}
