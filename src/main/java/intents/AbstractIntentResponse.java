package intents;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.SimpleResponse;

abstract class AbstractIntentResponse extends DialogflowApp {
    ActionRequest request;

    AbstractIntentResponse(ActionRequest request) {
        this.request = request;
    }

    ActionResponse getResponse() {
        final ResponseBuilder responseBuilder = getResponseBuilder(request);
        final SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setDisplayText(prepareTextMessage());
        responseBuilder.add(simpleResponse);
        return responseBuilder.build();
    }

    abstract String prepareTextMessage();
}
