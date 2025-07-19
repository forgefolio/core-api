package com.forgefolio.api.domain.pagination;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class PageCommand {

    private final int page;
    private final int size;

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    protected final List<String> sort;

    public abstract Map<String, Direction> getSort();

    public PageCommand(int page, int size, List<String> sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public enum Direction {
        ASC,
        DESC,
    }

    public String getSortQuery() {
        return getSort().entrySet().stream()
                .map(e -> " " + e.getKey() + " " + e.getValue().name())
                .collect(Collectors.joining(", "));
    }

}
