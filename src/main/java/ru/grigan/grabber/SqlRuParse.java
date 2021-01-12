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
        for (Element td : row) {
            Element href = td.child(0);
            Element other = td.parent().child(5);
            System.out.println("1 " + href.attr("href"));
            System.out.println("2 " + href.text());
            System.out.println("3 " + new RussianDateFormat().
                    getDateFromRusFormat(other.text(),
                    RussianDateFormat.PATTERN));
        }
    }
}
