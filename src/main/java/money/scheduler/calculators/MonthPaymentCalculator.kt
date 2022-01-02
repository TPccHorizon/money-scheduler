package money.scheduler.calculators

import java.time.LocalDate
import java.time.YearMonth

class MonthPaymentCalculator {

    companion object {
        /**
         * Returns a List containing the month numbers (e.g. 5 for May) in which a payment occurs.
         * @param monthOfLastPayment : The last month in a year where a payment occurs.
         * @param paymentInterval : The interval (in months) every which a payment takes place.
         * @param start : The year and month where the payment starts.
         */
        fun calculateMonthsOfPayment(monthOfLastPayment: Int, paymentInterval: Int, start: YearMonth): List<Int> {
            val paymentMonths: MutableList<Int> = mutableListOf()
            paymentMonths.add(monthOfLastPayment)
            var currentMonth = monthOfLastPayment

            val isCurrentYear = LocalDate.now().year == start.year
            // go from last month backwards
            val firstMonthOfPayment = if (isCurrentYear) start.month.value else paymentInterval
            while (currentMonth > firstMonthOfPayment) {
                currentMonth -= paymentInterval
                paymentMonths.add(currentMonth)
            }
            paymentMonths.reverse()
            return paymentMonths
        }
    }
}