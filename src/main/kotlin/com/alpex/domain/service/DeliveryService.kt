package com.alpex.domain.service

import arrow.Kind
import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.right
import arrow.mtl.EitherT
import arrow.mtl.extensions.eithert.monad.monad
import arrow.mtl.fix
import arrow.typeclasses.Applicative
import arrow.typeclasses.Monad

import com.alpex.domain.model.*
import com.alpex.domain.repo.DeliveryRepo
import com.alpex.domain.repo.UserRepo
import com.alpex.domain.validation.Validation
import com.alpex.request.CreateDeliveryRequest

sealed class DeliveryServiceError
data class ValidationError(val errors: NonEmptyList<String>): DeliveryServiceError()
// TODO: Other error types if applicable?

class DeliveryService<F>(
    private val M: Monad<F>,
    private val A: Applicative<F>,
    private val deliveryRepo: DeliveryRepo<F>,
    private val userInfoRepo: UserRepo<F>
): Monad<F> by M {

    private val ETM = EitherT.monad<F, DeliveryServiceError>(M)

    fun create(request: CreateDeliveryRequest): Kind<F, Either<DeliveryServiceError, Unit>> {

        val deliveryAndUserInfoE = Validation
            .create(request)
            .toEither()
            .mapLeft(::ValidationError)

        return ETM.fx.monad {
            val (pair) =  EitherT.fromEither(A, deliveryAndUserInfoE)k
            val (delivery, userInfo) = pair
            val (_) = EitherT.liftF<F, DeliveryServiceError, Unit>(M, deliveryRepo.add(delivery))
            // TODO: Check before creating new user every time
            val (_) = EitherT.liftF<F, DeliveryServiceError, Unit>(M, userInfoRepo.add(User(UserId.new(), userInfo)))
        }.fix().value()

    }

    fun details(deliveryId: DeliveryId): Kind<F, Unit?> {

        // TODO: Get Delivery, Point info (if needed), combine it?

    }


    // TODO: Not found
    fun changeStatus(id: DeliveryId, cmd: DeliveryCommand): Kind<F, Either<String, Unit>?> {

        // TODO: Optional and OptionT?
        val res = deliveryRepo.find(id).map {
            it?.let { delivery ->
              when(cmd) {
                  is DeliveryNext  -> delivery.status.next()
                  is DeliveryError -> delivery.status.error(cmd.msg).right()
              }
            }
        }

        // TODO: Change status or return error

    }
}