package com.da.simulators.generic_api_simulator.model;

import java.util.Set;

/**
 * The POJO of all mock response.
 */
public class GetAllMockResponse extends Response
{
    private Set<MockConfig> mockConfigs;

    /**
     * Setter.
     * @param mockConfigs
     */
    public void setMockConfigs(final Set<MockConfig> mockConfigs)
    {
        this.mockConfigs = mockConfigs;
    }
}
