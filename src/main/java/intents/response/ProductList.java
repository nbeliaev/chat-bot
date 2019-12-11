package intents.response;

import com.google.actions.api.ActionRequest;
import configs.Config;
import database.dao.Dao;
import database.dao.ProductDao;
import database.entities.PriceEntity;
import database.entities.ProductEntity;
import utils.PriceFormatter;
import utils.UTF8Control;

import java.text.NumberFormat;
import java.util.*;

public class ProductList extends AbstractIntentResponse {

    public ProductList(ActionRequest request) {
        super(request);
    }

    @Override
    String prepareTextMessage() {
        final String synonym = ((String) Objects.requireNonNull(request.getParameter("synonym"))).toLowerCase();
        final Dao<ProductEntity> dao = new ProductDao();
        // TODO: use Set instead of Stack
        final List<ProductEntity> productList = new Stack<>();
        productList.addAll(dao.findByPattern(ProductEntity.class, "synonym", synonym));
        productList.addAll(dao.findByPattern(ProductEntity.class, "name", synonym));
        final StringBuilder builder = new StringBuilder();
        final Locale locale = getLocale();
        final ResourceBundle bundle = ResourceBundle.getBundle("lang/i18n", locale, new UTF8Control());
        if (productList.isEmpty()) {
            builder.append(bundle.getString("refineSearchParameter"));
            return builder.toString();
        }
        final NumberFormat formatter = PriceFormatter.getInstance(locale, Integer.parseInt(Config.getProperty(Config.PRICE_FORMAT)));
        builder.append("По вашему запросу найдено:")
                .append("\n")
                .append("\n");
        productList.forEach(product -> {
            builder.append(product.getName());
            final Optional<PriceEntity> minPrice = product.getPrices().stream().min(Comparator.comparingDouble(PriceEntity::getPrice));
            if (!minPrice.isPresent()) {
                builder.append(", ")
                        .append(bundle.getString("notAvailableProduct"))
                        .append("\n");
            } else {
                final PriceEntity priceEntity = minPrice.get();
                builder.append(", ")
                        .append(bundle.getString("startPrice"))
                        .append(" ")
                        .append(formatter.format(priceEntity.getPrice()))
                        .append(" ")
                        .append(Config.getProperty(Config.CURRENCY))
                        .append("\n")
                        .append("\n");
            }
        });
        builder.append("\n")
                .append(bundle.getString("checkProductInStores"));
        return builder.toString();
    }
}
