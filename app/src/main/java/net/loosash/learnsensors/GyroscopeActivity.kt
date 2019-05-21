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


class GyroscopeActivity : AppCompatActivity(), SensorEventListener {

    companion object {
        const val TYPE:Int = Sensor.TYPE_GYROSCOPE
    }

    private lateinit var sensorManager: SensorManager
    private var gyroscopeData: ArrayList<String> = ArrayList()
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
                gyroscopeData.clear()
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Toast.makeText(this@GyroscopeActivity, "请输入数值", Toast.LENGTH_SHORT).show()
            }
        }


        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, gyroscopeData)
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

                gyroscopeData.add(0,"绕x轴角速度:$xValue rad/s\n绕y轴角速度:$yValue rad/s  \n绕z轴角速度:$zValue rad/s")
                adapter.notifyDataSetChanged()
            }


//            Log.e("xx", "xAccelerate:$xAccelerate  \nyAccelerate:$yAccelerate   \nzAccelerate:$zAccelerate")
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
