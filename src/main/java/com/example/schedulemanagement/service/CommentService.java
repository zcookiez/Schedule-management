package com.example.schedulemanagement.service;

import com.example.schedulemanagement.dto.request.CommentRequestDto;
import com.example.schedulemanagement.dto.response.CommentResponseDto;
import com.example.schedulemanagement.entity.Comment;
import com.example.schedulemanagement.repository.CommentRepository;
import com.example.schedulemanagement.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository; // 일정이 진짜 있는지 확인하기 위해 필요함

    @Transactional
    public CommentResponseDto createComment(Long scheduleId, CommentRequestDto requestDto) {

        // 1.  유효성 검사
        requestDto.validate();

        // 2. 해당 일정이 DB에 존재하는지 먼저 검증
        scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정은 존재하지 않습니다."));

        // 3. 댓글 갯수 제한 확인 (최대 10개)
        long commentCount = commentRepository.countByScheduleId(scheduleId);
        if (commentCount >= 10) {
            throw new IllegalArgumentException("하나의 일정에는 최대 10개의 댓글만 등록할 수 있습니다.");
        }

        // 4. Entity로 변환
        Comment comment = requestDto.toEntity(scheduleId);

        // 5. DB에 저장
        Comment savedComment = commentRepository.save(comment);

        // 6. Response DTO로 포장해서 반환
        return new CommentResponseDto(savedComment);
    }
}