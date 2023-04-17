import arrow.core.Either
import arrow.core.raise.either
import com.codependent.cr.ServiceOne
import com.codependent.cr.ServiceTwo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ServiceOneTests {

    @Test
    fun `should return the correct aggregation result`() {

        val serviceTwo = mock(ServiceTwo::class.java)

        val result = either {

            val serviceOne = ServiceOne(serviceTwo)

            `when`(serviceTwo.call("1")).thenReturn("1")

            val mockResult = serviceTwo.call("1")
            assertEquals("1", mockResult)

            serviceOne.call("1")
        }

        when(result){
            is Either.Left -> fail()
            is Either.Right -> assertEquals("1", result.value)
        }
    }

}