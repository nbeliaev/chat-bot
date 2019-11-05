package dialogflow;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.SimpleResponse;
import database.dao.Dao;
import database.dao.StoreDaoImpl;
import database.entities.StoreEntity;

import java.util.List;

@SuppressWarnings("unused")
public class IntentsHandler extends DialogflowApp {

    @ForIntent("Shops-all")
    public ActionResponse welcome(ActionRequest request) {
        final ResponseBuilder responseBuilder = getResponseBuilder(request);
        Dao<StoreEntity> dao = new StoreDaoImpl();
        final List<StoreEntity> stores = dao.getAll(StoreEntity.class);
        final StringBuilder stringBuilder = new StringBuilder();
        stores.forEach(storeEntity -> {
            stringBuilder.append(storeEntity.getName())
                    .append("\n")
                    .append("- Часы работы: Пн-Пт, 8-18")
                    .append("\n")
                    .append("- Т: +79115554875")
                    .append("\n")
                    .append("\n");
        });
        final SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setDisplayText(stringBuilder.toString());
        responseBuilder.add(simpleResponse);
        return responseBuilder.build();
    }
}
