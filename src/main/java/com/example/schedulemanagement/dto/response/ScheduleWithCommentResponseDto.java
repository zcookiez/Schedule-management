package com.example.schedulemanagement.dto.response;

import com.example.schedulemanagement.entity.Schedule;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ScheduleWithCommentResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final List<CommentResponseDto> comments;

    public ScheduleWithCommentResponseDto(Schedule schedule, List<CommentResponseDto> comments) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.author = schedule.getAuthor();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
        this.comments = comments;
    }
}