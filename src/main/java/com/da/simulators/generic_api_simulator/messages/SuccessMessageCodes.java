package com.da.simulators.generic_api_simulator.messages;

import com.da.simulators.generic_api_simulator.model.Message;

/**
 * All success code and messages.
 */
public final class SuccessMessageCodes
{
    public static final Message ADD_MOCK_SUCCESS = new Message("New mock added successfully.", 2000);
    public static final Message GET_ALL_MOCK_SUCCESS = new Message("Get all mocks successfully.", 2001);
    public static final Message MOCK_DELETED_SUCCESS = new Message("Mock deleted successfully.", 2002);
    public static final Message SWAGGER_CONVERTED_SUCCESS = new Message("Mock deleted successfully.", 2003);

    /**
     * Never instantiate me.
     */
    private SuccessMessageCodes()
    {
    }
}
