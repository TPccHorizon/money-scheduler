package money.scheduler

import money.scheduler.calculators.MonthPaymentCalculator
import money.scheduler.model.DECEMBER
import java.time.LocalDate
import java.time.YearMonth
import kotlin.math.abs

val DEFAULT_MONTHS = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
const val MAX_MONTH = DECEMBER

class Scheduler : MoneyScheduler {


    override fun calculateCurrentYearSchedule(startingMonth: Int, startingYear: Int, paymentInterval: Int): List<Int> {
        return calculateCurrentYearSchedule(YearMonth.of(startingYear, startingMonth), paymentInterval)
    }

    override fun calculateCurrentYearSchedule(startDate: LocalDate, paymentInterval: Int): List<Int> {
        return calculateCurrentYearSchedule(YearMonth.of(startDate.year, startDate.month), paymentInterval)
    }

    override fun calculateCurrentYearSchedule(start: YearMonth, paymentInterval: Int): List<Int> {
        validateInput(start, paymentInterval)
        if (paymentInterval == 1) {
            // This means the schedule is the same for every year
            return DEFAULT_MONTHS
        }
        val currentYearLastPayment = getCurrentYearLastPayment(start, paymentInterval)
        return MonthPaymentCalculator.calculateMonthsOfPayment(currentYearLastPayment, paymentInterval, start)
    }

    private fun getCurrentYearLastPayment(start: YearMonth, paymentInterval: Int): Int {
        val months = getTotalMonths(start.year)
        val mod = months % paymentInterval
        val initialYearLastPayment = MAX_MONTH - mod
        var currentYearLastPayment = initialYearLastPayment + start.month.value
        while (currentYearLastPayment > MAX_MONTH) {
            currentYearLastPayment -= paymentInterval
        }
        return currentYearLastPayment
    }

    private fun validateInput(start: YearMonth, paymentInterval: Int) {
        val startingYear = start.year
        require(startingYear <= currentYear()) { "$startingYear is in the future" }
        require(startingYear >= 0) { "The Year cannot be negative" }
        require(!(paymentInterval > DECEMBER || paymentInterval < 1)) { "Invalid Payment Interval $paymentInterval. It should range from 1 (inclusive) to 12 (inclusive)" }
    }

    /**
     * Returns the total number of months from 'startingYear' to the current year (always a multiple of 12).
     * @param startingYear : The year from which the calculation should start.
     * */
    private fun getTotalMonths(startingYear: Int): Int {
        val thisYear = currentYear()
        return getYearDelta(thisYear, startingYear) * MAX_MONTH
    }

    private fun getYearDelta(year1: Int, year2: Int): Int {
        return abs(year2 - year1)
    }

    private fun currentYear() : Int = LocalDate.now().year
}