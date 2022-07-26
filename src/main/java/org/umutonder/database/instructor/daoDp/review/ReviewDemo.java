package org.umutonder.database.instructor.daoDp.review;

import org.umutonder.database.instructor.entity.Review;

public class ReviewDemo {
    public static void main(String[] args) {
        ReviewDaoImp reviewDaoImp = new ReviewDaoImp();
        reviewDaoImp.add("Best C# Course!", 5);
        reviewDaoImp.read(5);
        reviewDaoImp.update(5, "It's OK");
        reviewDaoImp.delete(5, Review.class);
        reviewDaoImp.readAll(Review.class);
    }
}
