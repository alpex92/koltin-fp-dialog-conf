package com.alpex.domain.model

import com.alpex.domain.validation.NonEmptyString
import com.alpex.domain.validation.PositiveInt

inline class City(val name: NonEmptyString)
inline class Street(val name: NonEmptyString)
inline class HouseNumber(val value: PositiveInt)
inline class PostalCode(val value: PositiveInt)

data class Address(
    val city: City,
    val street: Street,
    val house: HouseNumber,
    val building: NonEmptyString?,
    val postalCode: PostalCode
)