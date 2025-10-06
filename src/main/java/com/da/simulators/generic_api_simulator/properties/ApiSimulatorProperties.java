package com.da.simulators.generic_api_simulator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@ConfigurationProperties(prefix = "api-simulator")
@Validated
@Component
public class ApiSimulatorProperties
{
    @Min(1)
    @Max(20)
    private int restConnectionTimeoutInSeconds = 1;
    @Min(1)
    @Max(20)
    private int restReadTimeoutInSeconds = 1;

    public void setRestConnectionTimeoutInSeconds(final int restConnectionTimeoutInSeconds)
    {
        this.restConnectionTimeoutInSeconds = restConnectionTimeoutInSeconds;
    }

    public void setRestReadTimeoutInSeconds(final int restReadTimeoutInSeconds)
    {
        this.restReadTimeoutInSeconds = restReadTimeoutInSeconds;
    }

    public int getRestConnectionTimeoutInSeconds()
    {
        return restConnectionTimeoutInSeconds;
    }

    public int getRestReadTimeoutInSeconds()
    {
        return restReadTimeoutInSeconds;
    }
}
