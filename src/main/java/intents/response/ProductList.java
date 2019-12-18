package intents.response;

import com.google.actions.api.ActionRequest;
import configs.Config;
import database.dao.Dao;
import database.dao.ProductDao;
import database.entities.ProductEntity;
import database.entities.ProductsInStoresEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.PriceFormatter;
import utils.UTF8Control;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductList extends AbstractIntentResponse {
    private final ResourceBundle bundle;
    private final NumberFormat numberFormat;
    private final static Logger log = LogManager.getLogger(ProductList.class);

    public ProductList(ActionRequest request) {
        super(request);
        final Locale locale = getLocale();
        bundle = ResourceBundle.getBundle("lang/i18n", locale, new UTF8Control());
        numberFormat = PriceFormatter.getInstance(locale, Integer.parseInt(Config.getProperty(Config.PRICE_FORMAT)));
    }

    @Override
    String prepareTextMessage() {
        final String synonym = ((String) Objects.requireNonNull(request.getParameter("synonym"))).toLowerCase();
        final Dao<ProductEntity> dao = new ProductDao();
        final Set<ProductEntity> productList = findUniqueProducts(synonym, dao);
        if (productList.isEmpty()) {
            generateNoFoundMessage(synonym);
            return builder.toString();
        }
        builder.append(bundle.getString("requestFound"))
                .append(NEW_ROW)
                .append(NEW_ROW);
        productList.forEach(product -> {
            builder.append(product.getName());
            final Optional<ProductsInStoresEntity> minPrice = product.getProductsInStores().stream().min(Comparator.comparingDouble(ProductsInStoresEntity::getPrice));
            if (!minPrice.isPresent()) {
                builder.append(", ")
                        .append(bundle.getString("notAvailableProduct"))
                        .append(NEW_ROW);
            } else {
                final ProductsInStoresEntity productsInStoresEntity = minPrice.get();
                generateMinPriceMessage(productsInStoresEntity.getPrice());
            }
        });
        builder.append(bundle.getString("checkProductInStores"));
        return builder.toString();
    }

    private Set<ProductEntity> findUniqueProducts(String synonym, Dao<ProductEntity> dao) {
        return Stream.concat(
                dao.findByPattern(ProductEntity.class, "synonym", synonym).stream(),
                dao.findByPattern(ProductEntity.class, "name", synonym).stream()
        ).collect(Collectors.toSet());
    }

    private void generateMinPriceMessage(double minPrice) {
        builder.append(", ")
                .append(bundle.getString("startPrice"))
                .append(" ")
                .append(numberFormat.format(minPrice))
                .append(" ")
                .append(Config.getProperty(Config.CURRENCY))
                .append(NEW_ROW)
                .append(NEW_ROW);
    }

    private void generateNoFoundMessage(String synonym) {
        log.info(String.format("No results were founded for %s", synonym));
        builder.append(bundle.getString("refineSearchParameter"));
    }
}
