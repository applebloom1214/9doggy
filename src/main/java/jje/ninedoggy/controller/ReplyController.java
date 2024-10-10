package jje.ninedoggy.controller;

import jje.ninedoggy.domain.Reply;
import jje.ninedoggy.dto.ReplyDTO;
import jje.ninedoggy.repository.ReplyRepository;
import jje.ninedoggy.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService replyService;
    private final ReplyRepository replyRepository;

    @PostMapping("/posting/reply")
    public ResponseEntity<Long> addReply(@RequestBody ReplyDTO replyDTO) {
        Long rno = replyService.saveReply(replyDTO);
        Reply reply = replyRepository.findById(rno).get();
        System.out.println(reply);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rno);
    }

}
