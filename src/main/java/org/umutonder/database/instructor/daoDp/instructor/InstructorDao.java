package org.umutonder.database.instructor.daoDp.instructor;

import org.umutonder.database.instructor.daoDp.BaseDao;

public interface InstructorDao extends BaseDao {
    void add(String firstName, String lastName, String email, String youtubeChannel, String hobby);
    void update(int id, String email);

    void readCourses(int id);
}
