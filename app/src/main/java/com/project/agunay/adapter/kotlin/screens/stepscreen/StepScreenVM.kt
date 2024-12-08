package com.project.agunay.adapter.kotlin.screens.stepscreen

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.floor

class StepScreenVM: ViewModel() {
    private val _steps = MutableLiveData(0)
    val steps: LiveData<Int> = _steps

    private val _points = MutableLiveData(0)
    val points: LiveData<Int> = _points

    private var initialSteps = -1

    fun initSensor(context: Context) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        val sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val totalSteps = event.values[0].toInt()
                if(initialSteps == -1) {
                    initialSteps = totalSteps
                }
                val sessionSteps = totalSteps - initialSteps
                _steps.value = sessionSteps
                _points.value = floor(sessionSteps.toDouble() / 50).toInt()
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
        sensorManager.registerListener(sensorEventListener, stepSensor, SensorManager.SENSOR_DELAY_UI)
        Log.d("steps", "Contador de pasos inicializado")
    }
}