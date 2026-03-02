package com.shipping.service;

import com.shipping.dto.DeliverySpeed;
import com.shipping.dto.ShippingChargeResponse;

public interface ShippingService {

    double calculateShippingCharge(Long warehouseId,
                                   Long customerId,
                                   DeliverySpeed speed);

    ShippingChargeResponse calculateFullShipping(Long sellerId,
                                                 Long customerId,
                                                 DeliverySpeed speed);
}
