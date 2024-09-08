package com.flab.pokerunner.controller.running;

import com.flab.pokerunner.domain.command.running.StartRunningCommand;
import com.flab.pokerunner.domain.command.running.StopRunningCommand;
import com.flab.pokerunner.service.running.RunningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RunningController {

    private final RunningService runningService;

    @PostMapping("/start-running")
    public ResponseEntity<String> startRunning(@RequestBody StartRunningCommand command) {
        runningService.start(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/stop-running")
    public ResponseEntity<String> stopRunning(@RequestBody StopRunningCommand command) {
        runningService.stop(command);
        return ResponseEntity.ok().build();
    }
}
