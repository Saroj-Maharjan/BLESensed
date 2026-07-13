package com.anchor.ugzeroharm.data.remote.network.ktor

import com.anchor.ugzeroharm.common.Either
import io.ktor.client.call.body

/**
 * Created by Ali Sadeghi
 * on 15,Apr,2025
 */

// Factory to create call adapters
class CallAdapterFactory {
    inline fun <reified T> create(): KtorCallAdapter<T, Either<T>> =
        EitherCallAdapter { response ->
            response.body<T>()
        }
}
