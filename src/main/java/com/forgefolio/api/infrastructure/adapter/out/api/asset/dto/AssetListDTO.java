package com.forgefolio.api.infrastructure.adapter.out.api.asset.dto;

import java.util.List;

public record AssetListDTO(
        List<IndexDTO> indexes,
        List<StockDTO> stocks,
        List<String> availableSectors,
        List<String> availableStockTypes,
        int currentPage,
        int totalPages,
        int itemsPerPage,
        int totalCount,
        boolean hasNextPage
) {
}
