package com.alespero.spenprank

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        val SPEN_ACTION = "com.samsung.pen.INSERT"
    }

    var mediaPlayer : MediaPlayer? = null
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
