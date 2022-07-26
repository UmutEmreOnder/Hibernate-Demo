package org.umutonder.database.instructor.daoDp.review;

import org.hibernate.Session;
import org.umutonder.database.instructor.daoDp.Util;
import org.umutonder.database.instructor.entity.Course;
import org.umutonder.database.instructor.entity.Review;

public class ReviewDaoImp implements ReviewDao {
    @Override
    public void read(int id) {
        Session session = Util.getCurrentSession();
        session.beginTransaction();

        Review review = get(id, Review.class, session);
        System.out.println(review);

        Util.commitTransaction(session);
    }


    @Override
    public void add(String comment, int courseId) {
        try {
            Session session = Util.getCurrentSession();

            session.beginTransaction();

            Course course = get(courseId, Course.class, session);
            Review review = new Review(comment);
            course.addReview(review);

            session.save(review);

            Util.commitTransaction(session);

            System.out.println("Review added successfully");
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to add review to non-exist course");
        }
    }

    @Override
    public void update(int id, String comment) {
        try (Session session = Util.getCurrentSession()) {
            session.beginTransaction();

            Review review = get(id, Review.class, session);
            review.setComment(comment);

            Util.commitTransaction(session);

            System.out.println("Review updated successfully");
        } catch (NullPointerException | IllegalStateException exception) {
            System.out.println("Attempt to update non-exist id");
        }
    }
}
