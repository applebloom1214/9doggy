package jje.ninedoggy.controller;

import jje.ninedoggy.dto.ReplyDTO;
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

    @PostMapping("/posting/reply")
    public ResponseEntity<Long> addReply(@RequestBody ReplyDTO replyDTO) {
        Long rno = replyService.saveReply(replyDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rno);
    }

}
