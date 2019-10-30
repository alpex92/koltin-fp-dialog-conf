package com.alpex.domain.repo

import arrow.Kind
import com.alpex.domain.model.Delivery
import com.alpex.domain.model.DeliveryId

interface DeliveryRepo<F> {
    fun add(delivery: Delivery): Kind<F, Unit>
    fun update(delivery: Delivery): Kind<F, Unit>
    fun find(deliveryId: DeliveryId): Kind<F, Delivery?>

}