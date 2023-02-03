package fr.quentixx.scrape2excel;

import fr.quentixx.scrape2excel.interfaces.ElementConsumer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class DataScraper {

    private final Logger logger = Logger.getLogger("DataScraper");

    private final Map<String, String> cookies;

    private long delayBetweenRequests;

    private String[] urls;

    public DataScraper(long delayBetweenRequests) {
        this(delayBetweenRequests, new HashMap<>());
    }

    public DataScraper(long delayBetweenRequests, Map<String, String> cookies) {
        this.setDelayBetweenRequests(delayBetweenRequests);
        this.cookies = cookies;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setDelayBetweenRequests(long delay) {
        this.delayBetweenRequests = delay;
    }

    public long getDelayBetweenRequests() {
        return delayBetweenRequests;
    }

    /**
     * Define the URLs that will be scraped
     *
     * @param urls
     */
    public void setUrls(String... urls) {
        this.urls = urls;
    }

    /**
     * Scrape multiple URLs that defined by setter or constructor before, with the same pattern.
     *
     * @param pattern  The select pattern
     * @param consumer The consumer
     * @param <T>
     * @return Stream
     * @throws IOException
     */
    public <T> Stream<T> scrape(String pattern, ElementConsumer<T> consumer) {
        return Arrays.stream(urls).flatMap(url -> {
                    try {
                        return scrapUrl(url, pattern, consumer);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .peek(x -> {
                    if (delayBetweenRequests > 0) {
                        try {
                            Thread.sleep(delayBetweenRequests);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }

    /**
     * Scrape a specfic URL with a pattern.
     *
     * @param url      The target URL
     * @param pattern  The select pattern
     * @param consumer The consumer
     * @param <T>
     * @return Stream
     * @throws IOException
     */
    public <T> Stream<T> scrapUrl(String url, String pattern, ElementConsumer<T> consumer) throws IOException {
        logger.info("Scraping '" + url + "' using pattern '" + pattern + "'");

        Document doc = Jsoup.connect(url).cookies(cookies).get();

        Element targetElement = Optional.ofNullable(doc.select(pattern).first())
                .orElse(doc.getElementsByClass(pattern).first());

        if (targetElement == null) {
            logger.info("Find null element");
            return null;
        }

        return targetElement.children().stream().map(element -> {
            try {
                T entity = consumer.accept(doc, element);
                logger.info("Entity new instance: " + entity.toString());
                return entity;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).filter(Objects::nonNull);
    }
}
