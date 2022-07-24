package org.umutonder.database.employee;

public class Main {
    public static void main(String[] args) {
        System.out.println("-- Before --");
        CrudOperations.readEmployees();

        System.out.println("\n-- Add Employee --");
        CrudOperations.addEmployee("Kemal","Buyuk", "Apple");

        System.out.println("\n-- Update Employee --");
        CrudOperations.updateEmployee(3, "Spotify");

        System.out.println("\n-- Delete Employee --");
        CrudOperations.deleteEmployee(3);

        System.out.println("\n-- After --");
        CrudOperations.readEmployees();
    }
}
