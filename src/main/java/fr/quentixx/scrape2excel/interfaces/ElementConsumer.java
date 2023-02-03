package fr.quentixx.scrape2excel.interfaces;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@FunctionalInterface
public interface ElementConsumer<T> {

    T accept(Document doc, Element element) throws Exception;

}
