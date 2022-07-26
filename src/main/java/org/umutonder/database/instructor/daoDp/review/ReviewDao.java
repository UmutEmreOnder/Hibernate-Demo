package org.umutonder.database.instructor.daoDp.review;

import org.umutonder.database.instructor.daoDp.BaseDao;

public interface ReviewDao extends BaseDao {
    void add(String comment, int courseId);
    void update(int id, String comment);
}
