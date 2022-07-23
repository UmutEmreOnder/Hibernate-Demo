package org.umutonder.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.umutonder.entity.Employee;

import java.util.List;

public class CrudOperations {
    private static final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Employee.class).buildSessionFactory();

    public static void addEmployee(String firstName, String lastName, String company) {
        Session session = getCurrentSession();

        Employee employee = new Employee(firstName, lastName, company);

        session.beginTransaction();
        session.save(employee);

        commitTransaction(session);

        System.out.println("Employee successfully added");
    }

    public static void readEmployees() {
        Session session = getCurrentSession();

        session.beginTransaction();

        List employees = session.createQuery("from Employee ").getResultList(); // List'e generic eklemeden nasil calisiyor?
        printList(employees);

        commitTransaction(session);
    }

    public static void updateEmployee(int id, String newCompany) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            Employee employee = getEmployee(id, session);
            employee.setCompany(newCompany);

            commitTransaction(session);

            System.out.println("Employee successfully updated");
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to update non-exist id");
        }
    }

    public static void deleteEmployee(int id) {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            Employee employee = getEmployee(id, session);
            session.delete(employee);

            commitTransaction(session);

            System.out.println("Employee successfully deleted");
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println("Attempt to delete non-exist id");
        }
    }

    private static void commitTransaction(Session session) {
        session.getTransaction().commit();
    }

    private static Employee getEmployee(int id, Session session) {
        return session.get(Employee.class, id);
    }

    private static void printList(List<?> list) {
        for (Object o : list) {
            System.out.println(o);
        }
    }

    private static Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
