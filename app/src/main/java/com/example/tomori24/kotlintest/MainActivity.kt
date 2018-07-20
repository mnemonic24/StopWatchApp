package com.example.tomori24.kotlintest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.*

class MainActivity : AppCompatActivity() {

    val handler = Handler()
    var timeValue = 0
    private var mode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timeText: TextView = findViewById(R.id.timeText)
        val mainButton: Button = findViewById(R.id.main)
        val subButton: Button = findViewById(R.id.sub)
        val rapList: ListView = findViewById(R.id.rapList)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        rapList.adapter = adapter

        val runnable = object : Runnable {
            override fun run() {
                timeValue++

                timeToText(timeValue)?.let {
                    timeText.text = it
                }
                handler.postDelayed(this, 1000)
            }
        }

        //mode true: 停止 false: 実行
        mainButton.setOnClickListener {
            //Start
            if (mode) {
                handler.post(runnable)
                mainButton.text = "STOP"
                subButton.text = "RAP"
                mode = false
            //Stop
            } else {
                handler.removeCallbacks(runnable)
                mainButton.text = "START"
                subButton.text = "RESET"
                mode = true
            }
        }

        subButton.setOnClickListener {
            //RESET
            if (mode) {
                timeValue = 0
                timeToText()?.let {
                    timeText.text = it
                }
                adapter.clear()
            //RAP
            } else{
                adapter.insert("RAP" + (adapter.count+1).toString() + ": " + timeText.text.toString(),adapter.count)
            }
        }
    }

    private fun timeToText(time: Int = 0): String? {
        return when {
            time < 0 -> null
            time == 0 -> "00:00:00"
            else -> {
                val h = time / 3600
                val m = time % 3600 / 60
                val s = time % 60
                "%1$02d:%2$02d:%3$02d".format(h, m, s)
            }
        }
    }
}

