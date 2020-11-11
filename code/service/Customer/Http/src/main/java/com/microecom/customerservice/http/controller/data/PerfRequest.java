package com.microecom.customerservice.http.controller.data;

public class PerfRequest {
    private int threads;

    public PerfRequest(int threads) {
        this.threads = threads;
    }

    protected PerfRequest() {}

    public int getThreads() {
        return threads;
    }
}
