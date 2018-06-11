package com.custu.project.walktogether.controller.caretaker

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView

import com.custu.project.project.walktogether.R
import com.custu.project.walktogether.data.Caretaker
import com.custu.project.walktogether.manager.ConnectServer
import com.custu.project.walktogether.model.CaretakerModel
import com.custu.project.walktogether.network.callback.OnDataSuccessListener
import com.custu.project.walktogether.util.BasicActivity
import com.custu.project.walktogether.util.ConfigService
import com.custu.project.walktogether.util.DataFormat
import com.custu.project.walktogether.util.DateTHFormat
import com.custu.project.walktogether.util.InitSpinnerDob
import com.custu.project.walktogether.util.NetworkUtil
import com.custu.project.walktogether.util.PicassoUtil
import com.custu.project.walktogether.util.UserManager
import com.google.gson.JsonObject

import okhttp3.ResponseBody
import retrofit2.Retrofit
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class EditCaretakerProfileActivity : AppCompatActivity(), BasicActivity, View.OnClickListener {
    private val RESULT_LOAD_IMAGE = 1

    private var inputDob: EditText? = null
    private var firstNameEditText: EditText? = null
    private var lastNameEditText: EditText? = null
    private var tellEditText: EditText? = null
    private var occupationEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var ageTextView: TextView? = null
    private var inputDay: Spinner? = null
    private var inputMonth: Spinner? = null
    private var inputYear: Spinner? = null
    private var editImageView: ImageView? = null
    private var profileImageView: ImageView? = null
    private var saveImageView: RelativeLayout? = null
    private var changePassword: Button? = null

    private var caretaker: Caretaker? = null
    private var progressDialog: ProgressDialog? = null
    private var picturePath: String? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var tell: String? = null
    private var occupation: String? = null
    private var email: String? = null

    private val dob: String
        get() {
            return (inputDay!!.selectedItem.toString() + " "
                    + inputMonth!!.selectedItem.toString() + " "
                    + inputYear!!.selectedItem.toString())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_edit_caretaker_profile)
        initProgressDialog()
        setUI()
        getData()
    }

    private fun setListener() {
        editImageView!!.setOnClickListener(this)
        saveImageView!!.setOnClickListener(this)
        changePassword!!.setOnClickListener(this)
        ageTextView!!.text = DateTHFormat.getInstance().birthDayToAge(dob)

        inputDay!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                ageTextView!!.text = DateTHFormat.getInstance().birthDayToAge(dob)

            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

        inputMonth!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                ageTextView!!.text = DateTHFormat.getInstance().birthDayToAge(dob)

            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

        inputYear!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                ageTextView!!.text = DateTHFormat.getInstance().birthDayToAge(dob)

            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
    }

    override fun initValue() {
        firstNameEditText!!.setText(caretaker!!.firstName)
        lastNameEditText!!.setText(caretaker!!.lastName)
        tellEditText!!.setText(caretaker!!.tell)
        occupationEditText!!.setText(DataFormat.getInstance().validateData(caretaker!!.occupation))
        emailEditText!!.setText(caretaker!!.email)
        ageTextView!!.text = caretaker!!.age
        PicassoUtil.getInstance()
                .setImageProfile(this@EditCaretakerProfileActivity, caretaker!!.image, profileImageView)

        firstName = firstNameEditText!!.text.toString().trim { it <= ' ' }
        lastName = lastNameEditText!!.text.toString().trim { it <= ' ' }
        tell = tellEditText!!.text.toString().trim { it <= ' ' }
        occupation = occupationEditText!!.text.toString().trim { it <= ' ' }
        email = emailEditText!!.text.toString().trim { it <= ' ' }
    }

    override fun setUI() {
        inputDob = findViewById(R.id.input_dob)
        firstNameEditText = findViewById(R.id.firstname)
        lastNameEditText = findViewById(R.id.lastname)
        tellEditText = findViewById(R.id.tell)
        occupationEditText = findViewById(R.id.occupation)
        emailEditText = findViewById(R.id.email)
        ageTextView = findViewById(R.id.age)
        inputDay = findViewById(R.id.day)
        inputMonth = findViewById(R.id.month)
        inputYear = findViewById(R.id.year)
        editImageView = findViewById(R.id.change_image_profile)
        profileImageView = findViewById(R.id.image_profile)
        saveImageView = findViewById(R.id.save)
        changePassword = findViewById(R.id.change_password)
    }

    override fun getData() {
        caretaker = UserManager.getInstance(this@EditCaretakerProfileActivity).caretaker
        ConnectServer.getInstance().get(object : OnDataSuccessListener {
            override fun onResponse(`object`: JsonObject?, retrofit: Retrofit) {
                if (`object` != null) {
                    caretaker = CaretakerModel.getInstance().getCaretaker(`object`)
                    UserManager.getInstance(this@EditCaretakerProfileActivity).storeCaretaker(caretaker)
                    setDobSpinner()
                    initValue()
                    setListener()
                }
            }

            override fun onBodyError(responseBodyError: ResponseBody) {

            }

            override fun onBodyErrorIsNull() {

            }

            override fun onFailure(t: Throwable) {
                caretaker = UserManager.getInstance(this@EditCaretakerProfileActivity).caretaker
                NetworkUtil.isOnline(this@EditCaretakerProfileActivity, firstNameEditText)
                setDobSpinner()
                initValue()
                setListener()
            }
        }, ConfigService.CARETAKER + caretaker!!.id!!)
    }

    private fun setDobSpinner() {
        var adapterArray = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, InitSpinnerDob.getInstance().createSpinnerDay())
        inputDay!!.adapter = adapterArray

        adapterArray = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, InitSpinnerDob.getInstance().createSpinnerMonth())
        inputMonth!!.adapter = adapterArray

        adapterArray = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, InitSpinnerDob.getInstance().createSpinnerYear())
        inputYear!!.adapter = adapterArray

        inputDay!!.isSelected = false
        inputDay!!.setSelection(InitSpinnerDob.getInstance().getIndexDay(caretaker!!.dob), true)

        inputMonth!!.isSelected = false
        inputMonth!!.setSelection(InitSpinnerDob.getInstance().getIndexMonth(caretaker!!.dob), true)

        inputYear!!.isSelected = false
        inputYear!!.setSelection(InitSpinnerDob.getInstance().getIndexYear(caretaker!!.dob), true)

    }

    override fun initProgressDialog() {
        progressDialog = ProgressDialog(this@EditCaretakerProfileActivity)
        progressDialog!!.setTitle(getString(R.string.loading))
        progressDialog!!.setCanceledOnTouchOutside(false)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.save -> if (validate()) {
                progressDialog!!.show()
                if (picturePath != null)
                    uploadImage()
                else
                    editProfile()
            }
            R.id.change_image_profile -> browseImage()
            R.id.change_password -> startActivity(Intent(this@EditCaretakerProfileActivity, ChangePasswordCaretakerActivity::class.java))
        }
    }

    private fun validate(): Boolean {
        var validate = true

        if (!DateTHFormat.getInstance().isDateValid(dob)) {
            inputDob!!.error = "กรุณาใส่วันเกิดให้ถูกต้อง"
            inputDob!!.requestFocus()
            validate = false
        }

        if (tellEditText!!.length() != 10) {
            tellEditText!!.error = "เบอร์โทรศัพท์ไม่ถูกต้อง"
            tellEditText!!.requestFocus()
            validate = false
        }

        if (emailEditText!!.length() > 0)
            if (!isEmailValid(emailEditText!!.text.toString())) {
                emailEditText!!.error = "อีเมลไม่ตถูกต้อง"
                emailEditText!!.requestFocus()
                validate = false
            }

        return validate
    }

    private fun isEmailValid(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    fun browseImage() {
        ActivityCompat.requestPermissions(this@EditCaretakerProfileActivity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1)
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, RESULT_LOAD_IMAGE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RESULT_LOAD_IMAGE && null != data) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = contentResolver.query(selectedImage!!,
                        filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                picturePath = cursor.getString(columnIndex)
                cursor.close()
                profileImageView!!.setImageBitmap(BitmapFactory.decodeFile(picturePath))
            }
        }
    }

    fun uploadImage() {
        ConnectServer.getInstance().uploadImage(object : OnDataSuccessListener {
            override fun onResponse(`object`: JsonObject?, retrofit: Retrofit) {
                if (`object` != null) {
                    if (`object`.get("status").asInt == 201) {
                        editProfile()
                    }
                }
            }

            override fun onBodyError(responseBodyError: ResponseBody) {

            }

            override fun onBodyErrorIsNull() {

            }

            override fun onFailure(t: Throwable) {
                progressDialog!!.dismiss()
                NetworkUtil.isOnline(this@EditCaretakerProfileActivity, firstNameEditText)
            }
        }, ConfigService.UPLOAD_IMAGE + ConfigService.CARETAKER, picturePath, caretaker!!.id!!.toString())
    }

    fun editProfile() {
        val jsonObject = JsonObject()

        if (!firstName!!.equals(firstNameEditText!!.text.toString().trim { it <= ' ' }, ignoreCase = true))
            jsonObject.addProperty("firstName", firstNameEditText!!.text.toString().trim { it <= ' ' })

        if (!lastName!!.equals(lastNameEditText!!.text.toString().trim { it <= ' ' }, ignoreCase = true))
            jsonObject.addProperty("lastName", lastNameEditText!!.text.toString().trim { it <= ' ' })

        if (!tell!!.equals(tellEditText!!.text.toString().trim { it <= ' ' }, ignoreCase = true))
            jsonObject.addProperty("tell", tellEditText!!.text.toString().trim { it <= ' ' })

        if (!occupation!!.equals(occupationEditText!!.text.toString().trim { it <= ' ' }, ignoreCase = true))
            jsonObject.addProperty("occupation", occupationEditText!!.text.toString().trim { it <= ' ' })

        if (!email!!.equals(emailEditText!!.text.toString().trim { it <= ' ' }, ignoreCase = true))
            jsonObject.addProperty("email", emailEditText!!.text.toString().trim { it <= ' ' })

        jsonObject.addProperty("dob", dob)

        ConnectServer.getInstance().update(object : OnDataSuccessListener {
            override fun onResponse(`object`: JsonObject?, retrofit: Retrofit) {
                progressDialog!!.dismiss()
                if (`object` != null) {
                    if (`object`.get("status").asInt == 201) {
                        caretaker = CaretakerModel.getInstance().getCaretaker(`object`)
                        UserManager.getInstance(this@EditCaretakerProfileActivity).storeCaretaker(caretaker)
                        startActivity(Intent(this@EditCaretakerProfileActivity, ReHomeCaretakerActivity::class.java))
                    } else {
                        NetworkUtil.showMessageResponse(this@EditCaretakerProfileActivity,
                                firstNameEditText,
                                `object`.get("message").asString)

                    }
                }

            }

            override fun onBodyError(responseBodyError: ResponseBody) {

            }

            override fun onBodyErrorIsNull() {

            }

            override fun onFailure(t: Throwable) {
                progressDialog!!.dismiss()
                NetworkUtil.isOnline(this@EditCaretakerProfileActivity, firstNameEditText)
            }
        }, ConfigService.CARETAKER + caretaker!!.id!!, jsonObject)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@EditCaretakerProfileActivity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            1)
                }
            }
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.cancel()
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base))
    }
}
