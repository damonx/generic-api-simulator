package com.da.simulators.generic_api_simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Entry class for the generic api simulator.
 */
@SpringBootApplication(scanBasePackageClasses = GenericApiSimulatorApplication.class)
public class GenericApiSimulatorApplication extends SpringBootServletInitializer
{
	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application)
	{
		return application.sources(GenericApiSimulatorApplication.class);
	}

	/**
	 * Entry point for the Generic Api Simulator.
	 * @param args Provided command line arguments.
	 */
	public static void main(String[] args)
	{
		SpringApplication.run(GenericApiSimulatorApplication.class, args);
	}
}
