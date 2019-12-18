package intents.response;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.SimpleResponse;

import java.util.Locale;
import java.util.Objects;

abstract class AbstractIntentResponse extends DialogflowApp {
    protected final ActionRequest request;
    protected final StringBuilder builder = new StringBuilder();
    protected final static String NEW_ROW = "\n";

    AbstractIntentResponse(ActionRequest request) {
        this.request = request;
    }

    public ActionResponse getResponse() {
        final ResponseBuilder responseBuilder = getResponseBuilder(request);
        final SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setDisplayText(prepareTextMessage());
        responseBuilder.add(simpleResponse);
        return responseBuilder.build();
    }

    protected Locale getLocale() {
        final String languageCode = Objects.requireNonNull(request.getWebhookRequest()).getQueryResult().getLanguageCode();
        return new Locale(languageCode);
    }

    abstract String prepareTextMessage();
}
