package com.alpex.domain.model

import arrow.core.NonEmptyList
import java.time.Instant
import java.util.*

inline class PickupPointId(val id: UUID)
data class PickupPoint(val id: PickupPointId, val title: String, val address: Address)

sealed class DeliveryType
data class Postal(val address: Address): DeliveryType()
data class Courier(val address: Address): DeliveryType()
data class Pickup(val point: PickupPointId): DeliveryType()

// TODO: Ordering, state machine?
sealed class DeliveryStatus { abstract val time: Instant }
data class New        (override val time: Instant): DeliveryStatus()
data class Processing (override val time: Instant): DeliveryStatus()
data class Sent       (override val time: Instant): DeliveryStatus()
data class Shipping   (override val time: Instant): DeliveryStatus()
data class Delivered  (override val time: Instant): DeliveryStatus()
data class Error      (override val time: Instant, val msg: String): DeliveryStatus()


inline class DeliveryId(val id: UUID) {
    companion object {
        fun new() = DeliveryId(UUID.randomUUID())
    }
}

data class Delivery(
    val id: DeliveryId,
    val positions: NonEmptyList<ItemPosition>,
    val deliveryType: DeliveryType,
    val status: DeliveryStatus = New(Instant.now())
)


