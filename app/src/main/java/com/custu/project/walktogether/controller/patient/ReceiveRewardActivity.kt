package com.custu.project.walktogether.controller.patient

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout

import com.airbnb.lottie.LottieAnimationView
import com.custu.project.project.walktogether.R
import com.custu.project.walktogether.data.Patient
import com.custu.project.walktogether.data.collection.Reward
import com.custu.project.walktogether.manager.ConnectServer
import com.custu.project.walktogether.model.CollectionModel
import com.custu.project.walktogether.model.PatientModel
import com.custu.project.walktogether.network.callback.OnDataSuccessListener
import com.custu.project.walktogether.util.BasicActivity
import com.custu.project.walktogether.util.ConfigService
import com.custu.project.walktogether.util.PicassoUtil
import com.custu.project.walktogether.util.UserManager
import com.google.gson.JsonObject

import okhttp3.ResponseBody
import retrofit2.Retrofit

class ReceiveRewardActivity : AppCompatActivity(), BasicActivity {
    private var giftLottieAnimationView: LottieAnimationView? = null
    private var reward: Reward? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_reward)
        supportActionBar!!.hide()
        getData()
    }

    override fun initValue() {
        val splashInterval = 1000
        Handler().postDelayed({
            giftLottieAnimationView!!.visibility = View.GONE
            showDialog(reward!!)
        }, splashInterval.toLong())
    }

    override fun setUI() {
        giftLottieAnimationView = findViewById(R.id.gift)
        initValue()
    }

    override fun getData() {
        ConnectServer.getInstance().get(object : OnDataSuccessListener {
            override fun onResponse(jsonObject: JsonObject, retrofit: Retrofit) {
                reward = CollectionModel.getInstance().getReward(jsonObject)
                setUI()
            }

            override fun onBodyError(responseBodyError: ResponseBody) {

            }

            override fun onBodyErrorIsNull() {

            }

            override fun onFailure(t: Throwable) {

            }
        }, ConfigService.RANDOM_REWARD + UserManager.getInstance(this@ReceiveRewardActivity).patient.id)
    }

    override fun initProgressDialog() {

    }

    private fun showDialog(reward: Reward) {
        val dialog = Dialog(this@ReceiveRewardActivity)
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.setContentView(R.layout.dialog_reward)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        val imageView = dialog.findViewById<ImageView>(R.id.image)
        PicassoUtil.getInstance().setImage(this@ReceiveRewardActivity, reward.image, imageView)

        val done = dialog.findViewById<LinearLayout>(R.id.submit)
        done.setOnClickListener { _ ->
            dialog.dismiss().also {
                ConnectServer.getInstance().get(object : OnDataSuccessListener {
                    override fun onResponse(jsonObject: JsonObject?, retrofit: Retrofit?) {
                        val patient: Patient = PatientModel.getInstance().getPatient(jsonObject)
                        UserManager.getInstance(this@ReceiveRewardActivity).storePatient(patient)
                        val intent = Intent(this@ReceiveRewardActivity, ReHomePatientActivity::class.java)
                        intent.putExtra("page", "collection")
                        startActivity(intent)
                    }

                    override fun onBodyError(responseBodyError: ResponseBody?) {
                    }

                    override fun onBodyErrorIsNull() {
                    }

                    override fun onFailure(t: Throwable?) {
                    }
                }, ConfigService.PATIENT + UserManager.getInstance(this).patient.id)
            }
        }
        dialog.show()
    }

    override fun onBackPressed() {
    }

}
