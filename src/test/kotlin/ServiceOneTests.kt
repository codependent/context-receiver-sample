import MockitoHelper.anyObject
import MockitoHelper.eq
import arrow.core.Either
import arrow.core.raise.either
import com.codependent.cr.BusinessError
import com.codependent.cr.BusinessError.SomeError
import com.codependent.cr.ServiceOne
import com.codependent.cr.ServiceTwo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.*


class ServiceOneTests {

    @Test
    fun `should return the correct aggregation result`() {

        val serviceTwo = mock(ServiceTwo::class.java)
        val serviceOne = ServiceOne(serviceTwo)

        val result = either {
            `when`(serviceTwo::call.invoke(anyObject(), eq("1"))).thenReturn("1")
            val mockResult = serviceTwo.call("1")
            assertEquals("1", mockResult)

            serviceOne.call("1")
        }

        when(result){
            is Either.Left -> fail()
            is Either.Right -> assertEquals("1", result.value)
        }
    }

    @Test
    fun `should fail`() {
        val serviceTwo = mock(ServiceTwo::class.java)
        val serviceOne = ServiceOne(serviceTwo)

        val result = either {
            `when`(serviceTwo::call.invoke(anyObject(), eq("1")))
                .thenAnswer {
                    raise(SomeError)
                }
            serviceOne.call("1")
        }

        when(result){
            is Either.Left -> println("OK")
            is Either.Right -> fail()
        }
    }

}

object MockitoHelper {
    fun <T> anyObject(): T {
        any<T>()
        return uninitialized()
    }

    fun <T> eq(v: T): T {
        return Mockito.eq(v)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T = null as T
}