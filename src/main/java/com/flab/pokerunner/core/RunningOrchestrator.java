package com.flab.pokerunner.core;

import com.flab.pokerunner.domain.entity.UserRunningJpo;
import com.flab.pokerunner.domain.event.running.RunningStarted;
import com.flab.pokerunner.domain.event.running.RunningStopped;
import com.flab.pokerunner.service.running.RunningRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RunningOrchestrator {

    private final GateWay gateWay;
    private final RunningRecordService runningRecordService;

    @EventListener
    public void on(RunningStarted event) {
        log.info("달리기 시작 이벤트 :{}", event);
        runningRecordService.save(new UserRunningJpo(event));
    }

    @EventListener
    public void on(RunningStopped event) {
        log.info("달리기 종료 이벤트:{}", event);
        runningRecordService.save(new UserRunningJpo(event));
    }
}
