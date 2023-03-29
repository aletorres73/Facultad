package com.example.firstapp

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var txtLabel   : TextView
    lateinit var btnShow    : Button
    lateinit var btnClear   : Button
    lateinit var btnYellow  : Button
    lateinit var btnBlue    : Button

    var labelText : String = "Daleeee Booooocaaaaaa!!"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtLabel    = findViewById(R.id.txtLabel)
        btnShow     = findViewById(R.id.btnShow)
        btnClear    = findViewById(R.id.btnClear)
        btnYellow      = findViewById(R.id.btnYellow)
        btnBlue     = findViewById(R.id.btnBlue)

        txtLabel.text = ""

        btnShow.setOnClickListener {
            if(txtLabel.text == "")
                txtLabel.text= labelText
        }
        btnClear.setOnClickListener {
            if (txtLabel.text == labelText)
                txtLabel.text = ""
        }

        btnBlue.setOnClickListener {
            if(txtLabel.text == labelText)
                txtLabel.setTextColor(Color.BLUE)
        }
        btnYellow.setOnClickListener {
            if(txtLabel.text == labelText)
                txtLabel.setTextColor(Color.YELLOW)
        }
    }
}