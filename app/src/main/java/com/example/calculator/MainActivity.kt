package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var canAddOperation = false
    private var canAddDecimal = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun numberAction(view: View) {
        if (view is Button) {
            if (view.text == ".") {
                if (canAddDecimal)
                    textView.append(view.text)
                canAddDecimal = false
            }
            else
                textView.append(view.text)
            canAddOperation = true
        }
    }

    fun operationAction(view: View) {
        if (view is Button && canAddOperation) {
            textView.append(view.text)
            canAddOperation = false
        }
    }

    fun allClearAction(view: View) {
        textView.text = ""

    }

    fun backSpaceAction(view: View) {
        val length = textView.length()

        if (length > 0 ) {
            textView.text = textView.text.subSequence(0, length - 1)
        }
    }

    fun equalsAction(view: View) {
        textView.text = calculateResults()
    }

    private fun calculateResults(): String {

        val digitsOperators = digitsOperator()
        if(digitsOperators.isEmpty()) return ""

        val timesDivision =  timesDivisionCalculate(digitsOperators)
        if(timesDivision.isEmpty()) return ""

        val result = addSubtractClaculate(timesDivision)
        return ""
    }

    private fun addSubtractClaculate(passedList: MutableList<Any>): Flaot
    {
        var result = passedList[0] as Float

        for(i in passedList.indices)
        {
            if(passedList[i] is char && i != passedList.index)
            {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Flaot
                if(operator == '+'){
                    result += nextDigit
                }

                if(operator == '-')
                    result -= nextDigit

            }
        }

        return result
    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any>
    {
        var list = passedList
        while (list.contains('*') || list.contains('/'))
            {
                list = calcTimesDiv(list)
            }
        return  list
    }

    private fun calcTimesDiv(passedlist: MutableList<Any>): MutableList<Any>
    {
        val newList = MutableListOf<Any>()

        var restartIndex = passedlist.size

        for (i in passedlist.indices)
        {
            if(passedlist[i] is char && i != passedlist.lastIndex && i < restartIndex)
            {
                val operator = passedlist[i]
                val preDigit = passedlist[i - 1] as Float
                val nextDigit = passedlist[i + 1] as Float
                when(operator)
                {
                    '*' ->
                    {
                        newList.add(preDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' ->
                    {
                        newList.add(preDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else ->
                    {
                        newList.add(preDigit)
                        newList.add(operator)
                    }
                }
            }
            if (i > restartIndex)
                newList.add(passedlist[i])

        }

        return newList
    }

    private fun digitsOperator(): MutableList<Any> {
        val list = mutableListOf<Any>()
        var currentDigit = ""

        for (character in textView.text) {
            if (character.isDigit() || character == ".")
                currentDigit += character
            else {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }

        if(currentDigit != "")
            list.add(currentDigit.toFloat())

        return list
    }
}