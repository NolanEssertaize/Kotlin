package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : Activity() {
    private lateinit var display: TextView
    private var premierNombre = ""
    private var operateur = ""
    private var nouveauNombre = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.displayText)

        setupNumberButton(R.id.btn0, "0")
        setupNumberButton(R.id.btn1, "1")
        setupNumberButton(R.id.btn2, "2")
        setupNumberButton(R.id.btn3, "3")
        setupNumberButton(R.id.btn4, "4")
        setupNumberButton(R.id.btn5, "5")
        setupNumberButton(R.id.btn6, "6")
        setupNumberButton(R.id.btn7, "7")
        setupNumberButton(R.id.btn8, "8")
        setupNumberButton(R.id.btn9, "9")
        setupNumberButton(R.id.btnDot, ".")

        setupOperatorButton(R.id.btnPlus, "+")
        setupOperatorButton(R.id.btnMinus, "-")
        setupOperatorButton(R.id.btnMultiply, "×")
        setupOperatorButton(R.id.btnDivide, "÷")

        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            calculerResultat()
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            reinitialiser()
        }
    }

    private fun setupNumberButton(buttonId: Int, number: String) {
        findViewById<Button>(buttonId).setOnClickListener {
            if (nouveauNombre) {
                display.text = if (number == ".") "0." else number
                nouveauNombre = false
            } else {
                if (number == "." && display.text.contains(".")) {
                    return@setOnClickListener
                }
                display.text = display.text.toString() + number
            }
        }
    }

    private fun setupOperatorButton(buttonId: Int, op: String) {
        findViewById<Button>(buttonId).setOnClickListener {
            premierNombre = display.text.toString()
            operateur = op
            nouveauNombre = true
        }
    }

    private fun calculerResultat() {
        if (premierNombre.isEmpty() || operateur.isEmpty()) return

        val nombre1 = premierNombre.toDoubleOrNull() ?: return
        val nombre2 = display.text.toString().toDoubleOrNull() ?: return

        val resultat = when (operateur) {
            "+" -> nombre1 + nombre2
            "-" -> nombre1 - nombre2
            "×" -> nombre1 * nombre2
            "÷" -> if (nombre2 != 0.0) nombre1 / nombre2 else null
            else -> null
        }

        if (resultat == null) {
            display.text = "Error"
        } else {
            display.text = if (resultat % 1 == 0.0) {
                resultat.toInt().toString()
            } else {
                resultat.toString()
            }
        }
        nouveauNombre = true
    }

    private fun reinitialiser() {
        display.text = "0"
        premierNombre = ""
        operateur = ""
        nouveauNombre = true
    }
}