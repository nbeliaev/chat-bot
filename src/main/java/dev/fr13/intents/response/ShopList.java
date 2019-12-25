package dev.fr13.intents.response;

import com.google.actions.api.ActionRequest;
import dev.fr13.database.dao.Dao;
import dev.fr13.database.dao.StoreDao;
import dev.fr13.database.entities.StoreEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import dev.fr13.utils.UTF8Control;

import java.util.List;
import java.util.ResourceBundle;

public class ShopList extends AbstractIntentResponse {
    private final static Logger log = LogManager.getLogger(ShopList.class);

    public ShopList(ActionRequest request) {
        super(request);
    }

    @Override
    String prepareTextMessage() {
        Dao<StoreEntity> dao = new StoreDao();
        final List<StoreEntity> stores = dao.getAll(StoreEntity.class);
        final ResourceBundle bundle = ResourceBundle.getBundle("lang/i18n", getLocale(), new UTF8Control());
        if (stores.isEmpty()) {
            log.warn("No available shops.");
            builder.append(bundle.getString("notAvailableShop"));
            return builder.toString();
        } else {
            builder.append(bundle.getString("shopContacts"))
                    .append(NEW_ROW);
            stores.forEach(entity -> {
                final String address = entity.getAddress();
                if (!address.isEmpty()) {
                    builder.append(address)
                            .append(NEW_ROW);
                    final String phoneNumber = entity.getPhoneNumber();
                    if (!phoneNumber.isEmpty()) {
                        builder.append(bundle.getString("phoneNumber"))
                                .append(" ")
                                .append(phoneNumber)
                                .append(NEW_ROW);
                    }
                }
            });
        }
        return builder.toString();
    }
}
