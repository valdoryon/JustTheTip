package com.example.justthetip

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.ComponentActivity

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 10
private const val MIN_PEOPLE = 1

class MainActivity : ComponentActivity() {
    private lateinit var etBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipPercentLabel: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var etPeople: EditText
    private lateinit var tvTotalTipDivided: TextView
    private lateinit var tvTotalDivided: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTipPercentLabel = findViewById(R.id.tvTipPercentLabel)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)

        etPeople = findViewById(R.id.etPeople)
        tvTotalTipDivided = findViewById(R.id.tvTotalTipDivided)
        tvTotalDivided = findViewById(R.id.tvTotalDivided)


        // Setting bar & percent label with initial percent
        seekBarTip.progress = INITIAL_TIP_PERCENT
        tvTipPercentLabel.text = "$INITIAL_TIP_PERCENT%"




        seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvTipPercentLabel.text = "$progress%"
                computeTipAndTotal()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        etBaseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                computeTipAndTotal()
            }

        })

        etPeople.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                computeTipAndTotal()
            }

        })

    }

    private fun computeTipAndTotal() {
        if (etBaseAmount.text.isEmpty()) {
            tvTipAmount.text = ""
            tvTotalAmount.text = ""
            return
        }

        // 1. Get the value base and tip percent
        val baseAmount = etBaseAmount.text.toString().toDouble()
        val tipPercent = seekBarTip.progress

        //1.a Get the value of people


        // 2. Compute the tip and total
        val tipAmount = (baseAmount * tipPercent) / 100
        val totalAmount = baseAmount + tipAmount


        //3. Update the UI
        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTotalAmount.text = "%.2f".format(totalAmount)

        if (etPeople.text.isEmpty() ||  etPeople.text.toString() == "0"  ||  etPeople.text.toString() == "1") {
            tvTotalTipDivided.text = ""
            tvTotalDivided.text = ""
            return
        } else {
            val peopleQuantity = etPeople.text.toString().toInt()
            //2.a Compute tip and total divided
            val totalTipDivided = tipAmount / peopleQuantity
            val totalAmountDivided = totalAmount / peopleQuantity

            //3.a Update divided UI
            tvTotalTipDivided.text = "%.2f".format(totalTipDivided) + " x $peopleQuantity"
            tvTotalDivided.text = "%.2f".format(totalAmountDivided) + " x $peopleQuantity"

        }

    }
}