package com.da.simulators.generic_api_simulator.messages;

import com.da.simulators.generic_api_simulator.model.Message;

/**
 * Error message codes.
 */
public class ErrorMessageCodes
{
    public static final Message MOCK_NOT_FOUND = new Message("Mock data not found.", 4000);
    public static final Message INVALID_OPENAPI_FILE = new Message("Invalid OpenAPI specification.", 4001);

    public static final Message FILE_NOT_CREATED = new Message("File cannot be created on server.", 5001);
    /**
     * Never initiate me.
     */
    private ErrorMessageCodes()
    {
    }
}
