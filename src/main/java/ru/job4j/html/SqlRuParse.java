package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        String url = "https://www.sql.ru/forum/job-offers/";
        for (int i = 1; i < 6; i++) {
            url = url + i;
            System.out.println(url);
            Document doc = Jsoup.connect(url).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element element = td.child(0);
                System.out.println(element.attr("href"));
                System.out.println(element.text());
                System.out.println(td.parent().child(5).text());
            }
            url = "https://www.sql.ru/forum/job-offers/";
        }
    }
}