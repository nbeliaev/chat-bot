package intents.response;

import com.google.actions.api.ActionRequest;
import configs.Config;
import database.dao.Dao;
import database.dao.ProductDao;
import database.entities.ProductsInStoresEntity;
import database.entities.ProductEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.PriceFormatter;
import utils.UTF8Control;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductList extends AbstractIntentResponse {
    private final static String NEW_ROW = "\n";
    private final static Logger log = LogManager.getLogger(ProductList.class);

    public ProductList(ActionRequest request) {
        super(request);
    }

    @Override
    String prepareTextMessage() {
        final String synonym = ((String) Objects.requireNonNull(request.getParameter("synonym"))).toLowerCase();
        final Dao<ProductEntity> dao = new ProductDao();
        final Set<ProductEntity> productList = Stream.concat(
                dao.findByPattern(ProductEntity.class, "synonym", synonym).stream(),
                dao.findByPattern(ProductEntity.class, "name", synonym).stream()
        ).collect(Collectors.toSet());
        final StringBuilder builder = new StringBuilder();
        final Locale locale = getLocale();
        final ResourceBundle bundle = ResourceBundle.getBundle("lang/i18n", locale, new UTF8Control());
        if (productList.isEmpty()) {
            log.info(String.format("No results were founded for %s", synonym));
            builder.append(bundle.getString("refineSearchParameter"));
            return builder.toString();
        }
        final NumberFormat formatter = PriceFormatter.getInstance(locale, Integer.parseInt(Config.getProperty(Config.PRICE_FORMAT)));
        // TODO: use bundle
        builder.append(bundle.getString("requestFound"))
                .append(NEW_ROW)
                .append(NEW_ROW);
        productList.forEach(product -> {
            builder.append(product.getName());
            final Optional<ProductsInStoresEntity> minPrice = product.getPrices().stream().min(Comparator.comparingDouble(ProductsInStoresEntity::getPrice));
            if (!minPrice.isPresent()) {
                builder.append(", ")
                        .append(bundle.getString("notAvailableProduct"))
                        .append(NEW_ROW);
            } else {
                final ProductsInStoresEntity productsInStoresEntity = minPrice.get();
                builder.append(", ")
                        .append(bundle.getString("startPrice"))
                        .append(" ")
                        .append(formatter.format(productsInStoresEntity.getPrice()))
                        .append(" ")
                        .append(Config.getProperty(Config.CURRENCY))
                        .append(NEW_ROW)
                        .append(NEW_ROW);
            }
        });
        builder.append(NEW_ROW)
                .append(bundle.getString("checkProductInStores"));
        return builder.toString();
    }
}
