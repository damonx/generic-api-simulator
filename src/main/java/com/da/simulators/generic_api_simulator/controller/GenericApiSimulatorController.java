package com.da.simulators.generic_api_simulator.controller;

import com.da.simulators.generic_api_simulator.exception.GenericApiSimulatorException;
import com.da.simulators.generic_api_simulator.service.SimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Generic api simulator controller.
 */
@RestController
public class GenericApiSimulatorController
{
    private final SimulatorService simulatorService;

    @Autowired
    public GenericApiSimulatorController(final SimulatorService simulatorService)
    {
        this.simulatorService = simulatorService;
    }

    @RequestMapping("/**")
    public ResponseEntity<?> catchAll(final HttpServletRequest request) throws GenericApiSimulatorException
    {
        final String path = request.getRequestURI();
        final String method = request.getMethod();
        return simulatorService.processWildcardRequests(path, method);
    }
}
