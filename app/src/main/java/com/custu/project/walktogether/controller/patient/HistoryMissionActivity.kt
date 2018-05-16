package com.custu.project.walktogether.controller.patient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import com.custu.project.project.walktogether.R
import com.custu.project.walktogether.adapter.ListHistoryMissionAdapter
import com.custu.project.walktogether.data.Patient
import com.custu.project.walktogether.data.mission.HistoryMission
import com.custu.project.walktogether.manager.ConnectServer
import com.custu.project.walktogether.model.MissionModel
import com.custu.project.walktogether.network.callback.OnDataSuccessListener
import com.custu.project.walktogether.util.BasicActivity
import com.custu.project.walktogether.util.ConfigService
import com.custu.project.walktogether.util.NetworkUtil
import com.custu.project.walktogether.util.UserManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Retrofit
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.*

class HistoryMissionActivity : AppCompatActivity(), BasicActivity, AdapterView.OnItemClickListener {
    private var listView: ListView? = null
    private var shimmerFrameLayout: ShimmerFrameLayout? = null
    private var noDataTextView: TextView? = null

    private var patient: Patient? = null
    private var historyMissionArrayList: List<HistoryMission>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_history_mission)
        setUI()
        getData()
    }

    override fun initValue() {
        if (historyMissionArrayList!!.isNotEmpty()) {
            listView!!.adapter = ListHistoryMissionAdapter(this@HistoryMissionActivity, historyMissionArrayList)
            listView!!.visibility = View.VISIBLE
            noDataTextView!!.visibility = View.GONE
        } else {
            noDataTextView!!.visibility = View.VISIBLE
        }
        shimmerFrameLayout!!.stopShimmerAnimation()
        shimmerFrameLayout!!.visibility = View.GONE
    }

    override fun setUI() {
        noDataTextView = findViewById(R.id.no_data)
        listView = findViewById(R.id.list)
        listView!!.onItemClickListener = this
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container)
    }

    override fun getData() {
        patient = UserManager.getInstance(this@HistoryMissionActivity).patient
        ConnectServer.getInstance().get(object : OnDataSuccessListener {
            override fun onResponse(`object`: JsonObject?, retrofit: Retrofit) {
                if (`object` != null) {
                    historyMissionArrayList = MissionModel.getInstance().getHistoryMissionArrayList(`object`)
                    val splashInterval = Random().nextInt(1500) + 500
                    Handler().postDelayed({ initValue() }, splashInterval.toLong())
                }

            }

            override fun onBodyError(responseBodyError: ResponseBody) {

            }

            override fun onBodyErrorIsNull() {

            }

            override fun onFailure(t: Throwable) {
                NetworkUtil.isOnline(this@HistoryMissionActivity, listView)

            }
        }, ConfigService.MISSION + ConfigService.HISTORY_MISSION + patient!!.id)
    }

    override fun initProgressDialog() {

    }

    public override fun onResume() {
        super.onResume()
        shimmerFrameLayout!!.startShimmerAnimation()
    }

    public override fun onPause() {
        shimmerFrameLayout!!.stopShimmerAnimation()
        super.onPause()
    }

    override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        val data = Gson().toJson(historyMissionArrayList!![i].patientGame.patientMissionList)
        val intent = Intent(this@HistoryMissionActivity, HistoryMissionDetailActivity::class.java)
        intent.putExtra("mission", data)
        intent.putExtra("route", historyMissionArrayList!![i].patientGame.route)
        startActivity(intent)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
