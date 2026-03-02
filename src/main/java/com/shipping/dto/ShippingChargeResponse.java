package com.shipping.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

@JsonPropertyOrder({
        "shippingCharge",
        "nearestWarehouse"
})
public class ShippingChargeResponse {

    private double shippingCharge;

    private NearestWarehouse nearestWarehouse;


    @Data
    @Builder
    @JsonPropertyOrder({
            "warehouseId",
            "warehouseLocation"
    })
    public static class NearestWarehouse {

        private Long warehouseId;

        private Location warehouseLocation;
    }


    @Data
    @Builder
    @JsonPropertyOrder({
            "lat",
            "long"
    })
    public static class Location {

        private double lat;

        @JsonProperty("long")
        private double longitude;
    }

}
