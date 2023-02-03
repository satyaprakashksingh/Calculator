package com.learning.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var lastSymbol : Boolean = false
    var lastDot : Boolean = false
    var expression : TextView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        expression = findViewById(R.id.expression)
    }
    fun onDigit(view : View){
        expression?.append((view as Button).text)
        lastSymbol = true
    }

    fun onClear(view : View){
        expression?.text = expression?.text.toString().substring(0, expression?.text.toString().length - 1)
        lastSymbol= false
        lastDot = false
    }
    fun onDecimalPoint(view: View) {

        // If the last appended value is numeric then append(".") or don't.
        if (lastSymbol && !lastDot) {
            expression?.append(".")
            lastSymbol = false // Update the flag
            lastDot = true // Update the flag
        }
    }

    /**
     * Append +,-,*,/ operators to the TextView as per the Button.Text
     */
    fun onOperator(view: View) {
        expression?.text?.let {
            if (lastSymbol && !isOperatorAdded(it.toString())) {
                expression?.append((view as Button).text)
                lastSymbol = false // Update the flag
                lastDot = false    // Reset the DOT flag
            }
        }

    }

    /**
     * Calculate the output
     */
    fun onEqual(view: View) {
        // If the last input is a number only, solution can be found.
        if (lastSymbol) {
            // Read the textView value
            var tvValue = expression?.text.toString()
            var prefix = ""
            try {

                // Here if the value starts with '-' then we will separate it and perform the calculation with value.
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1);
                }

                // If the inputValue contains the Division operator
                when {
                    tvValue.contains("/") -> {
                        // Will split the inputValue using Division operator
                        val splitedValue = tvValue.split("/")

                        var one = splitedValue[0] // Value One
                        val two = splitedValue[1] // Value Two

                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                            one = prefix + one
                        }

                        /*Here as the value one and two will be calculated based on the operator and
                                if the result contains the zero after decimal point will remove it.
                                And display the result to TextView*/
                        expression?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }
                    tvValue.contains("*") -> {
                        // If the inputValue contains the Multiplication operator
                        // Will split the inputValue using Multiplication operator
                        val splitedValue = tvValue.split("*")

                        var one = splitedValue[0] // Value One
                        val two = splitedValue[1] // Value Two

                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                            one = prefix + one
                        }

                        /** Here as the value one and two will be calculated based on the operator and
                        if the result contains the zero after decimal point will remove it.
                        And display the result to TextView
                         */
                        expression?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    }
                    tvValue.contains("-") -> {

                        // If the inputValue contains the Subtraction operator
                        // Will split the inputValue using Subtraction operator
                        val splitedValue = tvValue.split("-")

                        var one = splitedValue[0] // Value One
                        val two = splitedValue[1] // Value Two

                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                            one = prefix + one
                        }

                        /** Here as the value one and two will be calculated based on the operator and
                        if the result contains the zero after decimal point will remove it.
                        And display the result to TextView
                         */
                        expression?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }
                    tvValue.contains("+") -> {
                        // If the inputValue contains the Addition operator
                        // Will split the inputValue using Addition operator
                        val splitedValue = tvValue.split("+")

                        var one = splitedValue[0] // Value One
                        val two = splitedValue[1] // Value Two

                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                            one = prefix + one
                        }

                        /**Here as the value one and two will be calculated based on the operator and
                        if the result contains the zero after decimal point will remove it.
                        And display the result to TextView
                         */
                        expression?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Remove the zero after decimal point
     */
    private fun removeZeroAfterDot(result: String): String {

        var value = result

        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }

        return value
    }

    /**
     * It is used to check whether any of the operator is used or not.
     */
    private fun isOperatorAdded(value: String): Boolean {

        /**
         * Here first we will check that if the value starts with "-" then will ignore it.
         * As it is the result value and perform further calculation.
         */

        return if (value.startsWith("-")) {
            false
        } else {
            (value.contains("/")
                    || value.contains("*")
                    || value.contains("-")
                    || value.contains("+"))
        }
    }
}