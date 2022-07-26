package org.umutonder.database.instructor.daoDp.instructorDetail;

import org.hibernate.Session;
import org.umutonder.database.instructor.daoDp.Util;
import org.umutonder.database.instructor.entity.Instructor;
import org.umutonder.database.instructor.entity.InstructorDetail;

public class InstructorDetailDaoImp implements InstructorDetailDao {
    @Override
    public void read(int id) {
        Session session = Util.getCurrentSession();
        session.beginTransaction();

        InstructorDetail instructorDetail = get(id, InstructorDetail.class, session);
        System.out.println(instructorDetail);

        Util.commitTransaction(session);
    }


    @Override
    public void update(int id, String youtubeChannel) {
        try (Session session = Util.getCurrentSession()) {
            session.beginTransaction();

            InstructorDetail instructorDetail = get(id, InstructorDetail.class, session);
            instructorDetail.setYoutubeChannel(youtubeChannel);

            Util.commitTransaction(session);

            System.out.println("Instructor Detail updated successfully");
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to update non-exist id");
        }
    }

    @Override
    public <T> void delete(int id, Class<T> tClass) {
        try (Session session = Util.getCurrentSession()) {
            session.beginTransaction();

            InstructorDetail instructorDetail = get(id, InstructorDetail.class, session);
            Instructor instructor = instructorDetail.getInstructor();
            instructor.setInstructorDetail(null);
            session.delete(instructorDetail);

            Util.commitTransaction(session);

            System.out.println(tClass.getSimpleName() + " deleted successfully");
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println("Attempt to delete non-exist id");
        }
    }
}
