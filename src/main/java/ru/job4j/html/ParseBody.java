package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseBody {

    public static String parsing(String url) throws Exception {
        Document doc = Jsoup.connect(url).get();
        return doc.select(".msgBody").get(0).parent().child(1).text();
    }
}
