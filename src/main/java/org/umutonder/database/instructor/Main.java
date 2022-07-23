package org.umutonder.database.instructor;

public class Main {
    public static void main(String[] args) {
        CrudOperations.addInstructor("Umut", "Fidan", "umutfidan@gmail.com", "UFdn", "Chess"); // Will add instructor detail also. (Cascade)
        // CrudOperations.deleteInstructor(1); // Will delete the instructor detail of the instructor also. (Cascade and OneToOne)
        CrudOperations.readInstructors();
        // CrudOperations.readInstructorDetail(4);
        // CrudOperations.deleteInstructorDetails(4);
    }
}
