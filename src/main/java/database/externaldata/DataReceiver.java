package database.externaldata;

import configs.Config;
import exceptions.ConnectionException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import utils.AuthUtil;

import java.io.IOException;

public class DataReceiver {
    private int timeOut = 10_000;
    private boolean ignoreContentType = true;
    private Connection.Method method = Connection.Method.GET;

    public DataReceiver(int timeOut,
                        Connection.Method method,
                        boolean ignoreContentType) {
        this.timeOut = timeOut;
        this.method = method;
        this.ignoreContentType = ignoreContentType;
    }

    public DataReceiver() {
    }

    public String getResourceData(String resource) {
        final Connection.Response response;
        try {
            response = Jsoup.connect(Config.getProperty(Config.CONNECTION_1C))
                    .header("Authorization", AuthUtil.getBasicAuthorization())
                    .timeout(timeOut)
                    .ignoreContentType(ignoreContentType)
                    .method(method)
                    .execute();
        } catch (IOException e) {
            throw new ConnectionException("Couldn't connect to external data source");
        }
        // TODO check response status code
        return response.body();
        // TODO to process json in another class
        /*final StoreEntity[] stores = JsonParser.read(response.body(), StoreEntity[].class);
        final StoreDaoImpl dao = new StoreDaoImpl();
        Arrays.stream(stores).forEach(dao::save);*/
    }
}
