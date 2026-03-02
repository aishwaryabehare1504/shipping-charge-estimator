package com.shipping.dto;

import lombok.Data;

@Data
public class CalculateShippingRequest {

    private Long sellerId;

    private Long customerId;

    private DeliverySpeed deliverySpeed;
}
