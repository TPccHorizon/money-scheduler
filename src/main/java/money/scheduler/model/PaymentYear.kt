package money.scheduler.model

import java.util.LinkedList

class PaymentYear(val year: Int, val months: List<Month>) {

    companion object {
        fun of(year: Int, months: List<Int?>): PaymentYear {
            val rawMonths: MutableList<Month> = LinkedList()
            for (i in 1..12) {
                rawMonths.add(Month(i, months.contains(i)))
            }
            return PaymentYear(year, rawMonths)
        }
    }
}