package jje.ninedoggy.repository;

import jje.ninedoggy.domain.Post;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {
    public static Specification<Post> searchByTitle(String title) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title + "%"));
    }

    public static Specification<Post> searchByContent(String content) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("content"), "%" +content + "%"));
    }

    public static Specification<Post> searchByWriter(String writer) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("writer"), "%" + writer + "%"));
    }

}
