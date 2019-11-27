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

    @ForIntent("Product List")
    public ActionResponse getProductList(ActionRequest request) {
        final ProductList productList = new ProductList(request);
        return productList.getResponse();
    }

    @ForIntent("Particular Product")
    public ActionResponse getProduct(ActionRequest request) {
        final ParticularProduct product = new ParticularProduct(request);
        return product.getResponse();
    }
}
