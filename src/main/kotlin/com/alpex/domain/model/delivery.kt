package com.alpex.domain.model

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.left
import arrow.core.right
import java.time.Instant
import java.util.*

inline class PickupPointId(val id: UUID)
data class PickupPoint(val id: PickupPointId, val title: String, val address: Address)

sealed class DeliveryType
data class Postal(val address: Address): DeliveryType()
data class Courier(val address: Address): DeliveryType()
data class Pickup(val point: PickupPointId): DeliveryType()


sealed class DeliveryStatus { abstract val time: Instant }
data class New        (override val time: Instant): DeliveryStatus()
data class Processing (override val time: Instant): DeliveryStatus()
data class Sent       (override val time: Instant): DeliveryStatus()
data class Shipping   (override val time: Instant): DeliveryStatus()
data class Delivered  (override val time: Instant): DeliveryStatus()
data class Error      (override val time: Instant, val msg: String): DeliveryStatus()

sealed class DeliveryCommand
object     DeliveryNext: DeliveryCommand()
data class DeliveryError(val msg: String): DeliveryCommand()

fun DeliveryStatus.next(): Either<String, DeliveryStatus> {
    fun now() = Instant.now()
    return when (this) {
        is New        -> Processing(now()).right()
        is Processing -> Sent(now()).right()
        is Sent       -> Shipping(now()).right()
        is Shipping   -> Delivered(now()).right()
        is Delivered  -> "Already delivered".left()
        else          -> "Invalid transition".left()
    }
}

fun DeliveryStatus.error(msg: String): Error = Error(Instant.now(), msg)

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


