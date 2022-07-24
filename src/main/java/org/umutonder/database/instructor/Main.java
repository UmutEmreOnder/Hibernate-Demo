package org.umutonder.database.instructor;

public class Main {
    public static void main(String[] args) {
        // CrudOperations.addInstructor("Umut Emre", "Onder", "umutemreonder@gmail.com", "TedeX", "Code"); // Will add instructor detail also. (Cascade)
        // CrudOperations.deleteInstructor(1); // Will delete the instructor detail of the instructor also. (Cascade and OneToOne)
        // CrudOperations.readInstructors();
        // CrudOperations.readInstructorDetail(4); // Will print the instructor also. (MappedBy)
        // CrudOperations.deleteInstructorDetails(7);

        CrudOperations.readInstructors();
    }
}
