package intents;

import com.google.actions.api.ActionRequest;
import database.dao.Dao;
import database.dao.ProductDao;
import database.entities.PriceEntity;
import database.entities.ProductEntity;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

class ProductList extends AbstractIntentResponse {
    private static final String PARAMETER_NAME = "synonym";

    ProductList(ActionRequest request) {
        super(request);
    }

    @Override
    String prepareTextMessage() {
        final String synonym = (String) request.getParameter(PARAMETER_NAME);
        final Dao<ProductEntity> dao = new ProductDao();
        final List<ProductEntity> productList = dao.findByPattern(ProductEntity.class, PARAMETER_NAME, synonym);
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("Вы искали %s", synonym))
                .append("\n");
        if (productList.size() == 0) {
            builder.append("Извините, но сейчас ничего нет в наличии. ")
                    .append("Попробуйте уточнить параметр поиска");
            return builder.toString();
        }
        builder.append("Сейчас есть в наличии:")
                .append("\n");
        productList.forEach(product -> {
            builder.append(product.getName());
            final Optional<PriceEntity> minPrice = product.getPrices().stream().min(Comparator.comparingDouble(PriceEntity::getPrice));
            if (minPrice.isPresent()) {
                final PriceEntity priceEntity = minPrice.get();
                builder.append(" цена от ")
                        .append(priceEntity.getPrice())
                        .append("\n");
            }
        });
        return builder.toString();
    }
}
