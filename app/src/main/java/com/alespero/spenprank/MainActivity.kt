package com.alespero.spenprank

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.widget.Switch
import com.alespero.expandablecardview.ExpandableCardView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        val SPEN_ACTION = "com.samsung.pen.INSERT"
    }

    var mediaPlayer : MediaPlayer? = null
    lateinit var enableSound : Switch
    val sPenIntentFilter = IntentFilter(SPEN_ACTION)
    var firstReceive = true

    val penBroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            mediaPlayer?.let {
                if(!firstReceive) it.start()
                else firstReceive = false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ExpandableCardView>(R.id.settings).findViewById<CardView>(R.id.card).radius = 30f

        enableSound = findViewById(R.id.enableSound)
        mediaPlayer = MediaPlayer.create(this, R.raw.ack_ack)

        enableSound.setOnCheckedChangeListener{ buttonView , isChecked ->
            if (isChecked)
                registerReceiver(penBroadcastReceiver, sPenIntentFilter)
            else {
                unregisterReceiver(penBroadcastReceiver)
                firstReceive = true
            }
        }

    }
}
