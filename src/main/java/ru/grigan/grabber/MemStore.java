package ru.grigan.grabber;

import java.util.ArrayList;
import java.util.List;

public class MemStore implements Store {
    private List<Post> posts = new ArrayList<>();
    private int ids = 1;

    @Override
    public void save(Post post) {
        if (posts.contains(post)) {
            return;
        }
        post.setId(ids++);
        posts.add(post);
    }

    @Override
    public List<Post> getAll() {
        return posts;
    }

    @Override
    public Post findById(int id) {
        for (Post post : posts) {
            if (post.getId() == id) {
                return post;
            }
        }
        throw new IllegalArgumentException("Post not found, check id");
    }
}
