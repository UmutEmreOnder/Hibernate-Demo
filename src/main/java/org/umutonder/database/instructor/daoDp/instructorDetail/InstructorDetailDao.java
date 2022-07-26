package org.umutonder.database.instructor.daoDp.instructorDetail;

import org.umutonder.database.instructor.daoDp.BaseDao;

public interface InstructorDetailDao extends BaseDao {
    void update(int id, String youtubeChannel);
}
