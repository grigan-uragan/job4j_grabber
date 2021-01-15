package ru.grigan.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParserSqlRu implements Parse {
    private List<Post> posts = new ArrayList<>();
    private String[] urls =
            {"https://www.sql.ru/forum/job-offers/1",
            "https://www.sql.ru/forum/job-offers/2",
            "https://www.sql.ru/forum/job-offers/3",
            "https://www.sql.ru/forum/job-offers/4",
            "https://www.sql.ru/forum/job-offers/5"};

    @Override
    public List<Post> getPostList(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.select(".postslisttopic");
        for (Element element : row) {
            Element elementForPostInstance = element.child(0);
            String link = elementForPostInstance.attr("href");
            posts.add(getPost(link));
        }
        return posts;
    }

    @Override
    public Post getPost(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select(".msgBody");
        String title = document.select(".messageHeader").first().text();
        if (title.contains("[new]")) {
           title = title.replace("[new]", "").strip();
        }
        String footer = document.select(".msgFooter")
                .first()
                .text();
        String stringDate = footer.substring(0, footer.indexOf("[")).strip();
        Date date = new RussianDateFormat()
                .getDateFromRusFormat(stringDate, RussianDateFormat.PATTERN);
        String body = elements.select(".msgBody").last().text();
        return new Post(url, title, body, date);
    }

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public static void main(String[] args) throws IOException {
        ParserSqlRu parserSqlRu = new ParserSqlRu();
        parserSqlRu.getPostList("https://www.sql.ru/forum/job-offers/1")
                .forEach(System.out::println);
    }
}
