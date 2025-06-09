package com.salesianostriana.dam.alvarolazarocastellon.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageRender<T> {

    private String url;
    private Page<T> page;
    private int totalPages;
    private int currentPage;
    private int itemsPerPage;
    private List<PageItem> pages;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.pages = new ArrayList<PageItem>();

        itemsPerPage = 7;
        totalPages = page.getTotalPages();
        currentPage = page.getNumber() + 1;

        int desde, hasta;

        if (totalPages <= itemsPerPage) {
            desde = 1;
            hasta = totalPages;
        } else {
            if (currentPage <= itemsPerPage / 2) {
                desde = 1;
                hasta = itemsPerPage;
            } else if (currentPage >= totalPages - itemsPerPage / 2) {
                desde = totalPages - itemsPerPage + 1;
                hasta = itemsPerPage;
            } else {
                desde = currentPage - itemsPerPage / 2;
                hasta = itemsPerPage;
            }
        }

        for (int i = 0; i < hasta; i++) {
            pages.add(new PageItem(desde + i, currentPage == desde + i));

        }
    }

    public boolean isFirst() {
        return page.isFirst();
    }

    public boolean isLast() {
        return page.isLast();
    }

    public boolean isHasNext() {
        return page.hasNext();
    }

    public boolean isHasPrevious() {
        return page.hasPrevious();
    }

}
