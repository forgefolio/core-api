package com.forgefolio.api.infrastructure.adapter.out.api.asset.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.BigInteger;

public record StockDTO(
        String stock,
        String name,
        BigDecimal close,
        BigDecimal change,
        BigInteger volume,

        @JsonProperty("market_cap")
        BigInteger marketCap,

        String logo,
        String sector,
        String type
) {
}
