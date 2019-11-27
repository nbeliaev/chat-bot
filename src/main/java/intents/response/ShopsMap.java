package intents.response;

import com.google.actions.api.ActionRequest;

public class ShopsMap extends AbstractIntentResponse{

    public ShopsMap(ActionRequest request) {
        super(request);
    }

    @Override
    String prepareTextMessage() {
        return "dummy";
    }
}
