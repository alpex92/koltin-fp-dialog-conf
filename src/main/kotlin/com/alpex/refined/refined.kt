package com.alpex.refined

// TODO: type-class and inline class?
interface Refined<T> {
    val value: T
}

infix fun <T, R: Refined<T>>T.conforms(to: RefinedCompanion<T, R>): Boolean = to.check(this)

abstract class RefinedCompanion<T, R: Refined<T>>(
    val check: (T) -> Boolean,
    private val createInternal: (T) -> R?
) {
    operator fun invoke(x: T): R? = if (check(x)) createInternal(x) else null
}


