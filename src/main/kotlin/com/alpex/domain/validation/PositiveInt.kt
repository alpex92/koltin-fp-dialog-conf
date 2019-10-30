package com.alpex.domain.validation

import com.alpex.domain.model.Age
import com.alpex.refined.Refined
import com.alpex.refined.RefinedCompanion
import com.alpex.refined.conforms

class PositiveInt private constructor(override val value: Int): Refined<Int> {
    init { assert(value conforms PositiveInt) }
    companion object: RefinedCompanion<Int, PositiveInt>({ it > 0 }, ::PositiveInt)
}

class NonEmptyString private constructor(override val value: String): Refined<String> {
    init { assert(value conforms NonEmptyString) }
    companion object: RefinedCompanion<String, NonEmptyString>({ !it.isBlank() }, ::NonEmptyString)
}

class Adult private constructor(override val value: Age): Refined<Age> {
    init { assert(value conforms Adult) }
    companion object: RefinedCompanion<Age, Adult>({ it.value > 18 }, ::Adult)
}