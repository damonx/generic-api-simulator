package com.da.simulators.generic_api_simulator.controller;

import com.da.simulators.generic_api_simulator.exception.GenericApiSimulatorException;
import com.da.simulators.generic_api_simulator.messages.SuccessMessageCodes;
import com.da.simulators.generic_api_simulator.model.GetAllMockResponse;
import com.da.simulators.generic_api_simulator.model.Message;
import com.da.simulators.generic_api_simulator.model.MockConfig;
import com.da.simulators.generic_api_simulator.model.Response;
import com.da.simulators.generic_api_simulator.service.SimulatorAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import jakarta.validation.Valid;

/**
 * The api simulator admin controller.
 */
@RestController
@RequestMapping("/simulator-admin")
public class GenericApiSimulatorAdminController
{

    @Autowired
    private SimulatorAdminService simulatorAdminService;


    @PostMapping("/addmock")
    public ResponseEntity<Response> addMock(@RequestBody @Valid MockConfig mockConfig)
    {
        simulatorAdminService.addMock(mockConfig);
        return buildGenericSuccessResponse(SuccessMessageCodes.ADD_MOCK_SUCCESS);
    }

    @GetMapping("/allmocks")
    public ResponseEntity<GetAllMockResponse> getAll()
    {
        final GetAllMockResponse allMocksResponse = new GetAllMockResponse();
        allMocksResponse.setMockConfigs(simulatorAdminService.getAllMocks());
        allMocksResponse.setMessages(List.of(SuccessMessageCodes.GET_ALL_MOCK_SUCCESS));
        return ResponseEntity.ok(allMocksResponse);
    }

    @DeleteMapping("/deletemock")
    public ResponseEntity<Response>  deleteMock(@RequestBody @Valid MockConfig mockConfig)
    {
        simulatorAdminService.deleteMock(mockConfig.getPath(), mockConfig.getMethod());
        return buildGenericSuccessResponse(SuccessMessageCodes.MOCK_DELETED_SUCCESS);
    }

    @PostMapping("/upload")
    public ResponseEntity<Response> uploadOpenApi(@RequestParam("file") MultipartFile file) throws GenericApiSimulatorException
    {
        simulatorAdminService.convertFromOpenApi(file);
        return buildGenericSuccessResponse(SuccessMessageCodes.SWAGGER_CONVERTED_SUCCESS);
    }

    /**
     * Builds a generic success response.
     * @return the instance of {@link ResponseEntity}.
     */
    private ResponseEntity<Response> buildGenericSuccessResponse(final Message successMessage)
    {
        final Response response = new Response();
        response.getMessages().add(successMessage);
        return ResponseEntity.ok(response);
    }
}
