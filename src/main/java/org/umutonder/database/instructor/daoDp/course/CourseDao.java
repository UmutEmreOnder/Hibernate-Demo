package org.umutonder.database.instructor.daoDp.course;

import org.umutonder.database.instructor.daoDp.BaseDao;
import org.umutonder.database.instructor.entity.Course;

public interface CourseDao extends BaseDao {
    void add(String title, int instructorId);
    void update(int id, String title);
    void readReviews(int courseId);
}
