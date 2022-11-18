package com.yusuf.otochick

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
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
    private var topicTemp2             = ""
    private var topicHum2              = ""
    private var topicTemp3             = ""
    private var topicHum3              = ""
    private var topicTemp4             = ""
    private var topicHum4              = ""
    private var topicTemp5             = ""
    private var topicHum5              = ""
    private var topicTemp6             = ""
    private var topicHum6              = ""
    private var topicTemp7             = ""
    private var topicHum7              = ""

    //view id
    private lateinit var tvTemp1:TextView
    private lateinit var tvHum1 :TextView
    private lateinit var tvTemp2:TextView
    private lateinit var tvHum2 :TextView
    private lateinit var tvTemp3:TextView
    private lateinit var tvHum3 :TextView
    private lateinit var tvTemp4:TextView
    private lateinit var tvHum4 :TextView
    private lateinit var tvTemp5:TextView
    private lateinit var tvHum5 :TextView
    private lateinit var tvTemp6:TextView
    private lateinit var tvHum6 :TextView
    private lateinit var tvTemp7:TextView
    private lateinit var tvHum7 :TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //init view id
        initViewId()
        //init topic
        initTopic()
        //connect mqtt
        connetMqtt()
    }

    private fun initViewId() {
        tvTemp1 = findViewById(R.id.tvTemp0)
        tvHum1  = findViewById(R.id.tvHum0)
        tvTemp2 = findViewById(R.id.tvTemp1)
        tvHum2  = findViewById(R.id.tvHum1)
        tvTemp3 = findViewById(R.id.tvTemp2)
        tvHum3  = findViewById(R.id.tvHum2)
        tvTemp4 = findViewById(R.id.tvTemp3)
        tvHum4  = findViewById(R.id.tvHum3)
        tvTemp5 = findViewById(R.id.tvTemp4)
        tvHum5  = findViewById(R.id.tvHum4)
        tvTemp6 = findViewById(R.id.tvTemp5)
        tvHum6  = findViewById(R.id.tvHum5)
        tvTemp7 = findViewById(R.id.tvTemp6)
        tvHum7  = findViewById(R.id.tvHum6)
    }

    private fun initTopic() {
        topicTemp1 = topicTemp.replace("{device_id}", "0")
        topicHum1  = topicHum.replace("{device_id}", "0")
        topicTemp2 = topicTemp.replace("{device_id}", "1")
        topicHum2  = topicHum.replace("{device_id}", "1")
        topicTemp3 = topicTemp.replace("{device_id}", "2")
        topicHum3  = topicHum.replace("{device_id}", "2")
        topicTemp4 = topicTemp.replace("{device_id}", "3")
        topicHum4  = topicHum.replace("{device_id}", "3")
        topicTemp5 = topicTemp.replace("{device_id}", "4")
        topicHum5  = topicHum.replace("{device_id}", "4")
        topicTemp6 = topicTemp.replace("{device_id}", "5")
        topicHum6  = topicHum.replace("{device_id}", "5")
        topicTemp7 = topicTemp.replace("{device_id}", "6")
        topicHum7  = topicHum.replace("{device_id}", "6")
    }

    private fun connetMqtt() {
        if(mqttClient == null) mqttClient = MQTTClient(this, "tcp://devmyeco.my.id:1884", UUID.randomUUID().toString())
        if(mqttClient?.isConnect() == false) {
            mqttClient?.connect("client", "client",
                object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        Timber.e("connected")

                        mqttClient?.subscribe(topicTemp1)
                        mqttClient?.subscribe(topicHum1)
                        mqttClient?.subscribe(topicTemp2)
                        mqttClient?.subscribe(topicHum2)
                        mqttClient?.subscribe(topicTemp3)
                        mqttClient?.subscribe(topicHum3)
                        mqttClient?.subscribe(topicTemp4)
                        mqttClient?.subscribe(topicHum4)
                        mqttClient?.subscribe(topicTemp5)
                        mqttClient?.subscribe(topicHum5)
                        mqttClient?.subscribe(topicTemp6)
                        mqttClient?.subscribe(topicHum6)
                        mqttClient?.subscribe(topicTemp7)
                        mqttClient?.subscribe(topicHum7)
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
                                    tvTemp1.setText("Temp : $mqttData °C")
                                }
                                topicHum1 -> {
                                    tvHum1.setText("Hum : $mqttData %")
                                }
                                topicTemp2 -> {
                                    tvTemp2.setText("Temp : $mqttData °C")
                                }
                                topicHum2 -> {
                                    tvHum2.setText("Hum : $mqttData %")
                                }
                                topicTemp3 -> {
                                    tvTemp3.setText("Temp : $mqttData °C")
                                }
                                topicHum3 -> {
                                    tvHum3.setText("Hum : $mqttData %")
                                }
                                topicTemp4 -> {
                                    tvTemp4.setText("Temp : $mqttData °C")
                                }
                                topicHum4 -> {
                                    tvHum4.setText("Hum : $mqttData %")
                                }
                                topicTemp5 -> {
                                    tvTemp5.setText("Temp : $mqttData °C")
                                }
                                topicHum5 -> {
                                    tvHum5.setText("Hum : $mqttData %")
                                }
                                topicTemp6 -> {
                                    tvTemp6.setText("Temp : $mqttData °C")
                                }
                                topicHum6 -> {
                                    tvHum6.setText("Hum : $mqttData %")
                                }
                                topicTemp7 -> {
                                    tvTemp7.setText("Temp : $mqttData °C")
                                }
                                topicHum7 -> {
                                    tvHum7.setText("Hum : $mqttData %")
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