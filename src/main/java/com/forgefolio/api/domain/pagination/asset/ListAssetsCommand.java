package com.forgefolio.api.domain.pagination.asset;

import com.forgefolio.api.domain.exception.BadArgumentException;
import com.forgefolio.api.domain.exception.ErrorCode;
import com.forgefolio.api.domain.pagination.PageCommand;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListAssetsCommand extends PageCommand {

    private static final List<String> ALLOWED_SORT_FIELDS = List.of("ticker", "name");

    private final String ticker;

    public ListAssetsCommand(int page, int size, List<String> sort, String ticker) {
        super(page, size, sort);
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }

    @Override
    public Map<String, Direction> getSort() {
        if (sort == null || sort.isEmpty())
            return Map.of("ticker", Direction.ASC);

        return sort.stream()
                .map(sort -> {
                    String[] split = sort.split(":");

                    if (split.length != 2 || !ALLOWED_SORT_FIELDS.contains(split[0]))
                        throw new BadArgumentException(ErrorCode.INVALID_SORT_FIELD);

                    Direction direction = Direction.valueOf(split[1].toUpperCase());

                    return Map.entry(split[0], direction);
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }
}
