package com.shipping.service;

import com.shipping.entity.Warehouse;

public interface WarehouseService {

    Warehouse findNearestWarehouse(Long sellerId, Long productId);
}
