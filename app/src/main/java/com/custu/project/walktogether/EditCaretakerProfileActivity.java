package com.custu.project.walktogether;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.CaretakerModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.DateTHFormat;
import com.custu.project.walktogether.util.InitSpinnerDob;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.PicassoUtil;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditCaretakerProfileActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private final int RESULT_LOAD_IMAGE = 1;

    private EditText titleNamEditText;
    private EditText inputDob;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText tellEditText;
    private EditText occupationEditText;
    private EditText emailEditText;
    private TextView ageTextView;
    private Spinner inputDay;
    private Spinner inputMonth;
    private Spinner inputYear;
    private ImageView editImageView;
    private ImageView profileImageView;
    private RelativeLayout saveImageView;
    private Button changePassword;

    private Caretaker caretaker;
    private ProgressDialog progressDialog;
    private String picturePath;
    private String title;
    private String firstName;
    private String lastName;
    private String tell;
    private String occupation;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_caretaker_profile);
        initProgressDialog();
        setUI();
        getData();
    }

    private void setListener() {
        editImageView.setOnClickListener(this);
        saveImageView.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        ageTextView.setText(DateTHFormat.getInstance().birthDayToAge(getDob()));

        inputDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ageTextView.setText(DateTHFormat.getInstance().birthDayToAge(getDob()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inputMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ageTextView.setText(DateTHFormat.getInstance().birthDayToAge(getDob()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inputYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ageTextView.setText(DateTHFormat.getInstance().birthDayToAge(getDob()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void initValue() {
        titleNamEditText.setText(caretaker.getTitleName());
        firstNameEditText.setText(caretaker.getFirstName());
        lastNameEditText.setText(caretaker.getLastName());
        tellEditText.setText(caretaker.getTell());
        occupationEditText.setText(caretaker.getOccupation());
        emailEditText.setText(caretaker.getEmail());
        ageTextView.setText(caretaker.getAge());
        PicassoUtil.getInstance()
                .setImageProfile(EditCaretakerProfileActivity.this
                        , caretaker.getImage()
                        , profileImageView);

        title = titleNamEditText.getText().toString().trim();
        firstName = firstNameEditText.getText().toString().trim();
        lastName = lastNameEditText.getText().toString().trim();
        tell = tellEditText.getText().toString().trim();
        occupation = occupationEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
    }

    @Override
    public void setUI() {
        inputDob = findViewById(R.id.input_dob);
        titleNamEditText = findViewById(R.id.title);
        firstNameEditText = findViewById(R.id.firstname);
        lastNameEditText = findViewById(R.id.lastname);
        tellEditText = findViewById(R.id.tell);
        occupationEditText = findViewById(R.id.occupation);
        emailEditText = findViewById(R.id.email);
        ageTextView = findViewById(R.id.age);
        inputDay = findViewById(R.id.day);
        inputMonth = findViewById(R.id.month);
        inputYear = findViewById(R.id.year);
        editImageView = findViewById(R.id.change_image_profile);
        profileImageView = findViewById(R.id.image_profile);
        saveImageView = findViewById(R.id.save);
        changePassword = findViewById(R.id.change_password);
    }

    public void getData() {
        caretaker = UserManager.getInstance(EditCaretakerProfileActivity.this).getCaretaker();
        ConnectServer.getInstance().get(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                if (object != null) {
                    caretaker = CaretakerModel.getInstance().getCaretaker(object);
                    UserManager.getInstance(EditCaretakerProfileActivity.this).storeCaretaker(caretaker);
                    setDobSpinner();
                    initValue();
                    setListener();
                }
            }

            @Override
            public void onBodyError(ResponseBody responseBodyError) {

            }

            @Override
            public void onBodyErrorIsNull() {

            }

            @Override
            public void onFailure(Throwable t) {
                caretaker = UserManager.getInstance(EditCaretakerProfileActivity.this).getCaretaker();
                NetworkUtil.isOnline(EditCaretakerProfileActivity.this, firstNameEditText);
                setDobSpinner();
                initValue();
                setListener();
            }
        }, ConfigService.CARETAKER + caretaker.getId());
    }

    private void setDobSpinner() {
        ArrayAdapter<String> adapterArray = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, InitSpinnerDob.getInstance().createSpinnerDay());
        inputDay.setAdapter(adapterArray);

        adapterArray = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, InitSpinnerDob.getInstance().createSpinnerMonth());
        inputMonth.setAdapter(adapterArray);

        adapterArray = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, InitSpinnerDob.getInstance().createSpinnerYear());
        inputYear.setAdapter(adapterArray);

        inputDay.setSelected(false);
        inputDay.setSelection(InitSpinnerDob.getInstance().getIndexDay(caretaker.getDob()), true);

        inputMonth.setSelected(false);
        inputMonth.setSelection(InitSpinnerDob.getInstance().getIndexMonth(caretaker.getDob()), true);

        inputYear.setSelected(false);
        inputYear.setSelection(InitSpinnerDob.getInstance().getIndexYear(caretaker.getDob()), true);

    }

    @Override
    public void initProgressDialog() {
        progressDialog = new ProgressDialog(EditCaretakerProfileActivity.this);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                if (validate()) {
                    progressDialog.show();
                    if (picturePath != null)
                        uploadImage();
                    else
                        editProfile();
                }
                break;
            case R.id.change_image_profile:
                browseImage();
                break;
            case R.id.change_password:
                startActivity(new Intent(EditCaretakerProfileActivity.this, ChangePasswordCaretakerActivity.class));
                break;
        }
    }

    private boolean validate() {
        boolean validate = true;

        if (!DateTHFormat.getInstance().isDateValid(getDob())) {
            inputDob.setError("กรุณาใส่วันเกิดให้ถูกต้อง");
            inputDob.requestFocus();
            validate = false;
        }

        if (tellEditText.length() != 10) {
            tellEditText.setError("เบอร์โทรศัพท์ไม่ถูกต้อง");
            tellEditText.requestFocus();
            validate = false;
        }

        if (emailEditText.length() > 0)
            if (!isEmailValid(emailEditText.getText().toString())) {
                emailEditText.setError("อีเมลไม่ตถูกต้อง");
                emailEditText.requestFocus();
                validate = false;
            }

        return validate;
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public void browseImage() {
        ActivityCompat.requestPermissions(EditCaretakerProfileActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_LOAD_IMAGE && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
                profileImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }
    }

    public void uploadImage() {
        ConnectServer.getInstance().uploadImage(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                if (object != null) {
                    if (object.get("status").getAsInt() == 201) {
                        editProfile();
                    }
                }
            }

            @Override
            public void onBodyError(ResponseBody responseBodyError) {

            }

            @Override
            public void onBodyErrorIsNull() {

            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                NetworkUtil.isOnline(EditCaretakerProfileActivity.this, firstNameEditText);
            }
        }, ConfigService.UPLOAD_IMAGE + ConfigService.CARETAKER, picturePath, caretaker.getId().toString());
    }

    public void editProfile() {
        JsonObject jsonObject = new JsonObject();

        if (!title.equalsIgnoreCase(titleNamEditText.getText().toString().trim()))
            jsonObject.addProperty("titleName", titleNamEditText.getText().toString().trim());

        if (!firstName.equalsIgnoreCase(firstNameEditText.getText().toString().trim()))
            jsonObject.addProperty("firstName", firstNameEditText.getText().toString().trim());

        if (!lastName.equalsIgnoreCase(lastNameEditText.getText().toString().trim()))
            jsonObject.addProperty("lastName", lastNameEditText.getText().toString().trim());

        if (!tell.equalsIgnoreCase(tellEditText.getText().toString().trim()))
            jsonObject.addProperty("tell", tellEditText.getText().toString().trim());

        if (!occupation.equalsIgnoreCase(occupationEditText.getText().toString().trim()))
            jsonObject.addProperty("occupation", occupationEditText.getText().toString().trim());

        if (!email.equalsIgnoreCase(emailEditText.getText().toString().trim()))
            jsonObject.addProperty("email", emailEditText.getText().toString().trim());

        jsonObject.addProperty("dob", getDob());

        ConnectServer.getInstance().update(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                progressDialog.dismiss();
                if (object != null) {
                    if (object.get("status").getAsInt() == 201) {
                        caretaker = CaretakerModel.getInstance().getCaretaker(object);
                        UserManager.getInstance(EditCaretakerProfileActivity.this).storeCaretaker(caretaker);
                        startActivity(new Intent(EditCaretakerProfileActivity.this, ReHomeCaretakerActivity.class));
                    } else {
                        NetworkUtil.showMessageResponse(EditCaretakerProfileActivity.this,
                                firstNameEditText,
                                object.get("message").getAsString());

                    }
                }

            }

            @Override
            public void onBodyError(ResponseBody responseBodyError) {

            }

            @Override
            public void onBodyErrorIsNull() {

            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                NetworkUtil.isOnline(EditCaretakerProfileActivity.this, firstNameEditText);
            }
        }, ConfigService.CARETAKER + caretaker.getId(), jsonObject);
    }

    private String getDob() {
        String dobString;
        dobString = inputDay.getSelectedItem().toString() + " "
                + inputMonth.getSelectedItem().toString() + " "
                + inputYear.getSelectedItem().toString();
        return dobString;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EditCaretakerProfileActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
