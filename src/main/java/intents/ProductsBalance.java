package intents;

import com.google.actions.api.ActionRequest;
import database.dao.Dao;
import database.dao.ProductDaoImpl;
import database.entities.ProductEntity;

import java.util.List;

class ProductsBalance extends AbstractIntentResponse {

    ProductsBalance(ActionRequest request) {
        super(request);
    }

    @Override
    String prepareTextMessage() {
        final String activeIngredient = (String) request.getParameter("activeIngredient");
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
        return stringBuilder.toString();
    }
}
