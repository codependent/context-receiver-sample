package com.codependent.cr

import arrow.core.raise.Raise

class ServiceTwo {
    context(Raise<BusinessError>)
    fun call(id: String): String {
        return id
    }

}