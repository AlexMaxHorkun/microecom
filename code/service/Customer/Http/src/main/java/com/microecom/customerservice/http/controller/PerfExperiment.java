package com.microecom.customerservice.http.controller;

import com.microecom.customerservice.http.controller.data.PerfRequest;
import com.microecom.customerservice.model.client.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/rest/V1/customer/perf")
public class PerfExperiment {
    public final class PerfResult {
        private final long processed;

        private final long ms;

        private final long s;

        public PerfResult(long processed, Duration took) {
            this.processed = processed;
            this.ms = took.toMillis();
            this.s = took.toSeconds();
        }

        public long getProcessed() {
            return processed;
        }

        public long getMs() {
            return ms;
        }

        public long getS() {
            return s;
        }
    }
    private final AuthClient client;

    public PerfExperiment(@Autowired AuthClient client) {
        this.client = client;
    }

    @PostMapping
    public ResponseEntity<PerfResult> perfTest(@RequestBody PerfRequest request) {
        var started = LocalDateTime.now();
        var processed = client.processCustomers(request.getThreads());

        return new ResponseEntity<>(new PerfResult(processed, Duration.between(started, LocalDateTime.now())), HttpStatus.OK);
    }
}
