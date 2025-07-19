package com.forgefolio.api.infrastructure.adapter.out.api.asset;

import com.forgefolio.api.infrastructure.adapter.out.api.asset.dto.AssetListDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/")
@RegisterRestClient(configKey = "asset-price-provider")
public interface AssetPriceRestClient {

    @GET
    Uni<AssetListDTO> getAssetList(
            @QueryParam("page") int page,
            @QueryParam("limit") int limit,
            @QueryParam("type") String type,
            @QueryParam("token") String token
    );
}
