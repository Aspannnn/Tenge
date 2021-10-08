package kz.aspan.tenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kz.aspan.tenge.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var onHand: Boolean = false
    private var calculateSalary = CalculateSalary()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            onHandsCb.setOnCheckedChangeListener { _, isChecked ->
                onHand = isChecked
                val num = salaryEt.text?.replace("\\s+".toRegex(), "") ?: "0"
                val salary = if (onHand) {
                    (num.toDouble() - 4250) / 0.81
                } else {
                    num.toDouble()
                }
                calculateSalary = CalculateSalary(if (salary < 0) num.toDouble() else salary)
                displayInfo()
            }


            salaryEt.addTextChangedListener(object : TextWatcher {
                private var current = ""
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (p0.toString() != current) {
                        salaryEt.removeTextChangedListener(this)

                        val cleanString: String = if (p0.isNullOrEmpty()) {
                            "0.00"
                        } else {
                            p0.replace("\\s+".toRegex(), "")
                        }

                        val num: Double = if (onHand) {
                            (cleanString.toDouble() - 4250) / 0.81
                        } else {
                            cleanString.toDouble()
                        }


                        val formatted =
                            formatAmountDecimal(if (num < 0) cleanString.toDouble() else num)

                        calculateSalary =
                            CalculateSalary(if (num < 0) cleanString.toDouble() else num)

                        displayInfo()

                        current = formatted
                        salaryEt.setText(formatAmount(cleanString))
                        salaryEt.setSelection(formatAmount(cleanString).length)
                        salaryEt.addTextChangedListener(this)
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            })
        }
    }

    private fun displayInfo() {
        binding.apply {
            salaryTv.text = formatAmountDecimal(calculateSalary.salary)
            if (calculateSalary.salary >= 42500) {
                opvTv.text = formatAmountDecimal(calculateSalary.opv())
                ipnTv.text = formatAmountDecimal(calculateSalary.ipn())
                monthlyWageTv.text = formatAmountDecimal(calculateSalary.monthlyWage())
                wagePerYearTv.text = formatAmountDecimal(calculateSalary.wagePerYear())
                salaryPerYearTv.text =
                    formatAmountDecimal(calculateSalary.salaryPerYear())
            } else {
                monthlyWageTv.text = formatAmountDecimal(calculateSalary.salary)
                wagePerYearTv.text = formatAmountDecimal(calculateSalary.salary * 12)
                salaryPerYearTv.text = formatAmountDecimal(calculateSalary.salary * 12)
                opvTv.text = getString(R.string.defaultValueDecimalTenge)
                ipnTv.text = getString(R.string.defaultValueDecimalTenge)
            }
        }
    }


    private fun formatAmountDecimal(num: Double): String {
        return getString(
            R.string.tengeSymbol,
            DecimalFormat("###,##0.00").format(num).replace(',', ' ')
        )
    }

    private fun formatAmount(num: String): String {
        return DecimalFormat("###,###").format(num.toDouble()).replace(',', ' ')
    }

}