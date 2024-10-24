package jje.ninedoggy.repository;

import jje.ninedoggy.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByBnoOrderByRnoDesc(Long bno);
}
