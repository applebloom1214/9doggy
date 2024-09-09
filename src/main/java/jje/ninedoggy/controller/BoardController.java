package jje.ninedoggy.controller;

import jje.ninedoggy.domain.Post;
import jje.ninedoggy.dto.PostDto;
import jje.ninedoggy.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/board")
    public ModelAndView board() {
        ModelAndView mav = new ModelAndView("board");

        List<PostDto> posts = boardService.findAll().stream()
                .map(PostDto::new)
                .toList();
        mav.addObject("posts", posts);
        return mav;
    }

    @GetMapping("/posting")
    public ModelAndView write() {
        ModelAndView mav = new ModelAndView("write");

        return mav;
    }

    @PostMapping("/posting")
    public ResponseEntity<Long> addPost(@RequestBody PostDto postDto) {
        Long bno = boardService.save(postDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(bno);
    }

    @GetMapping("/posting/${bno}")
    public ResponseEntity<PostDto> readPost(@PathVariable("bno") Long bno) {
        Post post = boardService.findById(bno);
        return ResponseEntity.ok(new PostDto(post));
    }


    @GetMapping("/read")
    public ModelAndView read() {
        ModelAndView mav = new ModelAndView("read");

        return mav;
    }

}
