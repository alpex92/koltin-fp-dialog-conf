package com.alpex.domain.validation

import arrow.core.*
import com.alpex.domain.model.*
import com.alpex.request.CreateDeliveryRequest
import com.alpex.validation.refinedValidNel
import com.alpex.validation.toNelE
import com.alpex.validation.vNelApp
import com.alpex.request.UserInfo as RequestUserInfo
import com.alpex.request.ItemPosition as RequestItemPosition

object Validation {

    private val app = vNelApp<String>()

    fun create(input: CreateDeliveryRequest): ValidatedNel<String, Pair<Delivery, UserInfo>> {

        val positionsE = { list: List<RequestItemPosition> ->
            list.toNelE("Positions")
                .mapLeft { it.nel() }
                .flatMap(::positions)
        }

        return app.map(
            Validated.fromEither(positionsE(input.positions)),
            userInfo(input.userInfo)
        ) { (positions, userInfo) ->
            val delivery = Delivery(DeliveryId.new(), positions, input.deliveryType)
            Pair(delivery, userInfo)
        }.fix()
    }

    fun userInfo(input: RequestUserInfo): ValidatedNel<String, UserInfo> = app.map(
        input.name.refinedValidNel(NonEmptyString, "Name is empty"),
        input.lastName.refinedValidNel(NonEmptyString, "Last is empty"),
        Age(input.age).refinedValidNel(Adult, "Age should be > 18")
    ) { (name, lastName, age) -> UserInfo(name, lastName, age, input.gender) }.fix()


    fun positions(nel: Nel<RequestItemPosition>) =
        nel.map(::position)
            .sequence(app)
            .fix()
            .toEither()

    fun position(input: RequestItemPosition): ValidatedNel<String, ItemPosition> =
        input.amount
            .refinedValidNel(PositiveInt, "Amount should be > 0")
            .map { amount -> ItemPosition(ItemId(input.id), amount) }

}
