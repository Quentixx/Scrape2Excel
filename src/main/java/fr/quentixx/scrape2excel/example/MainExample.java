package fr.quentixx.scrape2excel.example;

import com.github.crab2died.ExcelUtils;
import com.github.crab2died.exceptions.Excel4JException;
import fr.quentixx.scrape2excel.DataScraper;
import fr.quentixx.scrape2excel.example.entity.OrderEntity;

import java.io.IOException;
import java.util.List;

public class MainExample {
    private static final String[] LINKS = {
            "web_page_orders_link_1",
            "web_page_orders_link_2",
    };

    public static void main(String[] args) throws IOException {
        // Here, the system will sleep one second between each request
        DataScraper dataScraper = new DataScraper(1000L);

        // Define the URLs to be scraped
        dataScraper.setUrls(LINKS);

        // Define cookies if you need to be connected to the website
        // I advise you to use 'Cookie-Editor' Chrome extension to get cookies
        dataScraper.getCookies().put("AUTH_COOKIE_NAME", "YOUR_AUTH_TOKEN");

        String pattern = "tbody tbody";

        List<OrderEntity> orderList = dataScraper.scrape(pattern, (doc, element) -> {

            // Passing elements that not tagged by 'tr'
            if (!element.is("tr")) return null;

            OrderEntity order = new OrderEntity();

            String elementText = element.text();
            String[] data = elementText.split(" ");
            // -> output: [orderID] [storeName] [boughtDate] [deliveryDATE] [fullName]

            // Collect needed data from element's children
            long orderId = Long.parseLong(element.child(1).text());
            String storeName = element.child(2).text();
            String boughtDate = element.child(4).text();
            String deliveryDate = element.child(5).text();
            String fullName = element.child(6).text();

            String[] fullNameSplit = fullName.split(" ");
            String firstName = fullNameSplit[fullNameSplit.length - 1];
            String lastName = fullName.replaceAll(firstName, "").trim();

            // Set collected data to your entity object
            order.setOrderID(orderId);
            order.setStoreName(storeName);
            order.setBoughtDate(boughtDate);
            order.setDeliveryDate(deliveryDate);
            order.setLastName(lastName);
            order.setFirstName(firstName);

            // You can exploit the Document as you want and also use
            // Jsoup.connect(url).cookies(dataScraper.getCookies()).get()
            // To get data related to the order on another page (href redirection)

            return order;
        }).toList();

        // Export data to excel using Excel4j
        final String TARGET_PATH = "path/newFileName.xlsx";
        try {
            ExcelUtils.getInstance().exportObjects2Excel(orderList, OrderEntity.class, true, "YOUR SHEET NAME", true, TARGET_PATH);
        } catch (Excel4JException exception) {
            exception.printStackTrace();
        }
    }
}
