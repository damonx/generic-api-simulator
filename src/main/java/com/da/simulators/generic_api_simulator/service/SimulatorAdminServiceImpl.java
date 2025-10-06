package com.da.simulators.generic_api_simulator.service;

import com.da.simulators.generic_api_simulator.exception.GenericApiSimulatorException;
import com.da.simulators.generic_api_simulator.messages.ErrorMessageCodes;
import com.da.simulators.generic_api_simulator.model.MockConfig;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link SimulatorAdminService}
 */
@Service
public class SimulatorAdminServiceImpl implements SimulatorAdminService
{
    private static Logger LOGGER = LogManager.getLogger(SimulatorAdminServiceImpl.class);
    private static final String EMPTY_JSON_RESPONSE = "{}";
    private final Set<MockConfig> configs = new CopyOnWriteArraySet<>();

    @Override
    public void addMock(final MockConfig mockConfig)
    {
        // TODO: add validation on path & method, etc.
        if (mockConfig != null && !StringUtils.isAnyBlank(mockConfig.getMethod(), mockConfig.getPath())) {
            LOGGER.info("Adding mock config having path {}", mockConfig.getPath());
            configs.add(mockConfig);
        }
    }

    @Override
    public Set<MockConfig> getAllMocks()
    {
        return configs;
    }

    @Override
    public void clearMocks()
    {
        LOGGER.info("Clearing mocks");
        configs.clear();
    }

    @Override
    public void deleteMock(final String path, final String method)
    {
        LOGGER.info("Deleting mock config having path {} and method {}", path, method);
        // TODO: add validation on path & method.
        configs.removeIf(config -> config.getPath().equals(path) && config.getMethod().equals(method));
    }

    @Override
    public MockConfig findMock(final String path, final String method)
    {
        // TODO: add validation on path & method.
        LOGGER.info("Looking for mock config based on path {} and method {}", path, method);
        return configs.stream()
            .filter(c -> method.equalsIgnoreCase(c.getMethod()))
            .filter(c -> {
                if (c.isRegex()) {
                    return path.matches(c.getPath());
                }
                return path.equals(c.getPath());
            })
            .findFirst()
            .orElse(null);
    }

    @Override
    public void convertFromOpenApi(final MultipartFile file) throws GenericApiSimulatorException
    {

        try {
            final File tempFile = File.createTempFile("openapi", ".yaml");
            file.transferTo(tempFile);
            final OpenAPI openAPI = new OpenAPIV3Parser().read(tempFile.getAbsolutePath());
            if (openAPI == null || MapUtils.isEmpty(openAPI.getPaths())) {
                throw new GenericApiSimulatorException("", HttpStatus.BAD_REQUEST, ErrorMessageCodes.INVALID_OPENAPI_FILE);
            }

            configs.addAll(openAPI.getPaths().entrySet().stream()
                .flatMap(entry -> {
                    String path = entry.getKey();
                    PathItem pathItem = entry.getValue();

                    return pathItem.readOperationsMap().entrySet().stream()
                        .map(opEntry -> {
                            final PathItem.HttpMethod httpMethod = opEntry.getKey();

                            final String mockBody = extractMockBody(opEntry.getValue());

                            final MockConfig config = new MockConfig();
                            config.setPath(path);
                            config.setMethod(httpMethod.name());
                            config.setResponseBody(mockBody);
                            return config;
                        });
                })
                .collect(Collectors.toSet()));
            if (tempFile.delete()) {
                LOGGER.info("Deleted temp file {} {}", tempFile.getAbsolutePath(), tempFile.getName());
            }
        } catch (final IOException ioe) {
            throw new GenericApiSimulatorException("Uploaded file cannot be saved on server.", HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessageCodes.FILE_NOT_CREATED, ioe);
        }
    }

    private String extractMockBody(final Operation operation) {
        final ApiResponses apiResponses = operation.getResponses();
        if (MapUtils.isEmpty(apiResponses) || CollectionUtils.isEmpty(apiResponses.values())) {
            return EMPTY_JSON_RESPONSE;
        }

        final ApiResponse successResponse = ObjectUtils.defaultIfNull(apiResponses.get(String.valueOf(HttpStatus.OK.value())), apiResponses.values().iterator().next());

        if (successResponse == null || successResponse.getContent() == null || successResponse.getContent().isEmpty()) {
            return EMPTY_JSON_RESPONSE;
        }

        return CollectionUtils.emptyIfNull(successResponse.getContent().values()).stream()
            .map(mediaType -> {
                if (mediaType.getExample() != null) {
                    return mediaType.getExample().toString();
                }
                final Map<String, Example> examples = mediaType.getExamples();
                if (MapUtils.isNotEmpty(examples) && CollectionUtils.isNotEmpty(examples.values())) {
                    return examples.values().iterator().next().getValue().toString();
                }
                return EMPTY_JSON_RESPONSE;
            })
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(EMPTY_JSON_RESPONSE);
    }
}
