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
import com.project.agunay.adapter.kotlin.configuration.CurrentUser
import com.project.agunay.domain.User

class StepScreenVM: ViewModel() {
    private val _steps = MutableLiveData(0)
    val steps: LiveData<Int> = _steps

    private val _points = MutableLiveData(0)
    val points: LiveData<Int> = _points

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private var initialSteps = -1
    private var stepsForPoint = 50

    private var progressToPoint = stepsForPoint

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
                if (sessionSteps >= progressToPoint) {
                    _points.value = _points.value?.inc()
                    progressToPoint += stepsForPoint
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
        sensorManager.registerListener(sensorEventListener, stepSensor, SensorManager.SENSOR_DELAY_UI)
        Log.d("steps", "Contador de pasos inicializado")
    }

    fun setCurrentUser(currentUser: CurrentUser) {
        _currentUser.postValue(currentUser.getUser())
        _points.value = currentUser.getUser()?.points
    }

    fun updateUserPoints() {
        _currentUser.value?.points = _points.value!!
    }
}