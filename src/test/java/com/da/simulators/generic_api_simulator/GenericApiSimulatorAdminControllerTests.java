package com.da.simulators.generic_api_simulator;

import com.da.simulators.tools.MockServerBean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@DisplayName("Integration tests for the admin controller.")
@Tag("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = MockServerBean.class, initializers = ApiSimulatorConfigInitializer.class)
@TestPropertySource(locations = {"/integration-test.properties"})
class GenericApiSimulatorAdminControllerTests
{

	@Test
	void contextLoads()
	{
	}

}
