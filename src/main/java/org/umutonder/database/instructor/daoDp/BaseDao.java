package org.umutonder.database.instructor.daoDp;

import org.hibernate.Session;

import java.util.List;

public interface BaseDao {
    void read(int id);

    default <T> T get(int id, Class<T> tClass, Session session) {
        return session.get(tClass, id);
    }
    default <T> void delete(int id, Class<T> tClass) {
        try (Session session = Util.getCurrentSession()) {
            session.beginTransaction();

            T object = get(id, tClass, session);
            session.delete(object);

            Util.commitTransaction(session);

            System.out.println(tClass.getSimpleName() + " deleted successfully");
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println("Attempt to delete non-exist id");
        }
    }

    default <T> void readAll(Class<T> tClass) {
        Session session = Util.getCurrentSession();

        session.beginTransaction();

        List list = session.createQuery("from " + tClass.getSimpleName()).getResultList(); // List'e generic eklemeden nasil calisiyor?
        Util.printList(list);

        Util.commitTransaction(session);
    }
}
