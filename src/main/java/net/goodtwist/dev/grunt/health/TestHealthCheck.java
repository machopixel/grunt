package net.goodtwist.dev.grunt.health;

import com.codahale.metrics.health.HealthCheck;

public class TestHealthCheck extends HealthCheck {

    public TestHealthCheck() {
    }

    @Override
    protected Result check() throws Exception {
        if (true) {
            return Result.healthy();
        }
        return Result.unhealthy("Test healthcheck failed");
    }
}
