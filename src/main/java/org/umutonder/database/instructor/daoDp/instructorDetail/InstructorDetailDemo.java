package org.umutonder.database.instructor.daoDp.instructorDetail;

import org.umutonder.database.instructor.entity.InstructorDetail;

public class InstructorDetailDemo {
    public static void main(String[] args) {
        InstructorDetailDaoImp instructorDetailDaoImp = new InstructorDetailDaoImp();
        instructorDetailDaoImp.read(12);
        instructorDetailDaoImp.update(11, "Umut");
        instructorDetailDaoImp.delete(8, InstructorDetail.class);
        instructorDetailDaoImp.readAll(InstructorDetail.class);
    }
}
