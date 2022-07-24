package org.umutonder.database.instructor;

public class Main {
    public static void main(String[] args) {
        // CrudOperations.addInstructor("Umut Emre", "Onder", "umutemreonder@gmail.com", "TedeX", "Code"); // Will add instructor detail also. (Cascade)
        // CrudOperations.deleteInstructor(1); // Will delete the instructor detail of the instructor also. (Cascade and OneToOne)
        // CrudOperations.readInstructorDetail(4); // Will print the instructor also. (MappedBy)
        // CrudOperations.deleteInstructorDetails(7); // Will not remove instructor
        // CrudOperations.addCourse("Spring", 9); // Will link the course with the given instructor id
        // CrudOperations.deleteCourse(1); // Will not remove instructor
        // CrudOperations.readCourses(8); // Only prints the courses of the given instructor id

        CrudOperations.readInstructors();
    }
}
