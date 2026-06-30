package com.example.schedulemanagement.repository;

import com.example.schedulemanagement.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByOrderByUpdatedAtDesc();
    List<Schedule> findAllByAuthorOrderByUpdatedAtDesc(String author);
}