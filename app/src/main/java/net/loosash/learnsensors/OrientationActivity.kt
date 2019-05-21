package net.loosash.learnsensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_acceleration.*
import java.lang.Exception


class OrientationActivity : AppCompatActivity(), SensorEventListener {

    companion object {
        const val TYPE:Int = Sensor.TYPE_ORIENTATION
    }

    private lateinit var sensorManager: SensorManager
    private var orientationData: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>

    private var oldX: Float = 0f
    private var oldY: Float = 0f
    private var oldZ: Float = 0f

    private var referenceValue: Float = 0.5f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceleration)

        btn_confirm.setOnClickListener {
            try {
                referenceValue = et_reference_value.text.toString().toFloat()
                orientationData.clear()
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Toast.makeText(this@OrientationActivity, "请输入数值", Toast.LENGTH_SHORT).show()
            }
        }


        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, orientationData)
        lv_acceleration.adapter = adapter

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(TYPE),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.e("xx", "onAccuracyChanged")
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor?.type == TYPE
            && event.values != null
            && event.values.size >= 3
        ) {
            val xValue = event.values[0]
            val yValue = event.values[1]
            val zValue = event.values[2]

            if (Math.abs(xValue - oldX) > referenceValue
                || Math.abs(yValue - oldY) > referenceValue
                || Math.abs(zValue - oldZ) > referenceValue
            ) {
                oldX = xValue
                oldY = yValue
                oldZ = zValue

                orientationData.add(0,"绕x轴转过角度:$xValue °\n绕y轴转过角度:$yValue °  \n绕z轴转过角度:$zValue °")
                adapter.notifyDataSetChanged()
            }

        }
    }


    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this, sensorManager.getDefaultSensor(TYPE),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }


}
