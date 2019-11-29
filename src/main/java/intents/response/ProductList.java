package intents.response;

import com.google.actions.api.ActionRequest;
import configs.Config;
import database.dao.Dao;
import database.dao.ProductDao;
import database.entities.PriceEntity;
import database.entities.ProductEntity;
import utils.PriceFormatter;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class ProductList extends AbstractIntentResponse {

    public ProductList(ActionRequest request) {
        super(request);
    }

    @Override
    String prepareTextMessage() {
        final String synonym = (String) request.getParameter("synonym");
        final Dao<ProductEntity> dao = new ProductDao();
        final List<ProductEntity> productList = new Stack<>();
        productList.addAll(dao.findByPattern(ProductEntity.class, "synonym", synonym));
        productList.addAll(dao.findByPattern(ProductEntity.class, "name", synonym));
        final StringBuilder builder = new StringBuilder();
        if (productList.isEmpty()) {
            builder.append("Извините, но сейчас ничего нет в наличии. Попробуйте уточнить параметр поиска");
            return builder.toString();
        }
        productList.forEach(product -> {
            builder.append(product.getName());
            final Optional<PriceEntity> minPrice = product.getPrices().stream().min(Comparator.comparingDouble(PriceEntity::getPrice));
            if (!minPrice.isPresent()) {
                builder.append(", нет в наличии");
            } else {
                final PriceEntity priceEntity = minPrice.get();
                builder.append(", цена от ")
                        .append(PriceFormatter.format(priceEntity.getPrice()))
                        .append(" ")
                        .append(Config.getProperty(Config.CURRENCY))
                        .append("\n");
            }
        });
        return builder.toString();
    }
}
