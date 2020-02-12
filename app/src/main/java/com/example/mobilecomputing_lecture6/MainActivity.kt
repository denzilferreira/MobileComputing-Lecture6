package com.example.mobilecomputing_lecture6

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    lateinit var sensorManager : SensorManager
    lateinit var lightSensor : Sensor
    lateinit var lightSensorListener : SensorEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        lightSensorListener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                println("Accuracy is: $accuracy")
            }

            override fun onSensorChanged(event: SensorEvent?) {
                val lux = event?.values?.get(0)
                lux_debug.text = "$lux lux"
                println("It's now: $lux")
            }
        }

        val allSensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (sensor in allSensors) {
            debug.text = "${debug.text} ${sensor.name} from ${sensor.vendor}\n"
            println("${sensor.name} from ${sensor.vendor}")
        }

        aware.setOnClickListener {
            val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            sensorManager.registerListener(lightSensorListener, lightSensor, lightSensor.maxDelay)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(lightSensorListener, lightSensor)
    }
}
