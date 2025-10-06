package com.da.simulators.generic_api_simulator.service;

import com.da.simulators.generic_api_simulator.exception.GenericApiSimulatorException;
import org.springframework.http.ResponseEntity;

/**
 * The internal service which handles different types of http requests from clients.
 */
public interface SimulatorService
{

    /**
     * Processes different types of api calls as http requests.
     * @param path the path of the api.
     * @param method the http method of the api.
     * @return The response containing the pre-configured json response.
     * @throws GenericApiSimulatorException when something went wrong.
     */
    ResponseEntity<String> processWildcardRequests(String path, String method) throws GenericApiSimulatorException;
}
