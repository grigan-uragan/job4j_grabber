package ru.grigan.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SqlRuParse {
private static int count = 0;

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        parsePage(row);
        Elements href = doc.select(".sort_options");
        for (Document document : getPage(doc, 5)) {
            parsePage(document.select(".postslisttopic"));
        }
    }

    // this method parsing page
    public static void parsePage(Elements source) {
        for (Element td : source) {
            Element href = td.child(0);
            Element other = td.parent().child(5);
            System.out.println(++count + ") " + href.attr("href"));
            System.out.println(href.text());
            System.out.println(new RussianDateFormat().
                    getDateFromRusFormat(other.text(),
                            RussianDateFormat.PATTERN));
        }
    }

    //this method parse link for new page
    public static Document[] getPage(Document document, int pages) throws IOException {
        Document[] doc = new Document[pages];
        Elements links = document.select(".sort_options").select("a");
        for (int i = 0; i < doc.length; i++) {
            doc[i] = Jsoup.connect(links.get(i).attr("href")).get();
        }
        return doc;
    }
}
