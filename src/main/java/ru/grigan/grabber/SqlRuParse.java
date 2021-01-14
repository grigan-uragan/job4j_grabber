package ru.grigan.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlRuParse {
    private static List<Post> posts = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        parsePage(row);
        Elements href = doc.select(".sort_options");
        for (Document document : getPage(doc)) {
            parsePage(document.select(".postslisttopic"));
        }
        posts.forEach(System.out::println);
    }

    // this method parsing page
    public static void parsePage(Elements source) throws IOException {
        for (Element td : source) {
            Element href = td.child(0);
            Element other = td.parent().child(5);
            Date date = new RussianDateFormat().
                    getDateFromRusFormat(other.text(),
                            RussianDateFormat.PATTERN);
            String title = href.text();
            posts.add(getPost(href.attr("href"), date, title));
        }
    }

    //this method parse link for new page
    public static Document[] getPage(Document document) throws IOException {
        Document  first = Jsoup.connect("https://www.sql.ru/forum/job-offers/2").get();
        Document  second = Jsoup.connect("https://www.sql.ru/forum/job-offers/3").get();
        Document  third = Jsoup.connect("https://www.sql.ru/forum/job-offers/4").get();
        Document  fourth = Jsoup.connect("https://www.sql.ru/forum/job-offers/5").get();
        Document  fifth = Jsoup.connect("https://www.sql.ru/forum/job-offers/6").get();
        return new Document[]{first, second, third, fourth, fifth};
    }

    //this method create Post instance
    public static Post getPost(String url, Date date, String title) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select(".msgBody");
        String body = elements.select(".msgBody").last().text();
        String description = elements.select(".msgBody").first().text();
        String username;
        if (description.contains("Member")) {
            username = description.substring(0, description.indexOf("Member") - 1);
        } else if (description.contains("Администратор")) {
            username = description.substring(0, description.indexOf("Администратор") - 1);

        } else {
            username = "_______________";
        }
        return new Post(url,title, username, body, date);
    }

}
