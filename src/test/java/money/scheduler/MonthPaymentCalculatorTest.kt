package money.scheduler

import money.scheduler.calculators.MonthPaymentCalculator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.time.LocalDate
import java.time.YearMonth

class MonthPaymentCalculatorTest {

    @Test
    fun `test happy path`() {
        // given
        val start = YearMonth.of(2020, 1)
        val lastPayment = 12
        val interval = 1

        // when
        val months = MonthPaymentCalculator.calculateMonthsOfPayment(lastPayment, interval, start)

        // then
        val expected = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        Assertions.assertEquals(expected, months)
    }

    @Test
    fun `test interval of 2`() {
        // given
        val start = YearMonth.of(2020, 1)
        val interval = 2
        val lastPayment = 11

        // when
        val months = MonthPaymentCalculator.calculateMonthsOfPayment(lastPayment, interval, start)

        // then
        val expected = listOf(1, 3, 5, 7, 9, 11)
        Assertions.assertEquals(expected, months)
    }

    @Test
    fun `first payment should be on starting month`() {
        // given
        val currentDate = LocalDate.of(2020, 1 , 1)
        val mocked = Mockito.mockStatic(LocalDate::class.java)
        Mockito.`when`(LocalDate.now()).thenReturn(currentDate)
        val start = YearMonth.of(2020, 6)
        val interval = 3
        val lastPayment = 12

        // when
        val months = MonthPaymentCalculator.calculateMonthsOfPayment(lastPayment, interval, start)

        // then
        val expected = listOf(6, 9, 12)
        Assertions.assertEquals(expected, months)

        mocked.close()
    }
}