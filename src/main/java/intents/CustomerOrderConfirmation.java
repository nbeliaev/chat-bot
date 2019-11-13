package intents;

import com.google.actions.api.ActionContext;
import com.google.actions.api.ActionRequest;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

class CustomerOrderConfirmation extends AbstractIntentResponse {

    CustomerOrderConfirmation(ActionRequest request) {
        super(request);
    }

    @Override
    String prepareTextMessage() {
        Map<String, Object> parameters = new LinkedHashMap<>();
        for (ActionContext context : request.getContexts()) {
            if (context.getName().contains("customerorder-followup")) {
                parameters = context.getParameters();
                break;
            }
        }
        final String store = (String) parameters.get("shop");
        final Random random = new Random();
        final int orderNumber = random.nextInt();
        return String.format("Отлично, ваш заказ ждет вас в магазине %s. Номер заказа %d. Спасибо за обращение.", store, orderNumber);
    }
}
