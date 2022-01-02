package money.scheduler

import java.time.LocalDate

interface MoneyScheduler {
    fun calculateCurrentYearSchedule(startingMonth: Int, startingYear: Int, paymentInterval: Int): List<Int?>
    fun calculateCurrentYearSchedule(startDate: LocalDate, paymentInterval: Int): List<Int?>
}