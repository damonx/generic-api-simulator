package com.da.simulators.generic_api_simulator.service;

import static com.da.simulators.generic_api_simulator.messages.ErrorMessageCodes.MOCK_NOT_FOUND;

import com.da.simulators.generic_api_simulator.exception.GenericApiSimulatorException;
import com.da.simulators.generic_api_simulator.model.MockConfig;
import com.da.simulators.generic_api_simulator.properties.ApiSimulatorProperties;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;

/**
 * Default implementation of {@link SimulatorService}.
 */
@Service
public class SimulatorServiceImpl implements SimulatorService
{
    private static final Logger LOGGER = LogManager.getLogger(SimulatorServiceImpl.class);
    private final SimulatorAdminService simulatorAdminService;
    private final ApiSimulatorProperties apiSimulatorProperties;

    @Autowired
    public SimulatorServiceImpl(final SimulatorAdminService simulatorAdminService, final ApiSimulatorProperties apiSimulatorProperties)
    {
        this.simulatorAdminService = simulatorAdminService;
        this.apiSimulatorProperties = apiSimulatorProperties;
    }

    @Override
    public ResponseEntity<String> processWildcardRequests(final String path, final String method) throws GenericApiSimulatorException
    {
        final MockConfig mockConfig = simulatorAdminService.findMock(path, method);
        if (mockConfig == null) {
            throw new GenericApiSimulatorException("Provided mock config in request does not exist.", HttpStatus.NOT_FOUND, List.of(MOCK_NOT_FOUND));
        }

        if (mockConfig.isForward()) {
            LOGGER.info("Forwarding current api http request to the target server {} and api path {}", mockConfig.getForwardBaseUrl(), mockConfig.getPath());
            final RestTemplate restTemplate = new RestTemplateBuilder()
                .connectTimeout(Duration.ofSeconds(apiSimulatorProperties.getRestConnectionTimeoutInSeconds()))
                .readTimeout(Duration.ofSeconds(apiSimulatorProperties.getRestReadTimeoutInSeconds()))
                .build();
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            final HttpEntity<String> entity = new HttpEntity<>(mockConfig.getForwardRequestBody(), headers);
            return restTemplate.exchange(
                mockConfig.getForwardBaseUrl().concat(mockConfig.getPath()),
                HttpMethod.valueOf(method),
                entity,
                String.class
            );
        } else {
            try {
                LOGGER.info("Simulating api call delay.");
                Thread.sleep(Duration.ofMillis(mockConfig.getDelayMillis()));
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            return ResponseEntity
                .status(mockConfig.getStatusCode())
                .contentType(ObjectUtils.defaultIfNull(MediaType.parseMediaType(mockConfig.getContentType()), MediaType.APPLICATION_JSON))
                .body(mockConfig.getResponseBody());
        }
    }
}
