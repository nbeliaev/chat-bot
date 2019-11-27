package intents.response;

import com.google.actions.api.ActionRequest;
import configs.Config;
import database.dao.Dao;
import database.dao.ProductDao;
import database.entities.ProductEntity;
import database.entities.StoreEntity;
import utils.PriceFormatter;

public class ParticularProduct extends AbstractIntentResponse {
    private static final String PARAMETER_NAME = "name";

    public ParticularProduct(ActionRequest request) {
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
                        .append(store.getAddress())
                        .append(", ")
                        .append("цена ")
                        .append(PriceFormatter.format(priceEntity.getPrice()))
                        .append(" ")
                        .append(Config.getProperty(Config.CURRENCY))
                        .append("\n");
            });
        }
        return builder.toString();
    }
}
