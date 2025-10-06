package com.da.simulators.tools;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * A portion of an initializer for integration test classes - for the purposes of getting a port for
 * downstream API calls.
 * Extensions of this should override initialize(ConfigurableApplicationContext).
 */
public abstract class ConfigInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>
{
    /**
     * Returns an available TCP port assigned by current operating system.
     * Important note: repeated calls to this will *not* return the same port; there is also
     * the slim chance of a race condition.
     *
     * @return a random currently unused port for testing.
     */
    protected int getTestPort()
    {
        int testPort;
        try {
            try (ServerSocket socket = new ServerSocket(0)) {
                testPort = socket.getLocalPort();
            }
        } catch (final IOException e) {
            throw new IllegalStateException("Could not get server port", e);
        }
        return testPort;
    }

    /**
     * Builds a {@link TestPropertyValues} instance containing test properties for a mock server.
     * <p>
     * This method assigns a random available port number to a mock server and creates a set of key-value
     * properties for Spring test configuration. It maps the given endpoint keys to a local URL using the
     * generated port number (e.g., {@code http://localhost:12345}). Additionally, it includes the
     * {@code mockserver.test.port} property to explicitly define the selected port.
     *
     * @param endpointKeys a non-null list of configuration keys representing endpoint property names that should
     *                     be mapped to the local mock server URL.
     * @return a {@code TestPropertyValues} object containing the constructed properties.
     */
    protected TestPropertyValues buildTestPropertyValues(final List<String> endpointKeys)
    {
        final String randomPort = String.valueOf(getTestPort());
        return TestPropertyValues.of(endpointKeys.stream(), endpointKey -> TestPropertyValues.Pair.of(endpointKey, "http://localhost:" + randomPort))
            .and("mockserver.test.port=" + randomPort);
    }

    /**
     * Builds a {@link TestPropertyValues} instance containing mock server configuration properties,
     * with two groups of keys mapped to two different randomly assigned ports.
     *
     * @param endpointKeys   a non-null list of configuration keys mapped to a local mock server URL using the first random port
     * @param extraPortKeys  a non-null list of configuration keys that are mapped directly to the second random port (as a string)
     * @return a {@code TestPropertyValues} object containing all constructed key-value property mappings
     */
    protected TestPropertyValues buildTestPropertyValues(final List<String> endpointKeys, final List<String> extraPortKeys)
    {
        final String firstRandomPort = String.valueOf(getTestPort());
        final String secondRandomPort = String.valueOf(getTestPort());

        return TestPropertyValues.of(endpointKeys.stream(), endpointKey -> TestPropertyValues.Pair.of(endpointKey, "http://localhost:" + firstRandomPort))
            .and("mockserver.test.port=" + firstRandomPort)
            .and(extraPortKeys.stream(), extraPortKey -> TestPropertyValues.Pair.of(extraPortKey, secondRandomPort));
    }
}
