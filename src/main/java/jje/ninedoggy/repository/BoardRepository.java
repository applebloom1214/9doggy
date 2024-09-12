package jje.ninedoggy.repository;

import jje.ninedoggy.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Post, Long> {

}
