package com.alpex.validation

import arrow.core.*
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.validated.applicative.applicative
import com.alpex.refined.Refined
import com.alpex.refined.RefinedCompanion

fun <E>vNelApp() = Validated.applicative<NonEmptyList<E>>(Nel.semigroup())

fun <A, R: Refined<A>>A.refinedValidNel(companion: RefinedCompanion<A, R>, error: String): ValidatedNel<String, R> =
    Validated.fromOption(Option.fromNullable(companion.invoke(this))) {error.nel()}

fun <T> List<T>.toNelE(entityName: String) =
    NonEmptyList
        .fromList(this)
        .toEither { "$entityName is empty" }
