package intents;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import intents.response.ParticularProduct;
import intents.response.ProductList;
import intents.response.ShopList;
import intents.response.ShopsMap;

@SuppressWarnings("unused")
public class IntentsHandler extends DialogflowApp {

    @ForIntent("Shop List")
    public ActionResponse getAllShops(ActionRequest request) {
        final ShopList shopList = new ShopList(request);
        return shopList.getResponse();
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

    @ForIntent("Show Shops Map")
    public ActionResponse getShopsMap(ActionRequest request) {
        final ShopsMap shopsMap = new ShopsMap(request);
        return shopsMap.getResponse();
    }
}
