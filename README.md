# Android sensors

## 基础知识

下面可能会用到关于设备的三个物理轴，这里解释一下：

![](https://img-blog.csdn.net/20180115223132900?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2VpeGluXzM4Mzc5Nzcy/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## 简介

### Motion sensors

运动传感器：沿三个轴测量加速力和旋转力。此类别包括加速度计，重力传感器，陀螺仪和旋转矢量传感器。

- [TYPE_ACCELEROMETER](https://developer.android.google.cn/reference/android/hardware/Sensor.html#TYPE_ACCELEROMETER)：测量在所有三个物理轴（x，y和z）上应用于设备的加速力m / s2，包括重力。
- [TYPE_GRAVITY](https://developer.android.google.cn/reference/android/hardware/Sensor.html#TYPE_GRAVITY)：测量在所有三个物理轴（x，y，z）上应用于设备的重力m / s2。
- [TYPE_LINEAR_ACCELERATION](https://developer.android.google.cn/reference/android/hardware/Sensor.html#TYPE_LINEAR_ACCELERATION)：测量在所有三个物理轴（x，y和z）上应用于设备的加速力m / s2，不包括重力。



### Environmental sensors

环境传感器：测量各种环境参数，例如环境空气温度和压力，照明和湿度。 此类别包括气压计，光度计和温度计。

- [TYPE_AMBIENT_TEMPERATURE](https://developer.android.google.cn/reference/android/hardware/Sensor.html#TYPE_AMBIENT_TEMPERATURE)：以摄氏度（°C）为单位测量环境室温。（红米5无法获取）
- [TYPE_LIGHT](https://developer.android.google.cn/reference/android/hardware/Sensor.html#TYPE_LIGHT)：以lx为单位测量环境光水平（照度）。
- [TYPE_PRESSURE](https://developer.android.google.cn/reference/android/hardware/Sensor.html#TYPE_PRESSURE)：以hPa或mbar测量环境空气压力。(红米5无法获取)
- [TYPE_PROXIMITY](https://developer.android.google.cn/reference/android/hardware/Sensor.html#TYPE_PROXIMITY)：测量相对于设备视图屏幕的对象的接近度（cm）。该传感器通常用于确定手机是否被握在人的耳朵上。
- [TYPE_RELATIVE_HUMIDITY](https://developer.android.google.cn/reference/android/hardware/Sensor.html#TYPE_RELATIVE_HUMIDITY)：以百分比（％）测量相对环境湿度。
- [TYPE_TEMPERATURE](https://developer.android.google.cn/reference/android/hardware/Sensor.html#TYPE_TEMPERATURE)：以摄氏度（°C）为单位测量设备的温度。此传感器实现因设备而异，并且此传感器已替换为API级别14中的TYPE_AMBIENT_TEMPERATURE传感器（红米5无法获取）



### Position sensors

位置传感器：这些传感器测量设备的物理位置。 此类别包括方向传感器和磁力计。

- [TYPE_GYROSCOPE](https://developer.android.google.cn/reference/android/hardware/Sensor.html#TYPE_GYROSCOPE)：测量设备在三个物理轴（x，y和z）中的每一个周围以rad / s为单位的旋转速率。
- [TYPE_MAGNETIC_FIELD](https://developer.android.google.cn/reference/android/hardware/Sensor.html#TYPE_MAGNETIC_FIELD)：以μT为单位测量所有三个物理轴（x，y，z）的环境地磁场。
- [TYPE_ROTATION_VECTOR](https://developer.android.google.cn/reference/android/hardware/Sensor.html#TYPE_ROTATION_VECTOR)：通过提供设备旋转矢量的三个元素来测量设备的方向。
- [TYPE_ORIENTATION](https://developer.android.google.cn/reference/android/hardware/Sensor.html#TYPE_ORIENTATION)：测量设备围绕所有三个物理轴（x，y，z）旋转的度数。 从API级别3开始，您可以通过使用重力传感器和地磁场传感器以及getRotationMatrix（）方法获得设备的倾斜矩阵和旋转矩阵。（**同时使用位置传感器**）

## 实现监听

### Sensor.TYPE_ACCELEROMETER

一、获取 SensorManager 并注册监听（以监听加速度为例，其余都很相似）

```kotlin
private lateinit var sensorManager: SensorManager
override fun onCreate(savedInstanceState: Bundle?) {
	super.onCreate(savedInstanceState)
	sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
	)
}
```

二、实现 SensorEventListener 并重写方法

```kotlin
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.e("xx", "onAccuracyChanged")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val xAccelerate = event.values?.get(0)
            val yAccelerate = event.values?.get(1)
            val zAccelerate = event.values?.get(2)
            Log.e("xx", "xAccelerate:$xAccelerate\nyAccelerate:$yAccelerate\nzAccelerate:$zAccelerate")
        }
    }
```



需要代码看demo吧！

[https://github.com/loosaSH/android-sensors](https://github.com/loosaSH/android-sensors)

