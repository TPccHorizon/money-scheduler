package money.scheduler

import java.time.LocalDate
import java.time.YearMonth

interface MoneyScheduler {
    fun calculateCurrentYearSchedule(startingMonth: Int, startingYear: Int, paymentInterval: Int): List<Int>
    fun calculateCurrentYearSchedule(startDate: LocalDate, paymentInterval: Int): List<Int>
    fun calculateCurrentYearSchedule(start: YearMonth, paymentInterval: Int): List<Int>
}