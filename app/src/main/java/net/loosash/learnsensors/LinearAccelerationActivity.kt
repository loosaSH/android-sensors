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

class LinearAccelerationActivity : AppCompatActivity(), SensorEventListener {


    companion object {
        const val TYPE:Int = Sensor.TYPE_LINEAR_ACCELERATION
    }

    private lateinit var sensorManager: SensorManager
    private var linearAccelerationData: ArrayList<String> = ArrayList()
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
                linearAccelerationData.clear()
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Toast.makeText(this@LinearAccelerationActivity, "请输入数值", Toast.LENGTH_SHORT).show()
            }
        }


        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, linearAccelerationData)
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
            val xGravity = event.values[0]
            val yGravity = event.values[1]
            val zGravity = event.values[2]

            if (Math.abs(xGravity - oldX) > referenceValue
                || Math.abs(yGravity - oldY) > referenceValue
                || Math.abs(zGravity - oldZ) > referenceValue
            ) {
                oldX = xGravity
                oldY = yGravity
                oldZ = zGravity

                linearAccelerationData.add(0,"x方向加速度:$xGravity m/s2 \ny方向加速度:$yGravity m/s2   \nz方向加速度:$zGravity m/s2")
                adapter.notifyDataSetChanged()
            }


//            Log.e("xx", "xGravity:$xGravity \nyGravity:$yGravity   \nzGravity:$zGravity")
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
