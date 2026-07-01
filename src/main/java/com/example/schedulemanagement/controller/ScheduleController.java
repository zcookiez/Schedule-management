package com.example.schedulemanagement.controller;

import com.example.schedulemanagement.dto.request.CommentRequestDto;
import com.example.schedulemanagement.dto.request.ScheduleRequestDto;
import com.example.schedulemanagement.dto.response.CommentResponseDto;
import com.example.schedulemanagement.dto.response.ScheduleResponseDto;
import com.example.schedulemanagement.dto.response.ScheduleWithCommentResponseDto;
import com.example.schedulemanagement.service.CommentService;
import com.example.schedulemanagement.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules") // 이 컨트롤러의 기본 주소를 "/schedules"로 고정
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final CommentService commentService;

    /*전체 일정 조회 API*/
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(@RequestParam(required = false) String author) {
        List<ScheduleResponseDto> scheduleList = scheduleService.getAllSchedules(author);

        for (ScheduleResponseDto schedule : scheduleList) {
            log.info(" 👉 {}", schedule);
        }
        log.info("======================================");

        return ResponseEntity.ok(scheduleList); // 200 OK
    }

    /*선택 일정 단건 조회 API (댓글 포함)*/
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleWithCommentResponseDto> getSchedule(@PathVariable Long id) {
        ScheduleWithCommentResponseDto responseDto = scheduleService.getSchedule(id);
        List<CommentResponseDto> comments = responseDto.getComments();

        log.info("======================================");
        log.info(" 👉 상세 정보: {}", responseDto);
        for (CommentResponseDto commnet : comments) {
            log.info(" 👉 일정의 댓글 {}", commnet);
        }
        log.info("======================================");

        return ResponseEntity.ok(responseDto); // 200 OK
    }

    /*일정 생성 API*/
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto);

        log.info("======================================");
        log.info(" 👉 저장할 상세 정보: {}", requestDto);
        log.info(" 👉 등록된 상세 정보: {}", responseDto);
        log.info("======================================");

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto); // 201 Created
    }

    /*일정 수정 API*/
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        ScheduleResponseDto responseDto = scheduleService.updateSchedule(id, requestDto);

        log.info("======================================");
        log.info("🔄 일정 수정 성공! [ID: {}]", id);
        log.info(" 👉 수정 요청 데이터: {}", requestDto);
        log.info(" 👉 수정 완료 데이터: {}", responseDto);
        log.info("======================================");

        return ResponseEntity.ok(responseDto); // 200 OK
    }

    /*일정 삭제 API*/
    @PostMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        scheduleService.deleteSchedule(id, requestDto.getPassword());

        // 컨트롤러에서 예쁘게 로그 찍기
        log.info("======================================");
        log.info("🗑️ 일정 삭제 성공! [ID: {}]", id);
        log.info("======================================");

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{scheduleId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long scheduleId,
            @RequestBody CommentRequestDto requestDto) {

        CommentResponseDto responseDto = commentService.createComment(scheduleId, requestDto);

        log.info("======================================");
        log.info(" 💬 댓글 생성 성공! [Schedule ID: {}]", scheduleId);
        log.info(" 👉 생성된 댓글 내용: {}", requestDto.getContent());
        log.info("======================================");

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto); // 201 Created
    }



}