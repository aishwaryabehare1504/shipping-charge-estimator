package com.shipping.controller;

import com.shipping.entity.Warehouse;
import com.shipping.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping("/nearest")
    public Map<String, Object> getNearestWarehouse(
            @RequestParam Long sellerId,
            @RequestParam Long productId
    ) {

        Warehouse warehouse =
                warehouseService.findNearestWarehouse(sellerId, productId);

        return Map.of(
                "warehouseId", warehouse.getId(),
                "warehouseLocation", Map.of(
                        "lat", warehouse.getLatitude(),
                        "long", warehouse.getLongitude()
                )
        );
    }
}
