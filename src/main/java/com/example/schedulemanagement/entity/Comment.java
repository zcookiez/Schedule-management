package com.example.schedulemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부 생성을 막기 위해 protected 권장
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    @Column(nullable = false, length = 100)
    private String content;

    @Column(nullable = false, length = 50)
    private String author;

    @Column(nullable = false)
    private String password;

    @Builder
    private Comment(Long scheduleId, String content, String author, String password) {
        this.scheduleId = scheduleId;
        this.content = content;
        this.author = author;
        this.password = password;
    }

}