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
        stores.forEach(entity -> {
            final String address = entity.getAddress();
            if (address != null) {
                builder.append(address)
                        .append("\n");
                final String phoneNumber = entity.getPhoneNumber();
                if (phoneNumber != null) {
                    builder.append(phoneNumber)
                            .append("\n");
                }
            }
        });
        return builder.toString();
    }
}
