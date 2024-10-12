package jje.ninedoggy.service;

import jje.ninedoggy.domain.Post;
import jje.ninedoggy.domain.Reply;
import jje.ninedoggy.dto.ReplyDTO;
import jje.ninedoggy.repository.BoardRepository;
import jje.ninedoggy.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Long saveReply(ReplyDTO replyDTO) {
        Reply reply = replyRepository.save(replyDTO.toEntity());
        Post post = boardRepository.findById(replyDTO.getBno()).get();
        post.addReply(reply);
        return reply.getRno();
    }

    public List<Reply> readReply(Long bno) {
       return  replyRepository.findAllByBnoOrderByRnoDesc(bno);
    }

}
