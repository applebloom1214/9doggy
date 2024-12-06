package jje.ninedoggy.controller;

import jje.ninedoggy.domain.Post;
import jje.ninedoggy.domain.Reply;
import jje.ninedoggy.dto.PostDto;
import jje.ninedoggy.dto.ReplyDTO;
import jje.ninedoggy.repository.ReplyRepository;
import jje.ninedoggy.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping("/posting/reply")
    public ResponseEntity<Map<Long, List<ReplyDTO>>> readPost(Long bno, int page) {
        Map<Long, List<ReplyDTO>> replies = new LinkedHashMap<>();
        List<Reply> readReplies = replyService.readReply(bno, page);
        List<ReplyDTO> repliesToDTO = readReplies.stream().map(ReplyDTO :: new).toList();
        // 대댓글 고민
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

        return ResponseEntity.ok()
                .body(replies);
    }

    @PostMapping("/posting/reply")
    public ResponseEntity<ReplyDTO> addReply(@RequestBody ReplyDTO replyDTO) {
        Reply reply = replyService.saveReply(replyDTO);
        replyDTO = new ReplyDTO(reply);
        System.out.println(replyDTO);
        return ResponseEntity.ok()
                .body(replyDTO);
    }

    @PutMapping("/posting/reply")
    public ResponseEntity<Void> modifyReply
            (@RequestBody ReplyDTO replyDTO) {
        replyService.modifyReply(replyDTO);
        return ResponseEntity.ok()
                .build();
    }

    @PatchMapping("/posting/reply")
    public ResponseEntity<Void> deleteReply
            (@RequestBody ReplyDTO replyDTO) {
        replyService.deleteReply(replyDTO);
        return ResponseEntity.ok()
                .build();
    }
}
