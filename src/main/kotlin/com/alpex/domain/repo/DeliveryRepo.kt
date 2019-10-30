package com.alpex.domain.repo

import arrow.Kind
import com.alpex.domain.model.Delivery

interface DeliveryRepo<F> {
    fun add(delivery: Delivery): Kind<F, Unit>
    fun update(delivery: Delivery): Kind<F, Unit>
}