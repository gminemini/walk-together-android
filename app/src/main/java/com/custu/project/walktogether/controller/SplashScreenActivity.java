package com.custu.project.walktogether.controller

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import com.crashlytics.android.Crashlytics
import com.custu.project.project.walktogether.R
import com.github.ybq.android.spinkit.style.ThreeBounce
import io.fabric.sdk.android.Fabric
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfFloat
import org.opencv.core.MatOfInt
import org.opencv.imgproc.Imgproc

class SplashScreenActivity : Activity() {
    internal lateinit var handler: Handler
    internal var runnable: Runnable? = null
    internal var delay_time: Long = 0
    internal var time = 2000L

    private val baseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                LoaderCallbackInterface.SUCCESS -> {

                    compare()
                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Fabric.with(this, Crashlytics())
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)
        handler = Handler()
        val progressBar = findViewById<ProgressBar>(R.id.progress)
        val threeBounce = ThreeBounce()
        progressBar.indeterminateDrawable = threeBounce

        if (OpenCVLoader.initDebug()) {
            Log.d("onManagerConnected: ", "onCreate: ")
        } else {
            Log.d("onManagerConnected: ", "onManagerConnected:")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, baseLoaderCallback)
        }

        /*int splashInterval = 2000;
        new Handler().postDelayed(() -> {
            Intent intent;
            if (UserManager.getInstance(SplashScreenActivity.this).getCaretaker() != null) {
                intent = new Intent(SplashScreenActivity.this, ReHomeCaretakerActivity.class);
                startActivity(intent);
            } else if (UserManager.getInstance(SplashScreenActivity.this).getPatient() != null) {
                Patient patient = UserManager.getInstance(SplashScreenActivity.this).getPatient();
                if (patient.getUserName() != null) {
                    intent = new Intent(SplashScreenActivity.this, ReHomePatientActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(SplashScreenActivity.this, RegisterPatientActivity.class);
                    intent.putExtra("idPatient", patient.getId());
                    intent.putExtra("isContinue", true);
                    startActivity(intent);
                }
            } else {
                intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            finish();
        }, splashInterval);*/
    }

    public override fun onResume() {
        super.onResume()
        delay_time = time
        handler.postDelayed(runnable, delay_time)
        time = System.currentTimeMillis()
    }

    public override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
        time = delay_time - (System.currentTimeMillis() - time)
    }

    override fun onBackPressed() {}

    private fun compare() {
        val b1 = BitmapFactory.decodeResource(resources, R.drawable.template)
        val image = intArrayOf(R.drawable.test1, R.drawable.test2, R.drawable.test3, R.drawable.test4, R.drawable.test5, R.drawable.test6, R.drawable.test7, R.drawable.test8, R.drawable.test9, R.drawable.test10, R.drawable.test11, R.drawable.test12, R.drawable.test13, R.drawable.test14, R.drawable.test15, R.drawable.test16, R.drawable.test17, R.drawable.test18, R.drawable.test19, R.drawable.test20, R.drawable.test21, R.drawable.test22, R.drawable.test23, R.drawable.test24, R.drawable.test25, R.drawable.test26, R.drawable.test27, R.drawable.test28, R.drawable.test29, R.drawable.test30, R.drawable.test31, R.drawable.test32, R.drawable.test33, R.drawable.test34, R.drawable.test35, R.drawable.test36, R.drawable.test37, R.drawable.test38, R.drawable.test39, R.drawable.test40, R.drawable.test40, R.drawable.test42, R.drawable.test43, R.drawable.test44, R.drawable.test45, R.drawable.test46, R.drawable.test47, R.drawable.test48, R.drawable.test49, R.drawable.test50)
        var i = 1
        var sum = 0.0
        var max = 0.0
        var maxI = 0.0
        var min = 1000.0
        var minI = 0.0
        val list = ArrayList<Double>()
        for (anImage in image) {
            val b2 = BitmapFactory.decodeResource(resources, anImage)
            val result = compare_image(b1, b2)
            list.add(result!!)
            Log.d("compare_image: ", "$result : $i")
            i++
        }

        Log.d("compare_image: ", "avr " + list.average())
        Log.d("compare_image: ", "min ${list.min()} ${list.indices.minBy { list[it] } ?: -1}")
        Log.d("compare_image: ", "max ${list.max()} ${list.indices.maxBy { list[it] } ?: -1}")

    }


    fun compare_image(img_1: Bitmap, img_2: Bitmap): Double? {
        var img_1 = img_1
        var img_2 = img_2
        val mat_1 = Mat()
        val mat_2 = Mat()


        img_1 = Bitmap.createScaledBitmap(img_1, 947, 710, true)
        img_2 = Bitmap.createScaledBitmap(img_2, 947, 710, true)

        val bitmap1 = img_1.copy(Bitmap.Config.ARGB_8888, true)
        Utils.bitmapToMat(bitmap1, mat_1)

        val bitmap2 = img_2.copy(Bitmap.Config.ARGB_8888, true)
        Utils.bitmapToMat(bitmap2, mat_2)

        val hist_1 = Mat()
        val hist_2 = Mat()

        val ranges = MatOfFloat(0f, 256f)
        val histSize = MatOfInt(25)

        Imgproc.calcHist(listOf(mat_1), MatOfInt(0),
                Mat(), hist_1, histSize, ranges)
        Imgproc.calcHist(listOf(mat_2), MatOfInt(0),
                Mat(), hist_2, histSize, ranges)

        val res = Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL)
        return res * 100
    }
}

