package com.da.simulators.generic_api_simulator;

import com.da.simulators.tools.ConfigInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class ApiSimulatorConfigInitializer extends ConfigInitializer
{
    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext)
    {
        final List<String> endpointKeys = List.of("api-simulator.acutal.server.endpoint");
        buildTestPropertyValues(endpointKeys).applyTo(applicationContext);
    }
}
