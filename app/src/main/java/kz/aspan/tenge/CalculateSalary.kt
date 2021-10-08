package kz.aspan.tenge

data class CalculateSalary(
    val salary: Double = 0.00
) {

    fun opv(): Double {
        return salary * 0.1
    }

    fun ipn(): Double {
        val ipn = (salary - opv() - 42500) * 0.1
        return if (ipn >= 0) ipn else 0.00
    }

    fun monthlyWage(): Double {
        return salary - opv() - ipn()
    }

    fun wagePerYear(): Double {
        return monthlyWage() * 12
    }

    fun salaryPerYear(): Double {
        return salary * 12
    }

}