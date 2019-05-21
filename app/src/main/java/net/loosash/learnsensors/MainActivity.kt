package net.loosash.learnsensors

import android.content.Intent
import android.hardware.Sensor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private var mSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_acceleration.setOnClickListener {
            startActivity(Intent(this, AccelerationActivity::class.java))
        }


        btn_gravity.setOnClickListener {
            startActivity(Intent(this, GravityActivity::class.java))
        }

        btn_linear_acceleration.setOnClickListener {
            startActivity(Intent(this, LinearAccelerationActivity::class.java))
        }

        btn_gyroscope.setOnClickListener {
            startActivity(Intent(this, GyroscopeActivity::class.java))
        }

        btn_orientation.setOnClickListener {
            startActivity(Intent(this, OrientationActivity::class.java))
        }

        btn_magnetic_field.setOnClickListener {
            startActivity(Intent(this, MagneticFieldActivity::class.java))
        }

        btn_temperature.setOnClickListener {
            startActivity(Intent(this, TemperatureActivity::class.java))
        }

        btn_light.setOnClickListener {
            startActivity(Intent(this, LightActivity::class.java))
        }

        btn_pressure.setOnClickListener {
            startActivity(Intent(this, PressureActivity::class.java))
        }

        btn_proximity.setOnClickListener {
            startActivity(Intent(this, ProximityActivity::class.java))
        }

    }


}
