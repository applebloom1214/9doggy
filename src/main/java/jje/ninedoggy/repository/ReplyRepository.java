package jje.ninedoggy.repository;

import jje.ninedoggy.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByBnoOrderByRnoDesc(Long bno);
    Page<Reply> findAllByBnoOrderByRnoDesc(Long bno, Pageable pageable);
    List<Reply> findAllByBno(Long bno, Sort sort);
}
