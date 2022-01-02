package money.scheduler

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mockStatic
import java.time.LocalDate
import java.util.stream.Stream

internal class SchedulerTest {
    internal class MonthsArgumentsProvider : ArgumentsProvider {
        @Throws(Exception::class)
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments?> {
            return Stream.of(
                    Arguments.of(1, 2011, 2021, 1, listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)),
                    Arguments.of(1, 2021, 2021, 2, listOf(1, 3, 5, 7, 9, 11)),
                    Arguments.of(3, 2021, 2021, 2, listOf(3, 5, 7, 9, 11)),
                    Arguments.of(3, 2021, 2022, 2, listOf(1, 3, 5, 7, 9, 11)),
                    Arguments.of(5, 2021, 2022, 2, listOf(1, 3, 5, 7, 9, 11))
            )
        }
    }

    private val scheduler = Scheduler()

    @ParameterizedTest
    @ArgumentsSource(MonthsArgumentsProvider::class)
    fun testSchedule(startingMonth: Int, startingYear: Int, currentYear: Int, paymentInterval: Int, expected: List<Int?>?) {
        // given
        val currentDate = LocalDate.of(currentYear, 1 , 1)
        val mocked = mockStatic(LocalDate::class.java)
        `when`(LocalDate.now()).thenReturn(currentDate)

        // when
        val result = scheduler.calculateCurrentYearSchedule(startingMonth, startingYear, paymentInterval)
        // then
        Assertions.assertEquals(expected, result)
        mocked.close()
    }

    @Test
    fun `invalid year should throw`() {
        // given
        val yearInFuture = 2030
        val yearInPast = -1

        val currentDate = LocalDate.of(2021, 1, 1)
        val mocked = mockStatic(LocalDate::class.java)
        `when`(LocalDate.now()).thenReturn(currentDate)
        // when
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            scheduler.calculateCurrentYearSchedule(1, yearInFuture, 1)
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            scheduler.calculateCurrentYearSchedule(1, yearInPast, 1)
        }
        mocked.close()
    }

    @Test
    fun `invalid paymentInterval should throw`() {
        // given
        val intervalTooHigh = 13
        val intervalTooLow = 0

        // when // then
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            scheduler.calculateCurrentYearSchedule(1, 2020, intervalTooHigh)
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            scheduler.calculateCurrentYearSchedule(1, 2020, intervalTooLow)
        }
    }
}