package id.myeco.myeco.utils.helper

import android.content.Context
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import timber.log.Timber

class MQTTClient(context: Context?, serverURI: String, clientID: String) {
    private var mqttClient = MqttAndroidClient(context!!, serverURI, clientID)

    private var defaultConnectListener = object: IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Timber.d("Connection success")
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Timber.d("Connection fails: ${exception.toString()}")
        }

    }

    private var defaultClientListener = object: MqttCallback {
        override fun connectionLost(cause: Throwable?) {
            Timber.d("Connection lost ${cause.toString()}")
        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {
            val msg = "Receive message: ${message.toString()} from topic: $topic"
            Timber.d(msg)
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            Timber.d("Delivery complete")
        }

    }

    fun connect(username: String = "", password: String = "", connectListener: IMqttActionListener = defaultConnectListener, clientListener: MqttCallback = defaultClientListener) {
        mqttClient.setCallback(clientListener)
        val options = MqttConnectOptions()
        options.userName = username
        options.password = password.toCharArray()

        try {
            mqttClient.connect(options, null, connectListener)
        } catch (e: MqttException) {
            Timber.e(e.toString())
        }
    }

    fun subscribe(topic: String, qos: Int = 1, listener: IMqttActionListener = defaultConnectListener) {
        try {
            mqttClient.subscribe(topic, qos, null, listener)
        } catch (e: MqttException){
            Timber.e(e.toString())
        }
    }

    fun unsubscribe(topic: String, listener: IMqttActionListener = defaultConnectListener) {
        try {
            mqttClient.unsubscribe(topic, null, listener)
        } catch (e: Exception) {
            Timber.e(e.toString())
        }
    }

    fun publish(topic: String, msg: String, qos: Int = 0, retained: Boolean = false, listener: IMqttActionListener = defaultConnectListener) {
        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            message.qos = qos
            message.isRetained = retained
            mqttClient.publish(topic, message, null, listener)
        }catch (e: MqttException) {
            Timber.e(e)
        }
    }

    fun disconnect(listener: IMqttActionListener = defaultConnectListener) {
        try {
            mqttClient.disconnect(null, listener)
        } catch (e: MqttException){
            Timber.e(e.toString())
        }
    }

    fun isConnect() = mqttClient.isConnected
}