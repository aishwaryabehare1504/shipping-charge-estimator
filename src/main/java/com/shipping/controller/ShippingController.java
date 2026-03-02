package com.shipping.controller;

import com.shipping.dto.CalculateShippingRequest;
import com.shipping.dto.DeliverySpeed;
import com.shipping.dto.ShippingChargeResponse;
import com.shipping.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    @GetMapping("/shipping-charge")
    public Map<String, Double> getShippingCharge(
            @RequestParam Long warehouseId,
            @RequestParam Long customerId,
            @RequestParam DeliverySpeed deliverySpeed
    ) {

        double charge =
                shippingService.calculateShippingCharge(
                        warehouseId,
                        customerId,
                        deliverySpeed
                );

        return Map.of(
                "shippingCharge", charge
        );
    }

    @PostMapping("/shipping-charge/calculate")
    public ShippingChargeResponse calculateShipping(
            @RequestBody CalculateShippingRequest request
    ) {

        return shippingService.calculateFullShipping(
                request.getSellerId(),
                request.getCustomerId(),
                request.getDeliverySpeed()
        );
    }
}
