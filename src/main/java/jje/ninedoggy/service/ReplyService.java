package jje.ninedoggy.service;

import jje.ninedoggy.domain.Reply;
import jje.ninedoggy.dto.ReplyDTO;
import jje.ninedoggy.repository.BoardRepository;
import jje.ninedoggy.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    public Long saveReply(ReplyDTO replyDTO) {
        return replyRepository.save(replyDTO.toEntity()).getRno();
    }

}
