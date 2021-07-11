package money.scheduler.calculators

import java.util.*

class MonthPaymentCalculator {

    companion object {
        /**
         * Returns a List containing the month numbers (e.g. 5 for May) in which a payment occurs.
         * @param monthOfLastPayment : The last month in a year where a payment occurs.
         * @param paymentInterval : The interval (in months) every which a payment takes place.
         */
        fun calculateMonthsOfPayment(monthOfLastPayment: Int, paymentInterval: Int): List<Int?>? {
            val paymentMonths: MutableList<Int> = mutableListOf()
            paymentMonths.add(monthOfLastPayment)
            var currentMonth = monthOfLastPayment
            // go from last month backwards
            do {
                currentMonth -= paymentInterval
                paymentMonths.add(currentMonth)
            } while (currentMonth > paymentInterval)
            paymentMonths.reverse()
            return paymentMonths
        }
    }
}