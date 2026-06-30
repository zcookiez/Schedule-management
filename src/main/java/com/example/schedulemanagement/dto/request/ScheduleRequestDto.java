package com.example.schedulemanagement.dto.request;

import com.example.schedulemanagement.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleRequestDto {

    private String title;
    private String content;
    private String author;
    private String password;

    /**
     * 유효성 검증 로직
     */
    public void validate(boolean isUpdate) {
        // 1. 공통 필수 검증: 생성방식이든 수정방식이든 비밀번호는 무조건 필수!
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수 입력값입니다.");
        }

        // 2. 수정(Update) 시 검증 로직
        if (isUpdate) {
            if (title != null && title.length() > 30) {
                throw new IllegalArgumentException("일정 제목은 최대 30자 이내로 입력해주세요.");
            }
        }
        // 3. 생성(Create) 시 검증 로직
        else {
            if (title == null || title.isBlank()) {
                throw new IllegalArgumentException("일정 제목은 필수 입력값입니다.");
            }
            if (title.length() > 30) {
                throw new IllegalArgumentException("일정 제목은 최대 30자 이내로 입력해주세요.");
            }
            if (author == null || author.isBlank()) {
                throw new IllegalArgumentException("작성자명은 필수 입력값입니다.");
            }
            if (content == null || content.isBlank()) {
                throw new IllegalArgumentException("일정 내용은 필수 입력값입니다.");
            }
            if (content.length() > 200) {
                throw new IllegalArgumentException("일정 내용은 최대 200자 이내로 입력해주세요.");
            }
        }
    }

    public Schedule toEntity() {
        return Schedule.builder()
                .title(this.title)
                .content(this.content)
                .author(this.author)
                .password(this.password)
                .build();
    }
}