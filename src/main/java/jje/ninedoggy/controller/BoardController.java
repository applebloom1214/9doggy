package jje.ninedoggy.controller;

import jje.ninedoggy.domain.Post;
import jje.ninedoggy.dto.PagingDTO;
import jje.ninedoggy.dto.PostDto;
import jje.ninedoggy.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        Page<Post> paging = boardService.listPaging(0);
        List<PostDto> posts = paging
                .getContent()
                .stream().map(PostDto::new)
                .toList();
        PagingDTO pagingDTO = new PagingDTO(0, paging.getTotalPages());
        mav.addObject("pagingDTO", pagingDTO);
        mav.addObject("posts", posts);
        return mav;
    }

    @GetMapping("/board/{page}")
    public ModelAndView boardByPage(@PathVariable("page") int page) {
        ModelAndView mav = new ModelAndView("board");
        List<PostDto> posts = boardService.listPaging(page)
                .getContent()
                .stream().map(PostDto::new)
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

    @PutMapping("/posting/{bno}/{likeFlag}")
    public ResponseEntity<Long> updateLikes(@PathVariable("bno") Long bno, @PathVariable("likeFlag") int likeFlag) {
        Long likes = boardService.handleLikes(bno, likeFlag);
        return ResponseEntity.ok()
                .body(likes);
    }

    @DeleteMapping("/posting/{bno}")
    public ResponseEntity<Void> deletePost(@PathVariable("bno") Long bno) {
        boardService.delete(bno);

        return ResponseEntity.ok()
                .build();
    }


}
