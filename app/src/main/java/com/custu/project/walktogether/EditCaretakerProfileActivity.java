package com.custu.project.walktogether;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.InitSpinnerDob;
import com.custu.project.walktogether.util.PicassoUtil;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.JsonObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class EditCaretakerProfileActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private final int RESULT_LOAD_IMAGE = 1;

    private EditText titleName;
    private EditText firstName;
    private EditText lastName;
    private EditText tell;
    private EditText occupation;
    private EditText email;
    private TextView ageTextView;
    private Spinner inputDay;
    private Spinner inputMonth;
    private Spinner inputYear;
    private CircleImageView editImageView;
    private CircleImageView profileImageView;
    private ImageView saveImageView;

    private Caretaker caretaker;
    private ProgressDialog progressDialog;
    private String picturePath;
    private Intent intent;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_caretaker_profile);
        getSupportActionBar().hide();
        initProgressDialog();
        setUI();
        getData();
        setDobSpinner();
        initValue();
        setListener();
    }

    private void setListener() {
        editImageView.setOnClickListener(this);
        saveImageView.setOnClickListener(this);
    }

    @Override
    public void initValue() {
        titleName.setText(caretaker.getTitleName());
        firstName.setText(caretaker.getFirstName());
        lastName.setText(caretaker.getLastName());
        tell.setText(caretaker.getTell());
        occupation.setText(caretaker.getOccupation());
        email.setText(caretaker.getEmail());
        ageTextView.setText(caretaker.getAge());
        PicassoUtil.getInstance()
                .setImageProfile(EditCaretakerProfileActivity.this
                        , caretaker.getImage()
                        , profileImageView);
    }

    @Override
    public void setUI() {
        titleName.findViewById(R.id.title_name);
        firstName.findViewById(R.id.firstname);
        lastName.findViewById(R.id.lastname);
        tell.findViewById(R.id.tell);
        occupation.findViewById(R.id.occupation);
        email.findViewById(R.id.email);
        ageTextView.findViewById(R.id.age);
        inputDay.findViewById(R.id.day);
        inputMonth.findViewById(R.id.month);
        inputYear.findViewById(R.id.year);
        editImageView.findViewById(R.id.change_image_profile);
        profileImageView.findViewById(R.id.image_profile);
        saveImageView.findViewById(R.id.save);
    }

    @Override
    public void getData() {
        caretaker = UserManager.getInstance(EditCaretakerProfileActivity.this).getCaretaker();
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
        inputDay.setSelection(InitSpinnerDob.getInstance().getIndexMonth(caretaker.getDob()), true);

        inputYear.setSelected(false);
        inputDay.setSelection(InitSpinnerDob.getInstance().getIndexYear(caretaker.getDob()), true);

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
                if (picturePath != null)
                    uploadImage();
                else
                    editProfile();
                break;
            case R.id.change_image_profile:
                browseImage();
                break;
        }
    }

    public void browseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), RESULT_LOAD_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_LOAD_IMAGE) {
                Uri selectedImageUri = data.getData();
                picturePath = selectedImageUri.getPath();
                bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(EditCaretakerProfileActivity.this.getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profileImageView.setImageBitmap(bitmap);
            }
        }
    }

    public void uploadImage() {
        ConnectServer.getInstance().uploadImage(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                if (object != null) {
                    if (object.get("status").getAsInt() == 200) {
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

            }
        }, ConfigService.UPLOAD_IMAGE + ConfigService.CARETAKER, picturePath, caretaker.getId().toString());
    }

    public void editProfile() {

    }
}
