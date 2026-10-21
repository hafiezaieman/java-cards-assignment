package com.hafiez.cards_assignment.dto;

import java.util.List;

public class PagedResponse<T> {

    private List<T> content;
    private int currentPage;
    private int pageSize;
    private long totalItems;
    private int totalPages;
    private boolean last;

    public PagedResponse(List<T> content, int currentPage, int pageSize,
                         long totalItems, int totalPages, boolean last) {
        this.content = content;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.last = last;
    }

    public List<T> getContent() { return content; }
    public int getCurrentPage() { return currentPage; }
    public int getPageSize() { return pageSize; }
    public long getTotalItems() { return totalItems; }
    public int getTotalPages() { return totalPages; }
    public boolean isLast() { return last; }
}
