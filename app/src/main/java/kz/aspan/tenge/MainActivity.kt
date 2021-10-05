package kz.aspan.tenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import kz.aspan.tenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.salaryEt.addTextChangedListener {
            val num: Double = if (it.isNullOrBlank()) 0.00 else it.toString().toDouble()
            binding.salaryTv.text = getString(R.string.tengeSymbol, num)
            if (num >= 42500) {
                val opv: Double = num * 0.1
                val ipn: Double = (num - opv - 42500) * 0.1
                val salaryPerMonth: Double = num - opv - ipn
                val salaryPerYear: Double = salaryPerMonth * 12
                val salaryForTheYear: Double = num * 12
                binding.opvTv.text = getString(R.string.tengeSymbol, opv)
                binding.ipnTv.text = getString(R.string.tengeSymbol, if (ipn > 0) ipn else 0.00)
                binding.salaryPerMonthTv.text = getString(R.string.tengeSymbol, salaryPerMonth)
                binding.salaryPerYearTv.text = getString(R.string.tengeSymbol, salaryPerYear)
                binding.salaryForTheYearTv.text = getString(R.string.tengeSymbol, salaryForTheYear)
            } else {
                binding.salaryPerMonthTv.text = getString(R.string.tengeSymbol, num)
                binding.salaryPerYearTv.text = getString(R.string.tengeSymbol, num * 12)
                binding.salaryForTheYearTv.text = getString(R.string.tengeSymbol, num *12)
                binding.opvTv.text = getString(R.string.tengeSymbol, 0.00)
                binding.ipnTv.text = getString(R.string.tengeSymbol, 0.00)
            }
        }
    }
}