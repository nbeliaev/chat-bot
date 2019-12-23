package intents.response;

import com.google.actions.api.ActionRequest;
import configs.Config;
import database.dao.Dao;
import database.dao.ProductDao;
import database.entities.ProductEntity;
import database.entities.ProductsInStoresEntity;
import database.entities.StoreEntity;
import exceptions.NotExistDataBaseException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.PriceFormatter;
import utils.UTF8Control;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class ParticularProduct extends AbstractIntentResponse {
    private static final String PARAMETER_NAME = "name";
    private final ResourceBundle bundle;
    private final NumberFormat numberFormat;
    private final static Logger log = LogManager.getLogger(ParticularProduct.class);

    public ParticularProduct(ActionRequest request) {
        super(request);
        final Locale locale = getLocale();
        bundle = ResourceBundle.getBundle("lang/i18n", locale, new UTF8Control());
        numberFormat = PriceFormatter.getInstance(locale, Integer.parseInt(Config.getProperty(Config.PRICE_FORMAT)));
    }

    @Override
    String prepareTextMessage() {
        final String name = (String) request.getParameter(PARAMETER_NAME);
        final Dao<ProductEntity> dao = new ProductDao();
        final ProductEntity product;
        try {
            product = dao.findByName(ProductEntity.class, name);
        } catch (NotExistDataBaseException e) {
            generateNoFoundMessage(name);
            return builder.toString();
        }
        builder.append(product.getName())
                .append(NEW_ROW)
                .append(NEW_ROW);
        final Optional<ProductsInStoresEntity> productsInStoresMaxQty = product.getProductsInStores().stream().max(Comparator.comparingDouble(ProductsInStoresEntity::getQuantity));
        if (!productsInStoresMaxQty.isPresent() || productsInStoresMaxQty.get().getQuantity() == 0) {
            builder.append(bundle.getString("notAvailableProduct"));
            return builder.toString();
        }
        if (!product.getProductsInStores().isEmpty()) {
            builder.append(bundle.getString("availableProduct"))
                    .append(NEW_ROW)
                    .append(NEW_ROW);
            product.getProductsInStores().forEach(entity -> {
                final StoreEntity store = entity.getStore();
                if (!store.getAddress().isEmpty() && entity.getQuantity() != 0) {
                    generatePricesInStoresMessage(entity, store);
                }
            });
            builder.append(bundle.getString("furtherSearch"));
        }
        return builder.toString();
    }

    private void generatePricesInStoresMessage(ProductsInStoresEntity entity, StoreEntity store) {
        builder.append("- ")
                .append(store.getAddress())
                .append(", ")
                .append(bundle.getString("price"))
                .append(" ")
                .append(numberFormat.format(entity.getPrice()))
                .append(" ")
                .append(Config.getProperty(Config.CURRENCY))
                .append(NEW_ROW)
                .append(NEW_ROW);
    }

    private void generateNoFoundMessage(String name) {
        log.info(String.format("No results were founded for %s", name));
        builder.append(bundle.getString("refineSearchParameter"));
    }
}
