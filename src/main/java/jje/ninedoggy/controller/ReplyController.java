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
    public ResponseEntity<ReplyDTO> addReply(@RequestBody ReplyDTO replyDTO) {
        Reply reply = replyService.saveReply(replyDTO);
        replyDTO = new ReplyDTO(reply);
        System.out.println(replyDTO);
        return ResponseEntity.ok()
                .body(replyDTO);
    }

}
