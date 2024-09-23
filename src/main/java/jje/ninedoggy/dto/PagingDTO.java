package jje.ninedoggy.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PagingDTO {
    private int currentPage; // 현재 페이지
    private int totalPage; // 총 페이지
    private int startPage;
    private int endPage;
    boolean showNext;
    boolean showPrev;
    final int size = 10;

    public PagingDTO(int currentPage, int totalPage){
        this.currentPage = currentPage+1;
        this.totalPage = totalPage;
        this.startPage = (currentPage - 1) / size * size +1;
        this.endPage = Math.min(startPage + size-1, totalPage);
        showPrev = startPage != 1;
        showNext = endPage != totalPage;
    }

}
