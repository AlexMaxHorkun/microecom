package com.microecom.customerservice.http.controller;

import com.microecom.customerservice.model.Registration;
import com.microecom.customerservice.model.client.AuthClient;
import com.microecom.customerservice.model.data.SigningUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

    public static class GenerateInput {
        private Integer count;

        private Integer startingFrom;

        public GenerateInput() {
        }

        public Integer getCount() {
            return count;
        }

        public Integer getStartingFrom() {
            return startingFrom;
        }
    }

    private final AuthClient client;

    private final Registration registrationService;

    public PerfExperiment(@Autowired AuthClient client, @Autowired Registration registrationService) {
        this.client = client;
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<PerfResult> perfTest() {
        var started = LocalDateTime.now();
        var processed = client.processCustomers();

        return new ResponseEntity<>(new PerfResult(processed, Duration.between(started, LocalDateTime.now())), HttpStatus.OK);
    }

    @PostMapping(value = "/generate")
    public ResponseEntity<PerfResult> generate(@RequestBody GenerateInput input) {
        var executor = Executors.newFixedThreadPool(10);
        var counter = new AtomicInteger();
        var started = LocalDateTime.now();
        for (int i = 1; i <= input.getCount(); i++) {
            var id = i + input.getStartingFrom();
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    registrationService.register(new SigningUp() {
                        @Override
                        public String getLogin() {
                            return "test" + id;
                        }

                        @Override
                        public String getPassword() {
                            return "12345aBc";
                        }

                        @Override
                        public String getFirstName() {
                            return "test";
                        }

                        @Override
                        public String getLastName() {
                            return "test";
                        }

                        @Override
                        public String getEmail() {
                            return String.format("test__%d@test.com", id);
                        }

                        @Override
                        public Optional<String> getDefaultShippingAddress() {
                            return Optional.empty();
                        }

                        @Override
                        public Optional<String> getDefaultBillingAddress() {
                            return Optional.empty();
                        }
                    });
                    counter.addAndGet(1);
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.MINUTES);
        } catch (Throwable ex) {
            //Ok
        }

        return new ResponseEntity<>(new PerfResult(counter.get(), Duration.between(started, LocalDateTime.now())), HttpStatus.OK);
    }
}
