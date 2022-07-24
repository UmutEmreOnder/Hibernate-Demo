package org.umutonder.database.instructor.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@RequiredArgsConstructor
@ToString(onlyExplicitlyIncluded = true)

@Getter
@Setter

@Entity
@Table(name = "instructor_detail")
public class InstructorDetail {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ToString.Include
    @NonNull
    @Column(name = "youtube_channel")
    private String youtubeChannel;

    @ToString.Include
    @NonNull
    @Column(name = "hobby")
    private String hobby;

    @OneToOne(mappedBy = "instructorDetail", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Instructor instructor;
}
