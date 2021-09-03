package io.github.asvid.services.server

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log

const val TAG = "Server"

class MyService : Service() {

    private val messenger = Messenger(object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d(TAG, "received message: ${msg.data.getString("message")}")
        }
    })

    override fun onBind(intent: Intent): IBinder {
        return messenger.binder
    }
}