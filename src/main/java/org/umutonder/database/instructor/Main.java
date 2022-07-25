package org.umutonder.database.instructor;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        CrudOperations crud = new CrudOperations();

        crud.readInstructorDetail(5);
    }
}
