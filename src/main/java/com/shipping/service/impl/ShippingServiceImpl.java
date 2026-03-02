package com.shipping.service.impl;

import com.shipping.dto.DeliverySpeed;
import com.shipping.dto.ShippingChargeResponse;
import com.shipping.entity.Customer;
import com.shipping.entity.Product;
import com.shipping.entity.Warehouse;
import com.shipping.exception.ResourceNotFoundException;
import com.shipping.repository.CustomerRepository;
import com.shipping.repository.ProductRepository;
import com.shipping.repository.WarehouseRepository;
import com.shipping.service.ShippingService;
import com.shipping.service.WarehouseService;
import com.shipping.util.DistanceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingServiceImpl implements ShippingService {

    private final WarehouseRepository warehouseRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final WarehouseService warehouseService;

    private static final double STANDARD_COURIER = 10;

    @Override
    public double calculateShippingCharge(Long warehouseId,
                                          Long customerId,
                                          DeliverySpeed speed) {

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Warehouse not found"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        Product product = productRepository.findById(1L)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        double distance = DistanceUtil.calculateDistance(
                warehouse.getLatitude(),
                warehouse.getLongitude(),
                customer.getLatitude(),
                customer.getLongitude()
        );

        double rate;

        if (distance >= 500)
            rate = 1;
        else if (distance >= 100)
            rate = 2;
        else
            rate = 3;

        double shipping = distance * rate * product.getWeight();

        if (speed == DeliverySpeed.express)
            shipping += product.getWeight() * 1.2;

        shipping += STANDARD_COURIER;

        return Math.round(shipping * 100.0) / 100.0;
    }

    @Override
    public ShippingChargeResponse calculateFullShipping(Long sellerId,
                                                        Long customerId,
                                                        DeliverySpeed speed) {

        Warehouse warehouse =
                warehouseService.findNearestWarehouse(sellerId, 1L);

        double charge =
                calculateShippingCharge(warehouse.getId(),
                        customerId,
                        speed);

        return ShippingChargeResponse.builder()
                .shippingCharge(charge)
                .nearestWarehouse(
                        ShippingChargeResponse.NearestWarehouse.builder()
                                .warehouseId(warehouse.getId())
                                .warehouseLocation(
                                        ShippingChargeResponse.Location.builder()
                                                .lat(warehouse.getLatitude())
                                                .longitude(warehouse.getLongitude())
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}