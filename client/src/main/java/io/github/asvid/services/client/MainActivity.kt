package io.github.asvid.services.client

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log

const val TAG = "Client"

class MainActivity : AppCompatActivity() {
    private var messenger: Messenger? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindToService()
    }

    private fun bindToService() {
        val intent = Intent("example_action")
        intent.`package` = "io.github.asvid.services.server"
        val connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.d(TAG, "service connected: $name")
                messenger = Messenger(service)
                sendWelcomeMessage()
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.d(TAG, "service disconnected: $name")
                messenger = null
            }

        }
        val bindingResult = bindService(intent, connection, Context.BIND_AUTO_CREATE)
        Log.d(TAG, "binding result $bindingResult")
    }

    private fun sendWelcomeMessage() {
        val message = Message().also {
            it.data.putString("message", "hello my dear service from another app!")
        }
        messenger?.send(message)
    }
}