package com.custu.project.walktogether.controller.patient

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.TextView

import com.custu.project.project.walktogether.R
import com.custu.project.walktogether.adapter.HomePatientPagerAdapter
import com.custu.project.walktogether.data.Caretaker
import com.custu.project.walktogether.util.BasicActivity
import com.custu.project.walktogether.util.DataFormat
import com.custu.project.walktogether.util.UserManager

import me.leolin.shortcutbadger.ShortcutBadger
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class ReHomePatientActivity : AppCompatActivity(), BasicActivity, View.OnClickListener {

    private var tabLayout: TabLayout? = null
    private var editProfileRelativeLayout: RelativeLayout? = null
    private var addProfileRelativeLayout: RelativeLayout? = null
    private var historyMissionRelativeLayout: RelativeLayout? = null
    private var titleTextView: TextView? = null
    private var page: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_re_homepatient)
        initValue()
        setUI()
        setListener()
    }

    override fun onClick(view: View) {
        val id = view.id
        val intent: Intent
        when (id) {
            R.id.add -> {
                intent = Intent(this@ReHomePatientActivity, AddTabPatientActivity::class.java)
                startActivity(intent)
            }
            R.id.edit_profile -> {
                intent = Intent(this@ReHomePatientActivity, EditPatientProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.history_mission -> {
                intent = Intent(this@ReHomePatientActivity, HistoryMissionActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setListener() {
        titleTextView!!.setOnClickListener(this)
        addProfileRelativeLayout!!.setOnClickListener(this)
        editProfileRelativeLayout!!.setOnClickListener(this)
        historyMissionRelativeLayout!!.setOnClickListener(this)
    }

    override fun initValue() {
        page = if (intent.getStringExtra("page") == null) "" else intent.getStringExtra("page")
    }

    override fun setUI() {
        ShortcutBadger.removeCount(this)
        titleTextView = findViewById(R.id.title)
        tabLayout = findViewById(R.id.tabs)
        addProfileRelativeLayout = findViewById(R.id.add)
        editProfileRelativeLayout = findViewById(R.id.edit_profile)
        historyMissionRelativeLayout = findViewById(R.id.history_mission)
        tabLayout!!.setTabTextColors(Color.parseColor("#8E8E93"), Color.parseColor("#389A1E"))
        val adapter = HomePatientPagerAdapter(supportFragmentManager, UserManager.getInstance(this@ReHomePatientActivity).patient)
        val viewPager = findViewById<ViewPager>(R.id.container)
        viewPager.adapter = adapter


        var tab: TabLayout.Tab? = tabLayout!!.getTabAt(0)
        var tabIconColor = ContextCompat.getColor(this@ReHomePatientActivity, R.color.colorBackground)
        tab!!.icon!!.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)

        tab = tabLayout!!.getTabAt(1)
        tabIconColor = ContextCompat.getColor(this@ReHomePatientActivity, R.color.colorMiddleGray)
        tab!!.icon!!.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
        tab = tabLayout!!.getTabAt(2)
        tabIconColor = ContextCompat.getColor(this@ReHomePatientActivity, R.color.colorMiddleGray)
        tab!!.icon!!.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
        tab = tabLayout!!.getTabAt(3)
        tabIconColor = ContextCompat.getColor(this@ReHomePatientActivity, R.color.colorMiddleGray)
        tab!!.icon!!.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
        tab = tabLayout!!.getTabAt(4)
        tabIconColor = ContextCompat.getColor(this@ReHomePatientActivity, R.color.colorMiddleGray)
        tab!!.icon!!.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)


        tabLayout!!.setOnTabSelectedListener(
                object : TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    override fun onTabSelected(tab: TabLayout.Tab) {
                        super.onTabSelected(tab)
                        val tabIconColor = ContextCompat.getColor(this@ReHomePatientActivity, R.color.colorBackground)
                        tab.icon!!.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        super.onTabUnselected(tab)
                        val tabIconColor = ContextCompat.getColor(this@ReHomePatientActivity, R.color.colorMiddleGray)
                        tab!!.icon!!.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                        super.onTabReselected(tab)
                    }
                }
        )

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        viewPager.currentItem = 0
        viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == 0) {
                    titleTextView!!.text = "โปรไฟล์"
                    editProfileRelativeLayout!!.visibility = View.VISIBLE
                    addProfileRelativeLayout!!.visibility = View.GONE
                    historyMissionRelativeLayout!!.visibility = View.GONE
                } else if (position == 1) {
                    titleTextView!!.text = "ผู้ดูแล"
                    addProfileRelativeLayout!!.visibility = View.VISIBLE
                    editProfileRelativeLayout!!.visibility = View.GONE
                    historyMissionRelativeLayout!!.visibility = View.GONE
                } else if (position == 2) {
                    titleTextView!!.text = "ภารกิจ"
                    historyMissionRelativeLayout!!.visibility = View.VISIBLE
                    editProfileRelativeLayout!!.visibility = View.GONE
                    addProfileRelativeLayout!!.visibility = View.GONE
                } else if (position == 3) {
                    titleTextView!!.text = "แบบทดสอบ"
                    editProfileRelativeLayout!!.visibility = View.GONE
                    addProfileRelativeLayout!!.visibility = View.GONE
                    historyMissionRelativeLayout!!.visibility = View.GONE
                } else {
                    titleTextView!!.text = "สะสม"
                    editProfileRelativeLayout!!.visibility = View.GONE
                    addProfileRelativeLayout!!.visibility = View.GONE
                    historyMissionRelativeLayout!!.visibility = View.GONE
                }
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        if (page!!.equals("list", ignoreCase = true))
            viewPager.currentItem = 1
        if (page!!.equals("historyEvaluation", ignoreCase = true))
            viewPager.currentItem = 3
        if (page!!.equals("collection", ignoreCase = true))
            viewPager.currentItem = 4
    }

    override fun getData() {

    }

    override fun initProgressDialog() {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = this.currentFocus
        if (imm != null) {
            if (view != null) {
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

    fun openProfileDetail(caretaker: Caretaker) {
        val bundle = Bundle()
        bundle.putString("caretaker", DataFormat.getInstance().gsonParser.toJson(caretaker))
        val fragment = ProfileCaretakerDetailFragment()
        fragment.arguments = bundle
        openFragment(fragment)
    }

    private fun openFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_fragment, R.anim.exit_fragment)
        transaction.replace(R.id.profile_content, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base))
    }
}
