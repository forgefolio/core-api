package com.forgefolio.api.infrastructure.adapter.out.api.asset;

import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "infrastructure.adapter.out.api.asset.price-provider")
@StaticInitSafe
public class AssetPriceProviderProperties {

    private String token;

    public AssetPriceProviderProperties() {
    }

    public String getToken() {
        return token;
    }
}
