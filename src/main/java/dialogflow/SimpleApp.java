package dialogflow;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;

@SuppressWarnings("unused")
public class SimpleApp extends DialogflowApp {

    @ForIntent("Default Welcome Intent")
    public ActionResponse welcome(ActionRequest request) {
        final ResponseBuilder builder = getResponseBuilder(request);
        builder.add("Hello, Google!");
        return builder.build();
    }
}
