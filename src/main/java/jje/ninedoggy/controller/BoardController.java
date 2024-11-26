package jje.ninedoggy.controller;

import jje.ninedoggy.domain.Post;
import jje.ninedoggy.domain.Reply;
import jje.ninedoggy.dto.PagingDTO;
import jje.ninedoggy.dto.PostDto;
import jje.ninedoggy.dto.ReplyDTO;
import jje.ninedoggy.service.BoardService;
import jje.ninedoggy.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;
    private final ReplyService replyService;

    @GetMapping("/board")
    public ModelAndView board(String searchCondition) {
        ModelAndView mav = new ModelAndView("board");
        Page<Post> paging = boardService.listPaging(0, "", ""
                , searchCondition ==null ?"": searchCondition);
        List<PostDto> posts = paging
                .getContent()
                .stream().map(PostDto::new)
                .toList();
        PagingDTO pagingDTO = new PagingDTO(0, paging.getTotalPages());
        System.out.println(pagingDTO);
        mav.addObject("pagingDTO", pagingDTO);
        mav.addObject("posts", posts);
        return mav;
    }

    @GetMapping("/board/{page}")
    public ModelAndView boardByPage(@PathVariable("page") int page, String condition, String keyword) {
        ModelAndView mav = new ModelAndView("board");
        Page<Post> paging = boardService.listPaging(page-1,condition==null ? "": condition
                ,keyword==null ? "" : keyword,null);
        List<PostDto> posts = paging
                .getContent()
                .stream().map(PostDto::new)
                .toList();
        PagingDTO pagingDTO = new PagingDTO(page-1, paging.getTotalPages());
        System.out.println(pagingDTO);
        mav.addObject("pagingDTO", pagingDTO);
        mav.addObject("posts", posts);
        mav.addObject("condition", condition);
        mav.addObject("keyword", keyword);
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
    public ModelAndView readPost(@PathVariable("bno") Long bno, int page) {
        Post post = boardService.findById(bno);
        Map<Long, List<ReplyDTO>> replies = new LinkedHashMap<>();
        List<Reply> readReplies = replyService.readReply(bno, 0);
        List<ReplyDTO> repliesToDTO = readReplies.stream().map(ReplyDTO :: new).toList();

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

        ModelAndView mav = new ModelAndView("read");
        mav.addObject("post", new PostDto(post));
        mav.addObject("replies", replies);
        mav.addObject("page", page);
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
