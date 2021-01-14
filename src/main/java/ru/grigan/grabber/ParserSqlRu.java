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
            Element elementForDate = element.parent().child(5);
            Date date = new RussianDateFormat().getDateFromRusFormat(
                    elementForDate.text(), RussianDateFormat.PATTERN);
            String title = elementForPostInstance.text();
            String link = elementForPostInstance.attr("href");
            Post post = getPost(link);
            post.setTitle(title);
            post.setDateCreation(date);
            posts.add(post);
        }

        return posts;
    }

    @Override
    public Post getPost(String url) throws IOException {
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
        Post post = new Post();
        post.setUrl(url);
        post.setUsername(username);
        post.setDescription(body);
        return post;
    }

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public static void main(String[] args) {
        ParserSqlRu parserSqlRu = new ParserSqlRu();
        for (String url : parserSqlRu.getUrls()) {
            try {
                parserSqlRu.getPostList(url).forEach(System.out::println);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
