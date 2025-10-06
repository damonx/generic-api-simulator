package com.da.simulators.tools;

import org.springframework.http.HttpStatus;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

/**
 * A helper class for okhttp3's mock webserver - to simplify setting up the dispatcher
 * and mock responses in the case that you have multiple URLs and mocked responses
 * (potentially for different endpoints).
 */
public final class MockDispatcherSupport
{
    /**
     * A private constructor.
     */
    private MockDispatcherSupport()
    {
        // Do nothing.
    }

    /**
     * Given a list of request-response objects, add a dispatcher to the given mock server
     * that handles all those objects.
     *
     * @param mockServer The mock server.
     * @param requestResponses  The list of request-responses.
     */
    public static void setupDispatcher(final MockServerBean mockServer, final RequestResponses requestResponses)
    {
        final Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(final RecordedRequest request) throws InterruptedException
            {
                for (RequestResponses.RequestResponsePair requestResponse: requestResponses.getRequestResponseList()) {
                    if (requestResponse.getRequestMatcher().test(request)) {
                        if (requestResponse.getMockResponses().size() == 1) {
                            return requestResponse.getMockResponses().element();
                        } else {
                            return requestResponse.getMockResponses().remove();
                        }
                    }
                }
                return new MockResponse().setResponseCode(HttpStatus.NOT_FOUND.value());
            }
        };
        mockServer.getServer().setDispatcher(dispatcher);
    }
}

