package com.codependent.cr

import arrow.core.raise.either

class ServiceOne(private val serviceTwo: ServiceTwo) {

    fun call(code: String): String {
        val result = either {
            val r = serviceTwo.call(code)
            r
        }

        return result.fold(
            { "-1" })
        { it }
    }
}