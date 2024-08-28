package jje.ninedoggy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class BoardController {

    @GetMapping("/board")
    public ModelAndView board() {
        ModelAndView mav = new ModelAndView("board");
        return mav;
    }

}
