package org.umutonder.database.instructor.daoDp.student;

import org.umutonder.database.instructor.daoDp.BaseDao;

public interface StudentDao extends BaseDao {
    void add(String firstName, String lastName, String email, int... courseId);
    void update(int id, String email);
}
