package ru.grigan.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SqlRuParse {

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        parsePage(row);
        Elements href = doc.select(".sort_options");
        for (Document document : getPage(doc)) {
            parsePage(document.select(".postslisttopic"));
        }
    }

    // this method parsing page
    public static void parsePage(Elements source) {
        for (Element td : source) {
            Element href = td.child(0);
            Element other = td.parent().child(5);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            System.out.println(new RussianDateFormat().
                    getDateFromRusFormat(other.text(),
                            RussianDateFormat.PATTERN));
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
}
