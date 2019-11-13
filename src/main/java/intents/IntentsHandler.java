package intents;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;

@SuppressWarnings("unused")
public class IntentsHandler extends DialogflowApp {

    @ForIntent("Shops")
    public ActionResponse getAllShops(ActionRequest request) {
        final ShopsSchedule shopsSchedule = new ShopsSchedule(request);
        return shopsSchedule.getResponse();
    }

    @ForIntent("Active Ingredient")
    public ActionResponse getProductListByActiveIngredient(ActionRequest request) {
        final ProductsBalance productsBalance = new ProductsBalance(request);
        return productsBalance.getResponse();
    }

    @ForIntent("Customer Order Confirm")
    public ActionResponse createCustomerOrder(ActionRequest request) {
        final CustomerOrderConfirmation confirmation = new CustomerOrderConfirmation(request);
        return confirmation.getResponse();
    }
}
