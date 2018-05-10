package com.custu.project.walktogether.controller.tmse

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView

import com.custu.project.project.walktogether.R
import com.custu.project.walktogether.adapter.HintAdapter
import com.custu.project.walktogether.data.Evaluation.Question
import com.custu.project.walktogether.model.EvaluationModel
import com.custu.project.walktogether.util.BasicActivity
import com.custu.project.walktogether.util.ConfigService
import com.custu.project.walktogether.util.DialogUtil
import com.custu.project.walktogether.util.StoreAnswerTmse

import java.util.ArrayList

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class QuestionEightActivity : AppCompatActivity(), BasicActivity, View.OnClickListener {
    private var answerSpinnerOne: Spinner? = null
    private var answerSpinnerTwo: Spinner? = null
    private var answerSpinnerThree: Spinner? = null
    private var answerSpinnerFour: Spinner? = null
    private var answerSpinnerFive: Spinner? = null
    private val answerArray = ArrayList<String>()
    private var nextBtn: Button? = null
    private var question: Question? = null

    private var countDownTimer: CountDownTimer? = null

    private val isCorrect: Boolean
        get() {
            var isCorrect = true
            if (!answerSpinnerOne!!.selectedItem.toString().equals(answerArray[4], ignoreCase = true))
                isCorrect = false

            if (!answerSpinnerTwo!!.selectedItem.toString().equals(answerArray[5], ignoreCase = true))
                isCorrect = false

            if (!answerSpinnerThree!!.selectedItem.toString().equals(answerArray[2], ignoreCase = true))
                isCorrect = false

            if (!answerSpinnerFour!!.selectedItem.toString().equals(answerArray[3], ignoreCase = true))
                isCorrect = false

            if (!answerSpinnerFive!!.selectedItem.toString().equals(answerArray[0], ignoreCase = true))
                isCorrect = false

            Log.d("isCorrect: ", "isCorrect: $isCorrect")
            return isCorrect
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_eight)
        getData()
        setUI()
        setListener()
        createSpinnerData()
        setAdapter()
        countDownTime()
    }

    private fun countDownTime() {
        val timeInterval = ConfigService.TIME_INTERVAL
        val time = intArrayOf(31)
        val progress: ProgressBar = findViewById(R.id.progress)
        progress.max = time[0]
        progress.progress = time[0]
        countDownTimer = object : CountDownTimer(timeInterval, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                progress.progress = --time[0]
            }

            override fun onFinish() {
                progress.progress = 0
                countDownTimer!!.cancel()
                StoreAnswerTmse.getInstance().storeAnswer("no8", question!!.id, "")
                val intent = Intent(this@QuestionEightActivity, QuestionNineActivity::class.java)
                startActivity(intent)
            }
        }.start()
    }

    override fun onBackPressed() {
        countDownTimer!!.cancel()
        DialogUtil.getInstance().showDialogExitEvaluation(this)
    }

    override fun initValue() {

    }

    @SuppressLint("SetTextI18n")
    override fun setUI() {
        answerSpinnerOne = findViewById<View>(R.id.answer_day_one) as Spinner
        answerSpinnerTwo = findViewById<View>(R.id.answer_day_two) as Spinner
        answerSpinnerThree = findViewById<View>(R.id.answer_day_three) as Spinner
        answerSpinnerFour = findViewById<View>(R.id.answer_day_four) as Spinner
        answerSpinnerFive = findViewById<View>(R.id.answer_day_five) as Spinner
        nextBtn = findViewById<View>(R.id.next) as Button
        val title = findViewById<TextView>(R.id.title)
        val description = findViewById<TextView>(R.id.description)
        title.text = "(8) " + question!!.title
        description.text = question!!.description
    }

    override fun getData() {
        question = EvaluationModel.getInstance().getEvaluationByNumber("8", this@QuestionEightActivity)!!.question
    }

    override fun initProgressDialog() {

    }

    fun setAdapter() {
        val adapter = HintAdapter(this@QuestionEightActivity, answerArray, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        adapter.setDropDownViewResource(R.layout.spinner_layout)
        answerSpinnerOne!!.adapter = adapter
        answerSpinnerTwo!!.adapter = adapter
        answerSpinnerThree!!.adapter = adapter
        answerSpinnerFour!!.adapter = adapter
        answerSpinnerFive!!.adapter = adapter

        answerSpinnerOne!!.setSelection(adapter.count)
        answerSpinnerTwo!!.setSelection(adapter.count)
        answerSpinnerThree!!.setSelection(adapter.count)
        answerSpinnerFour!!.setSelection(adapter.count)
        answerSpinnerFive!!.setSelection(adapter.count)
    }

    private fun createSpinnerData() {
        answerArray.add("วันจันทร์")
        answerArray.add("วันอาทิตย์")
        answerArray.add("วันพุธ")
        answerArray.add("วันอังคาร")
        answerArray.add("วันศุกร์")
        answerArray.add("วันพฤหัสบดี")
        answerArray.add("วันเสาร์")
        answerArray.add("วัน...")
    }

    private fun setListener() {
        nextBtn!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.next -> {
                countDownTimer!!.cancel()
                StoreAnswerTmse.getInstance().storeAnswer("no8", question!!.id, isCorrect.toString())
                val intent = Intent(this@QuestionEightActivity, QuestionNineActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base))
    }
}



