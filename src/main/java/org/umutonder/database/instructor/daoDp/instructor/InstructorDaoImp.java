package org.umutonder.database.instructor.daoDp.instructor;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.umutonder.database.instructor.daoDp.Util;
import org.umutonder.database.instructor.entity.Instructor;
import org.umutonder.database.instructor.entity.InstructorDetail;

import javax.persistence.NoResultException;

public class InstructorDaoImp implements InstructorDao {
    @Override
    public void read(int id) {
        Session session = Util.getCurrentSession();
        session.beginTransaction();

        Instructor instructor = get(id, Instructor.class, session);
        System.out.println(instructor);

        Util.commitTransaction(session);
    }


    @Override
    public void add(String firstName, String lastName, String email, String youtubeChannel, String hobby) {
        Session session = Util.getCurrentSession();

        Instructor instructor = new Instructor(firstName, lastName, email);
        InstructorDetail instructorDetail = new InstructorDetail(youtubeChannel, hobby);

        instructor.setInstructorDetail(instructorDetail);

        session.beginTransaction();
        session.save(instructor);

        Util.commitTransaction(session);

        System.out.println("Instructor added successfully");
    }

    @Override
    public void update(int id, String email) {
        try (Session session = Util.getCurrentSession()) {
            session.beginTransaction();

            Instructor instructor = get(id, Instructor.class, session);
            instructor.setEmail(email);

            Util.commitTransaction(session);

            System.out.println("Instructor updated successfully");
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to update non-exist id");
        }
    }

    @Override
    public void readCourses(int id) {
        try {
            Session session = Util.getCurrentSession();
            session.beginTransaction();

            Query<Instructor> query = session.createQuery("SELECT i FROM Instructor i JOIN FETCH i.courses WHERE i.id =: theInstructorId", Instructor.class);
            query.setParameter("theInstructorId", id);

            Instructor instructor = query.getSingleResult();

            Util.commitTransaction(session);

            Util.printList(instructor.getCourses());
        } catch (NullPointerException | IllegalStateException | NoResultException exception) {
            System.out.println("Attempt to read non-exist id or the instructor has no courses");
        }
    }
}
