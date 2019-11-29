package intents.response;

import com.google.actions.api.ActionRequest;
import database.dao.Dao;
import database.dao.StoreDao;
import database.entities.StoreEntity;

import java.util.List;

public class ShopList extends AbstractIntentResponse {

    public ShopList(ActionRequest request) {
        super(request);
    }

    @Override
    String prepareTextMessage() {
        Dao<StoreEntity> dao = new StoreDao();
        final List<StoreEntity> stores = dao.getAll(StoreEntity.class);
        final StringBuilder builder = new StringBuilder();
        if (stores.isEmpty()) {
            builder.append("Извините, но я не нашел ни одного доступного адреса или телефона.");
            return builder.toString();
        } else {
            builder.append("Адреса и контактные телефоны аптек нашей сети:")
                    .append("\n");
            stores.forEach(entity -> {
                final String address = entity.getAddress();
                if (!address.isEmpty()) {
                    builder.append(address)
                            .append("\n");
                    final String phoneNumber = entity.getPhoneNumber();
                    if (!phoneNumber.isEmpty()) {
                        builder.append("тел. ")
                                .append(phoneNumber)
                                .append("\n");
                    }
                }
            });
        }
        return builder.toString();
    }
}
