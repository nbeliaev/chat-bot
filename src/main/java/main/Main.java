package main;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {


    public static void main(String[] args) throws Exception {

        try (final SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory()) {
            System.out.println("done!");
        }


        /*final String user = "bot";
        final String password = "1122";
        final String login = user +
                ":" +
                password;
        final String base64Login = new String(Base64.encodeBase64(login.getBytes()));

        final Connection.Response execute = Jsoup.connect("http://localhost/retail/hs/bot/stores")
                .header("Authorization", "Basic " + base64Login)
                .timeout(5_000)
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .execute();
        final Gson gson = new Gson();
        final Store[] stores = gson.fromJson(execute.body(), Store[].class);*/
    }


    // TODO url to properties
    //final Path path = WebUtil.saveWebPage("https://tdab.ru/");
        /*try {
            List<String> addresses = new ArrayList<>();
            Document doc = Jsoup.connect("https://tdab.ru/").get();
            doc.getElementsByClass("adress").forEach((item) -> {
                final String s = item.ownText();
                if (s.contains("Адрес:")) {
                    addresses.add(s.replace("Адрес:", ""));
                }
            });
            addresses.forEach(System.out::println);
            // TODO create class Address with geocoord. Use Google
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    //System.out.println(path);

       /* try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

}
