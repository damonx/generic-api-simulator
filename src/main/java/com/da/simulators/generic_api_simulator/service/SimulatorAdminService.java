package com.da.simulators.generic_api_simulator.service;

import com.da.simulators.generic_api_simulator.exception.GenericApiSimulatorException;
import com.da.simulators.generic_api_simulator.model.MockConfig;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * Internal simulator service which is for admin.
 */
public interface SimulatorAdminService
{
    /**
     * Adds mock config.
     * @param mockConfig the {@link MockConfig} instance in request.
     */
    void addMock(MockConfig mockConfig);

    /**
     * Gets all configured mock configs.
     * @return a set of {@link MockConfig} instances.
     */
    Set<MockConfig> getAllMocks();

    /**
     * Removes all mock configs.
     */
    void clearMocks();

    /**
     * Deletes a mock config based on provided path and http method.
     * @param path the api path.
     * @param method the http method.
     */
    void deleteMock(String path, String method);

    /**
     * Finds an existing mock config by provided path and method.
     * @param path the api path.
     * @param method the http method.
     * @return the instance of {@link MockConfig}.
     */
    MockConfig findMock(String path, String method);

    /**
     * Converts the provided swagger 3.0 (Open API) file to a set of instances of {@link MockConfig}.
     * @param file the swagger document file.
     * @throws GenericApiSimulatorException when something went wrong.
     */
    void convertFromOpenApi(final MultipartFile file) throws GenericApiSimulatorException;
}
