package money.scheduler

import money.scheduler.calculators.MonthPaymentCalculator
import java.time.LocalDate

class Scheduler : MoneyScheduler{

    private val DEFAULT_MONTHS = java.util.List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    override fun calculateCurrentYearSchedule(startingMonth: Int, startingYear: Int, paymentInterval: Int): List<Int?>? {
        validateInput(startingYear, startingMonth, paymentInterval)
        if (paymentInterval == 1) {
            // This means the schedule is the same for every year
            return DEFAULT_MONTHS
        }
        val currentYearLastPayment = getCurrentYearLastPayment(startingMonth, startingYear, paymentInterval)
        return MonthPaymentCalculator.calculateMonthsOfPayment(currentYearLastPayment, paymentInterval)
    }

    override fun calculateCurrentYearSchedule(startDate: LocalDate, paymentInterval: Int): List<Int?>? {
        return calculateCurrentYearSchedule(startDate.monthValue, startDate.year, paymentInterval)
    }

    private fun getCurrentYearLastPayment(startingMonth: Int, startingYear: Int, paymentInterval: Int): Int {
        val months = getTotalMonths(startingYear)
        val mod = months % paymentInterval
        val initialYearLastPayment = 12 - mod
        var currentYearLastPayment = initialYearLastPayment + startingMonth
        if (currentYearLastPayment > 12) {
            currentYearLastPayment -= paymentInterval
        }
        return currentYearLastPayment
    }

    private fun validateInput(startingYear: Int, startingMonth: Int, paymentInterval: Int) {
        require(!(startingMonth < 1 || startingMonth > 12)) { "Invalid month $startingMonth. It should range from 1 (inclusive) to 12 (inclusive)" }
        require(startingYear <= LocalDate.now().year) { "$startingYear is in the future" }
        require(startingYear >= 0) { "The Year cannot be negative" }
        require(!(paymentInterval > 12 || paymentInterval < 1)) { "Invalid Payment Interval $paymentInterval. It should range from 1 (inclusive) to 12 (inclusive)" }
    }

    private fun getTotalMonths(startingYear: Int): Int {
        val thisYear = LocalDate.now().year
        return getYearDelta(thisYear, startingYear) * 12
    }

    private fun getYearDelta(year1: Int, year2: Int): Int {
        return Math.abs(year2 - year1)
    }
}