package dialogflow;

import com.google.actions.api.*;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.SimpleResponse;
import database.dao.Dao;
import database.dao.ProductDaoImpl;
import database.dao.StoreDaoImpl;
import database.entities.ProductEntity;
import database.entities.StoreEntity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("unused")
public class IntentsHandler extends DialogflowApp {

    @ForIntent("Shops")
    public ActionResponse getAllShops(ActionRequest request) {
        final ResponseBuilder responseBuilder = getResponseBuilder(request);
        Dao<StoreEntity> dao = new StoreDaoImpl();
        final List<StoreEntity> stores = dao.getAll(StoreEntity.class);
        final StringBuilder stringBuilder = new StringBuilder();
        stores.forEach(entity -> {
            stringBuilder.append(entity.getName())
                    .append("\n")
                    .append("- Часы работы: Пн-Пт, 8-18")
                    .append("\n")
                    .append("- Т: +79115554875")
                    .append("\n")
                    .append("\n");
        });
        final SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setDisplayText(stringBuilder.toString());
        responseBuilder.add(simpleResponse);
        return responseBuilder.build();
    }

    @ForIntent("Active Ingredient")
    public ActionResponse getProductListByActiveIngredient(ActionRequest request) {
        final String activeIngredient = (String) request.getParameter("activeIngredient");
        final ResponseBuilder responseBuilder = getResponseBuilder(request);
        final Dao<ProductEntity> dao = new ProductDaoImpl();
        final List<ProductEntity> analogs = dao.findByPattern(ProductEntity.class, "activeIngredient", activeIngredient);
        final StringBuilder stringBuilder = new StringBuilder();
        analogs.forEach(product -> {
            stringBuilder.append(product.getName())
                    .append("\n");
            product.getStores().forEach(store -> {
                stringBuilder
                        .append("- ")
                        .append(store.getName())
                        .append("\n");
            });
        });
        final SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setDisplayText(stringBuilder.toString());
        responseBuilder.add(simpleResponse);
        return responseBuilder.build();
    }

    @ForIntent("Customer Order Confirm")
    public ActionResponse createCustomerOrder(ActionRequest request) {
        Map<String, Object> parameters = new LinkedHashMap<>();
        for (ActionContext context : request.getContexts()) {
            if (context.getName().contains("customerorder-followup")) {
                parameters = context.getParameters();
                break;
            }
        }
        final String store = (String) parameters.get("store");
        final ResponseBuilder responseBuilder = getResponseBuilder(request);
        final SimpleResponse simpleResponse = new SimpleResponse();
        final Random random = new Random();
        final int orderNumber = random.nextInt();
        final String msg = String.format("Отлично, ваш заказ ждет вас в магазине %s. Номер заказа %d. Спасибо за обращение.", store, orderNumber);
        simpleResponse.setDisplayText(msg);
        responseBuilder.add(simpleResponse);
        return responseBuilder.build();
    }
}
