package com.flab.pokerunner.domain.entity;

import com.flab.pokerunner.domain.event.running.RunningStarted;
import com.flab.pokerunner.domain.event.running.RunningStopped;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_running")
@NoArgsConstructor
public class UserRunningJpo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public int userId;
    public String distanceMeter;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public String pace;

    public UserRunningJpo(RunningStarted events) {
        this.userId = events.getUserId();
        this.startTime = events.getStartTime();

        //달리기 시작 이벤트일 때는 전부 null 로 세팅
        this.distanceMeter = null;
        this.endTime = null;
        this.pace = null;
    }

    public UserRunningJpo(RunningStopped events) {
        this.userId = events.getUserId();
        this.distanceMeter = events.getTotalDistanceMeter().toString();
        this.endTime = events.getEndTime();
        this.pace = events.getPace();
    }
}
