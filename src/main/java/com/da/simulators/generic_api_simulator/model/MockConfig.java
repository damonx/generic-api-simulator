package com.da.simulators.generic_api_simulator.model;

import lombok.Data;
import lombok.Getter;

import java.util.Map;
import jakarta.validation.constraints.NotBlank;

/**
 * Pojo which holds all mock configuration data.
 */
@Data
public class MockConfig
{
    @NotBlank
    private String path;
    @NotBlank
    private String method;
    @NotBlank
    private String responseBody;
    private String contentType = "application/json";
    private int statusCode = 200;
    private long delayMillis = 0;
    private Map<String, String> queryParams;
    @Getter
    private boolean regex = false;
    @Getter
    private boolean forward = false;
    private String forwardBaseUrl;
    private String forwardRequestBody;
}
