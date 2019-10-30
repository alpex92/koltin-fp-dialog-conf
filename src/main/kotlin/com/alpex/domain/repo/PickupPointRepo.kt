package com.alpex.domain.repo

import arrow.Kind
import com.alpex.domain.model.PickupPoint
import com.alpex.domain.model.PickupPointId

interface PickupPointRepo<F> {
    fun find(id: PickupPointId): Kind<F, PickupPoint?>
    // TODO: Geo-search?
    fun add(pickupPoint: PickupPoint): Kind<F, Unit>
}