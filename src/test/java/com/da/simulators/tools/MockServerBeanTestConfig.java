package com.da.simulators.tools;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Base configuration class for integration tests that need a mock web server.
 * This contains both configuration of the mock server, as well as customisation
 * of the web test client.
 */
@TestConfiguration
public class MockServerBeanTestConfig extends TestConfigBase
{
    /**
     * @return a MockServerBean.
     */
    @Bean
    public MockServerBean mockServer()
    {
        return new MockServerBean();
    }
}
