package com.example.schedulemanagement.dto.request;

import com.example.schedulemanagement.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    private String content;
    private String author;
    private String password;

    /**
     * 유효성 검증 로직
     */
    public void validate() {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("댓글 내용은 필수 입력값입니다.");
        }
        if (content.length() > 100) {
            throw new IllegalArgumentException("댓글 내용은 최대 100자 이내로 입력해주세요.");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("작성자명은 필수 입력값입니다.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수 입력값입니다.");
        }
    }

    public Comment toEntity(Long scheduleId) {
        return Comment.builder()
                .scheduleId(scheduleId)
                .content(this.content)
                .author(this.author)
                .password(this.password)
                .build();
    }
}