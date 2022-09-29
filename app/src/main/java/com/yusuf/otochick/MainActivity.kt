package com.yusuf.otochick

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import id.myeco.myeco.utils.helper.MQTTClient
import org.eclipse.paho.client.mqttv3.*
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity() {
    private var mqttClient:MQTTClient? = null
    //init topic
    private var topicTemp              = "device/{device_id}/temp/sub"
    private var topicHum               = "device/{device_id}/hum/sub"

    private var topicTemp1             = ""
    private var topicHum1              = ""

    //view id
    private lateinit var tvTemp1:EditText
    private lateinit var tvHum1 :EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //init view id
        initViewId()
        //connect mqtt
        connetMqtt()
    }

    private fun initViewId() {
        tvTemp1 = findViewById(R.id.tvTemp1)
        tvHum1  = findViewById(R.id.tvHum1)
    }

    private fun connetMqtt() {
        if(mqttClient == null) mqttClient = MQTTClient(this, "tcp://devmyeco.my.id:1883", UUID.randomUUID().toString())
        if(mqttClient?.isConnect() == false) {
            mqttClient?.connect("myeco1234", "devmyeco123",
                object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        Timber.e("connected")
                        topicTemp1 = topicTemp.replace("{device_id}", "1")
                        topicHum1  = topicHum.replace("{device_id}", "1")
                        mqttClient?.subscribe(topicTemp1)
                        mqttClient?.subscribe(topicHum1)
                        Timber.tag("DEBUG").d(topicTemp1)
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Timber.e(exception)
                        Toast.makeText(this@MainActivity, "Unable connect to realtime server", Toast.LENGTH_LONG).show()
                    }

                },
                object : MqttCallback {
                    override fun connectionLost(cause: Throwable?) {

                    }

                    @SuppressLint("SetTextI18n")
                    override fun messageArrived(topic: String?, message: MqttMessage?) {
                        val mqttData = message.toString()
                        Timber.tag("DEBUG").d(topic.toString())
                        try {
                            when (topic) {
                                topicTemp1 -> {
                                    tvTemp1.setText("$mqttData °C")
                                }
                                topicHum1 -> {
                                    tvHum1.setText("$mqttData %")
                                }
                            }
                        } catch (e: Exception) {
                            Timber.e(e)
                        }
                    }

                    override fun deliveryComplete(token: IMqttDeliveryToken?) {

                    }

                })
        }
    }
}