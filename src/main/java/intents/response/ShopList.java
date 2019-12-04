package intents.response;

import com.google.actions.api.ActionRequest;
import database.dao.Dao;
import database.dao.StoreDao;
import database.entities.StoreEntity;
import utils.UTF8Control;

import java.util.List;
import java.util.ResourceBundle;

public class ShopList extends AbstractIntentResponse {

    public ShopList(ActionRequest request) {
        super(request);
    }

    @Override
    String prepareTextMessage() {
        Dao<StoreEntity> dao = new StoreDao();
        final List<StoreEntity> stores = dao.getAll(StoreEntity.class);
        final StringBuilder builder = new StringBuilder();
        final ResourceBundle bundle = ResourceBundle.getBundle("lang/i18n", request.getLocale(), new UTF8Control());
        if (stores.isEmpty()) {
            builder.append(bundle.getString("notAvailableShop"));
            return builder.toString();
        } else {
            builder.append(bundle.getString("shopContacts"))
                    .append("\n");
            stores.forEach(entity -> {
                final String address = entity.getAddress();
                if (!address.isEmpty()) {
                    builder.append(address)
                            .append("\n");
                    final String phoneNumber = entity.getPhoneNumber();
                    if (!phoneNumber.isEmpty()) {
                        builder.append(bundle.getString("phoneNumber"))
                                .append(" ")
                                .append(phoneNumber)
                                .append("\n");
                    }
                }
            });
        }
        return builder.toString();
    }
}
