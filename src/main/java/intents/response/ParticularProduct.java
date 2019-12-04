package intents.response;

import com.google.actions.api.ActionRequest;
import configs.Config;
import database.dao.Dao;
import database.dao.ProductDao;
import database.entities.ProductEntity;
import database.entities.StoreEntity;
import utils.PriceFormatter;
import utils.UTF8Control;

import java.util.ResourceBundle;

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
        final ResourceBundle bundle = ResourceBundle.getBundle("lang/i18n", request.getLocale(), new UTF8Control());
        builder.append(product.getName())
                .append("\n");
        if (!product.getPrices().isEmpty()) {
            builder.append(bundle.getString("availableProduct"))
                    .append("\n");
            product.getPrices().forEach(priceEntity -> {
                final StoreEntity store = priceEntity.getStore();
                if (!store.getAddress().isEmpty()) {
                    builder.append("- ")
                            .append(store.getAddress())
                            .append(", ")
                            .append(bundle.getString("price"))
                            .append(" ")
                            .append(PriceFormatter.format(priceEntity.getPrice()))
                            .append(" ")
                            .append(Config.getProperty(Config.CURRENCY))
                            .append("\n");
                }
            });
        }
        return builder.toString();
    }
}
