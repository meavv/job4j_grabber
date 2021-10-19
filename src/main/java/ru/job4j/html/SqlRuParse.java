package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.Post;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    public static void main(String[] args) throws Exception {
        DateTimeParser dateTimeParser = new SqlRuDateTimeParser();
        SqlRuParse sqlRuParse = new SqlRuParse(dateTimeParser);
        String url = "https://www.sql.ru/forum/job-offers/";
        sqlRuParse.list(url);
    }

    @Override
    public List<Post> list(String link) throws Exception {
        List<Post> listPost = new ArrayList<>();
        DateTimeParser dateTimeParser = new SqlRuDateTimeParser();
        SqlRuParse sqlRuParse = new SqlRuParse(dateTimeParser);
        for (int i = 1; i < 6; i++) {
            String url = link + i;
            Document doc = Jsoup.connect(url).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                listPost.add(sqlRuParse.detail(td.child(0).attr("href")));
            }
            url = link;
        }
        return listPost;
    }

    @Override
    public Post detail(String link) throws Exception {
        Document doc = Jsoup.connect(link).get();
        Post post = new Post();
        String time = doc.select(".msgFooter").get(0).text();
        post.setDescription(ParseBody.parsing(link));
        post.setLink(link);
        post.setTitle(doc.title());
        post.setCreated(dateTimeParser.parse(time.substring(0, time.indexOf("[") - 1)));
        return post;
    }
}