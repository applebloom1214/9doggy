package jje.ninedoggy.repository;

import jje.ninedoggy.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
