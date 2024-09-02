package jje.ninedoggy.controller;

import jje.ninedoggy.dto.PostDto;
import jje.ninedoggy.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
