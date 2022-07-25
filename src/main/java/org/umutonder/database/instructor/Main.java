package org.umutonder.database.instructor;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        // CrudOperations.addInstructor("Umut Emre", "Onder", "umutemreonder@gmail.com", "TedeX", "Code"); // Will add instructor detail also. (Cascade)
        // CrudOperations.deleteInstructor(1); // Will delete the instructor detail of the instructor also. (Cascade and OneToOne)
        // CrudOperations.readInstructorDetail(4); // Will print the instructor also. (MappedBy)
        // CrudOperations.deleteInstructorDetails(7); // Will not remove instructor
        // CrudOperations.addCourse("Spring", 9); // Will link the course with the given instructor id
        // CrudOperations.deleteCourse(1); // Will not remove instructor
        // CrudOperations.readCourses(5); // Only prints the courses of the given instructor id
        // CrudOperations.readInstructors();
        // CrudOperations.addCourse("Java", 4);
        // CrudOperations.addReview("Java course!", 4);
        // CrudOperations.readReviews(4);
        // CrudOperations.deleteCourse(4); // Will remove the reviews also
        CrudOperations.addStudent("Umut Emre", "Onder", "umutemreonder@gmail.com",  3); // Will add the student to the course 2 and 3, also creates rows on class_student table
        // CrudOperations.addStudent("John", "Doe", "johndoe@gmail.com", 2, 3); // Will add the student to the course 2 and 3, also creates rows on class_student table
        // CrudOperations.readCourses(2); // Prints the info of the course and students
        // CrudOperations.readStudent(2); // Prints the info of the student and courses
        // CrudOperations.deleteCourse(2); // Will not remove students
        // CrudOperations.deleteStudent(11); // Will not remove courses
    }
}
