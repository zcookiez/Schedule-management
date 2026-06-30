package com.example.schedulemanagement.service;

import com.example.schedulemanagement.dto.request.ScheduleRequestDto;
import com.example.schedulemanagement.dto.response.CommentResponseDto;
import com.example.schedulemanagement.dto.response.ScheduleResponseDto;
import com.example.schedulemanagement.dto.response.ScheduleWithCommentResponseDto;
import com.example.schedulemanagement.entity.Comment;
import com.example.schedulemanagement.entity.Schedule;
import com.example.schedulemanagement.repository.CommentRepository;
import com.example.schedulemanagement.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    /*전체 일정 조회 로직*/
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getAllSchedules(String author) {
        List<Schedule> scheduleList;

        // 1. author 파라미터가 없거나(Null), 텅 빈 공백 문자열("")이면 전체 조회
        if (author == null || author.trim().isEmpty()) {
            scheduleList = scheduleRepository.findAllByOrderByUpdatedAtDesc();

        // 2. author 값이 있으면 해당 작성자의 일정만 조회
        } else {
            scheduleList = scheduleRepository.findAllByAuthorOrderByUpdatedAtDesc(author);
        }

        // 2. 가져온 Entity 리스트를 DTO 리스트로 변환하여 반환
        return scheduleList.stream()
                .map(ScheduleResponseDto::new) // Entity를 DTO로 변환
                .collect(Collectors.toList());
    }

    /* 선택 일정 단건 조회 (댓글 포함) */
    @Transactional(readOnly = true)
    public ScheduleWithCommentResponseDto getSchedule(Long id) {
        // 1. 일정 정보 조회
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정은 존재하지 않습니다."));

        // 2. 해당 일정의 댓글 목록 조회
        List<Comment> commentList = commentRepository.findAllByScheduleId(id);

        // 3. 엔티티 리스트 -> DTO 리스트로 변환
        List<CommentResponseDto> commentResponseDtoList = commentList.stream()
                                                        .map(CommentResponseDto::new)
                                                        .collect(Collectors.toList());

        // 4. 하나의 큰 DTO 상자에 일정과 댓글 리스트를 조립하여 반환
        return new ScheduleWithCommentResponseDto(schedule, commentResponseDtoList);
    }

    /* 일정 생성 로직*/
    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {

        // 1. 유효성 검사
        requestDto.validate(false);

        // 2. 요청받은 DTO 데이터를 바탕으로 Entity 객체 생성
        Schedule schedule = requestDto.toEntity();

        // 3.  DB에 저장
        // save() 메서드는 저장 후, DB에서 세팅된 ID(Auto_Increment)와 시간 등이 채워진 완벽한 Entity를 돌려준다.
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // 4. 저장된 Entity를 ResponseDto 상자에 담아서 리턴
        return new ScheduleResponseDto(savedSchedule);
    }

    /*일정 수정 로직*/
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {

        // 1. 유효성 검사
        requestDto.validate(true);

        // 2. DB에서 수정할 일정을 조회 (없으면 예외 발생)
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정은 존재하지 않습니다."));

        // 3. 비밀번호 일치 여부를 확인
        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 4. 엔티티의 데이터를 수정
        schedule.update(requestDto.getTitle(), requestDto.getAuthor());

        // 5. 수정된 엔티티를 DTO로 포장해서 반환
        return new ScheduleResponseDto(schedule);
    }

    /*일정 삭제 로직*/
    @Transactional
    public void deleteSchedule(Long id, String password) {

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }

        // 1. DB에서 삭제할 일정을 조회
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정은 존재하지 않습니다."));


        // 2. 비밀번호 일치 여부를 확인
        if (!schedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 삭제
        scheduleRepository.delete(schedule);

    }
}

