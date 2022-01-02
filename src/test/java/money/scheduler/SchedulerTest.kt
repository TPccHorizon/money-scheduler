package money.scheduler

import money.scheduler.calculators.MonthPaymentCalculator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mockStatic
import java.time.LocalDate
import java.time.YearMonth
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
    fun testSchedule(startingMonth: Int, startingYear: Int, currentYear: Int, paymentInterval: Int, expected: List<Int>) {
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
        assertThrows(IllegalArgumentException::class.java) {
            scheduler.calculateCurrentYearSchedule(1, yearInFuture, 1)
        }
        assertThrows(IllegalArgumentException::class.java) {
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
        assertThrows(IllegalArgumentException::class.java) {
            scheduler.calculateCurrentYearSchedule(1, 2020, intervalTooHigh)
        }
        assertThrows(IllegalArgumentException::class.java) {
            scheduler.calculateCurrentYearSchedule(1, 2020, intervalTooLow)
        }
    }

    @Test
    fun `test method overload`() {
        // given
        val startYear = 2021
        val startMonth = 1
        val interval = 2

        val startDate = LocalDate.of(startYear, startMonth, 1)

        // when
        val results = mutableListOf<List<Int>>()
        results.add(scheduler.calculateCurrentYearSchedule(startDate, interval))
        results.add(scheduler.calculateCurrentYearSchedule(startMonth, startYear, interval))
        results.add(scheduler.calculateCurrentYearSchedule(YearMonth.of(startYear, startMonth), interval))

        // then
        val expected = listOf(1, 3, 5, 7, 9, 11)
        results.forEach {
            assertEquals(expected, it)
        }

    }
}