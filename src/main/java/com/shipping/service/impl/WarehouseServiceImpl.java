package com.shipping.service.impl;

import com.shipping.entity.Seller;
import com.shipping.entity.Warehouse;
import com.shipping.repository.SellerRepository;
import com.shipping.repository.WarehouseRepository;
import com.shipping.service.WarehouseService;
import com.shipping.util.DistanceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final SellerRepository sellerRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public Warehouse findNearestWarehouse(Long sellerId, Long productId) {

        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        List<Warehouse> warehouses = warehouseRepository.findAll();

        Warehouse nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Warehouse w : warehouses) {

            double distance =
                    DistanceUtil.calculateDistance(
                            seller.getLatitude(),
                            seller.getLongitude(),
                            w.getLatitude(),
                            w.getLongitude()
                    );

            if (distance < minDistance) {
                minDistance = distance;
                nearest = w;
            }
        }

        return nearest;
    }
}
