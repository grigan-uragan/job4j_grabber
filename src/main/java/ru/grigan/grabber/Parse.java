package ru.grigan.grabber;

import java.io.IOException;
import java.util.List;

public interface Parse {

    List<Post> getPostList(String url) throws IOException;

    Post getPost(String url) throws IOException;
}
