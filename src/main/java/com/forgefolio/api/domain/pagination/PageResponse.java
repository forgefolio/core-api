package com.forgefolio.api.domain.pagination;


import java.util.List;
import java.util.function.Function;

public class PageResponse<T> {
    private final int page;
    private final int size;
    private final long totalElements;
    private final List<T> content;

    public <ORIGINAL> PageResponse(PageResponse<ORIGINAL> other, Function<ORIGINAL, T> contentMapper) {
        this.page = other.page;
        this.size = other.size;
        this.totalElements = other.totalElements;

        this.content = other.content.stream()
                .map(contentMapper)
                .toList();
    }

}
