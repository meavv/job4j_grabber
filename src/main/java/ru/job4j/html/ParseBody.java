package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseBody {
    public static void main(String[] args) throws Exception {
        ParseBody parseBody = new ParseBody();
        parseBody.parsing("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t");
    }

    public void parsing(String url) throws Exception {
        Document doc = Jsoup.connect(url).get();
        var row = doc.select(".msgBody").get(0).parent().child(1).text();
        System.out.println(row);
    }
}
