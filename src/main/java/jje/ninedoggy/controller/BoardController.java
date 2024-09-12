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
        List<PostDto> posts = boardService.findAll();
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

    @GetMapping("/posting/{bno}")
    public ModelAndView readPost(@PathVariable("bno") Long bno) {
        Post post = boardService.findById(bno);
        ModelAndView mav = new ModelAndView("read");
        mav.addObject("post", new PostDto(post));
        return mav;
    }

    @PutMapping("/posting/{bno}")
    public ResponseEntity<Void> updatePost(@PathVariable("bno") Long bno, @RequestBody PostDto postDto) {
        boardService.update(bno, postDto);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/posting/{bno}")
    public ResponseEntity<Void> deletePost(@PathVariable("bno") Long bno) {
        boardService.delete(bno);

        return ResponseEntity.ok()
                .build();
    }


}
