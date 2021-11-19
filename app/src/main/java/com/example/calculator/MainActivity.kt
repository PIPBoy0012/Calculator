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
        return ""
    }

    /*private fun digitsOperator(): MutableList<Any> {
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
    }*/
}