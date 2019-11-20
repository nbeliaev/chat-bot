package intents;

import com.google.actions.api.ActionRequest;
import database.dao.Dao;
import database.dao.StoreDao;
import database.entities.StoreEntity;

import java.util.List;

class ShopsSchedule extends AbstractIntentResponse {

    ShopsSchedule(ActionRequest request) {
        super(request);
    }

    @Override
    String prepareTextMessage() {
        Dao<StoreEntity> dao = new StoreDao();
        final List<StoreEntity> stores = dao.getAll(StoreEntity.class);
        final StringBuilder stringBuilder = new StringBuilder();
        stores.forEach(entity -> {
            stringBuilder.append(entity.getName())
                    .append("\n")
                    .append("- Часы работы: Пн-Пт, 8-18")
                    .append("\n")
                    .append("- Т: +79115554875")
                    .append("\n")
                    .append("\n");
        });
        return stringBuilder.toString();
    }
}
