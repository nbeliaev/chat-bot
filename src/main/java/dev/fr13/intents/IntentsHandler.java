package dev.fr13.intents;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import dev.fr13.intents.response.ParticularProduct;
import dev.fr13.intents.response.ProductList;
import dev.fr13.intents.response.ShopList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@SuppressWarnings("unused")
public class IntentsHandler extends DialogflowApp {
    private final static Logger log = LogManager.getLogger(IntentsHandler.class);

    @ForIntent("Shop List")
    public ActionResponse getAllShops(ActionRequest request) {
        log.info("Processing intent Shop List");
        final ShopList shopList = new ShopList(request);
        return shopList.getResponse();
    }

    @ForIntent("Product List")
    public ActionResponse getProductList(ActionRequest request) {
        log.info("Processing intent Product List");
        final ProductList productList = new ProductList(request);
        return productList.getResponse();
    }

    @ForIntent("Particular Product")
    public ActionResponse getProduct(ActionRequest request) {
        log.info("Processing intent Particular Product");
        final ParticularProduct product = new ParticularProduct(request);
        return product.getResponse();
    }
}
