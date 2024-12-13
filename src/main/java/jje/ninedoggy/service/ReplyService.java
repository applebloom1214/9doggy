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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Reply saveReply(ReplyDTO replyDTO) {
        Reply reply;
        if(replyDTO.getPrno() == null){
            reply = replyRepository.save(replyDTO.toEntity());
        }else{
            reply = replyRepository.save(replyDTO.toEntity());
            reply.setPrno(replyDTO.getPrno());
        }
        Post post = boardRepository.findById(replyDTO.getBno()).get();
        post.addReply(reply);
        return reply;
    }

    public List<Reply> readReply(Long bno) {
//        PageRequest pageRequest = PageRequest.of(page, 100);
//        List<Reply> replies = replyRepository
//             .findAllByBnoOrderByRnoAsc(bno, pageRequest).getContent();
     List<Reply> replies = replyRepository
                .findAllByBno(bno, Sort.by(Sort.Order.asc("rno")));
     return replies;
    }


    @Transactional
    public Reply modifyReply(ReplyDTO replyDTO) {
        Reply reply = replyRepository.findById(replyDTO.getRno()).get();
        reply.modifyReply(replyDTO.getContent());
        return reply;
    }

    @Transactional
    public void deleteReply(ReplyDTO replyDTO) {
        Reply reply = replyRepository.findById(replyDTO.getRno()).get();
        reply.deleteReply();
        Post post = boardRepository.findById(replyDTO.getBno()).get();
        post.minusRcnt();
    }

}
