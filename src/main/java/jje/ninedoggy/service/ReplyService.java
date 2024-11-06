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

    public Map<Long, List<ReplyDTO>> readReply(Long bno) {
        List<Reply> readReplies = replyRepository
                .findAllByBno(bno, Sort.by(Sort.Order.asc("rno")));
        List<ReplyDTO> repliesToDTO = readReplies.stream().map(ReplyDTO :: new).toList();
        Map<Long, List<ReplyDTO>> replies = new HashMap<>();

        for(ReplyDTO replyDTO : repliesToDTO) {
            if(replyDTO.getPrno() ==0){
                List<ReplyDTO> replyList = new ArrayList<>();
                replyList.add(replyDTO);
                replies.put(replyDTO.getRno(), replyList);
            }else{
                List<ReplyDTO> replyList = replies.get(replyDTO.getPrno());
                replyList.add(replyDTO);
                replies.put(replyDTO.getPrno(), replyList);
            }
        }

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
