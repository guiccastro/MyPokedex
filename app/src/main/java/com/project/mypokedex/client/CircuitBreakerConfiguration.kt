package com.project.mypokedex.client

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import java.time.Duration

class CircuitBreakerConfiguration {

    private fun getConfiguration(): CircuitBreakerConfig = CircuitBreakerConfig.custom()
        .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
        .slidingWindowSize(10)
        .slowCallRateThreshold(70.0f)
        .failureRateThreshold(70.0f)
        .waitDurationInOpenState(Duration.ofSeconds(5))
        .slowCallDurationThreshold(Duration.ofSeconds(5))
        .permittedNumberOfCallsInHalfOpenState(3)
        .build()

    fun getCircuitBreaker(): CircuitBreaker = CircuitBreakerRegistry.of(getConfiguration())
        .circuitBreaker("circuit-breaker-pokemon")
}