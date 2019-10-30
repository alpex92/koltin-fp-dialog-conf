package com.alpex.request

import com.alpex.domain.model.DeliveryType
import com.alpex.domain.model.Gender
import java.util.*

data class UserInfo(
    val name: String,
    val lastName: String,
    val age: Int,
    val gender: Gender
)

data class CreateDeliveryRequest(
    val positions: List<ItemPosition>,
    val deliveryType: DeliveryType,
    val userInfo: UserInfo
)

data class ItemPosition(val id: UUID, val amount: Int)

