package intents;

import com.google.actions.api.ActionRequest;
import database.dao.Dao;
import database.dao.ProductDao;
import database.entities.ProductEntity;
import database.entities.StoreEntity;

class ParticularProduct extends AbstractIntentResponse {
    private static final String PARAMETER_NAME = "name";

    ParticularProduct(ActionRequest request) {
        super(request);
    }

    @Override
    String prepareTextMessage() {
        final String name = (String) request.getParameter(PARAMETER_NAME);
        final Dao<ProductEntity> dao = new ProductDao();
        final ProductEntity product = dao.findByName(ProductEntity.class, name);
        final StringBuilder builder = new StringBuilder();
        builder.append(product.getName())
                .append("\n");
        if (product.getPrices().size() != 0) {
            builder.append("Есть в наличии в магазинах:")
                    .append("\n");
            product.getPrices().forEach(priceEntity -> {
                final StoreEntity store = priceEntity.getStore();
                builder.append("-")
                        .append(store.getName())
                        .append(", ")
                        .append("цена: ")
                        .append(priceEntity.getPrice())
                        .append("\n");
            });
        }
        return builder.toString();
    }
}
