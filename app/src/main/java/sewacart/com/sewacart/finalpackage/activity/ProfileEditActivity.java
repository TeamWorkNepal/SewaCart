package sewacart.com.sewacart.finalpackage.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.model.UserModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class ProfileEditActivity extends AppCompatActivity {
    Toolbar toolbar;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.telephone)
    EditText telephone;
    @BindView(R.id.state)
    EditText state;
    @BindView(R.id.street)
    EditText street;
    @BindView(R.id.additional_address)
    EditText additionalAddress;
    @BindView(R.id.about_me)
    EditText aboutMe;
    @BindView(R.id.update_profile)
    AppCompatButton updateProfile;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.btn_update_image)
    AppCompatButton btnUpdateImage;
    UserModel.UserDetails details;
    @BindView(R.id.city)
    EditText city;
    @BindView(R.id.zip)
    EditText zip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbar2);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileEditActivity.super.onBackPressed();
            }
        });


        // Saving initial data before updating data
        details = Parcels.unwrap(getIntent().getParcelableExtra("data"));
        toolbar.setTitle(details.getName());
        titleName.setText(details.getName());
        name.setText(details.getName());
        email.setText(details.getEmail());
        phone.setText(details.getMobile());
        telephone.setText(details.getPhone());
        state.setText(details.getState());
        city.setText(details.getCity());
        zip.setText(details.getZip());
        street.setText(details.getStreet());
        additionalAddress.setText(details.getExtraAddress());
        aboutMe.setText(details.getDescription());
        if (!details.getUserPhoto().isEmpty()) {
            Picasso.with(ProfileEditActivity.this).load(details.getUserPhoto()).placeholder(R.drawable.default_user).networkPolicy(NetworkPolicy.OFFLINE).into(profileImage, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError() {
                    Picasso.with(ProfileEditActivity.this).load(details.getUserPhoto()).placeholder(R.drawable.default_user).into(profileImage);
                }


            });
        }
        //End of loading initail data

    }

    @OnClick({R.id.btn_update_image, R.id.update_profile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_update_image:
                updateProfileImage();
                break;
            case R.id.update_profile:
                updateProfileInfo();
                break;
        }
    }

    private void updateProfileImage() {
        ImagePicker.create(ProfileEditActivity.this)
                .folderMode(true)
                .single()
                .start();

    }

    private void updateProfileInfo() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(ProfileEditActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setContentText("Updating your profile image...");
        pDialog.setCancelable(false);
        pDialog.show();

        String mName = name.getText().toString();
        String mEmail = email.getText().toString();
        String mMobile = phone.getText().toString();
        String mPhone = telephone.getText().toString();
        String mState = state.getText().toString();
        String mCity = city.getText().toString();
        String mZip = zip.getText().toString();
        String mStreet = street.getText().toString();
        String mAddress = additionalAddress.getText().toString();
        String mAboutMe = aboutMe.getText().toString();

        final UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Call<UserModel> call = userInterface.updateProfile("edit_profile", details.getId(), mName, mEmail, mPhone, mMobile, mState, mCity, mZip, mStreet, mAddress, mAboutMe, "false");
        call.enqueue(new retrofit2.Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                pDialog.dismiss();
                if(response.body().getValue()==1){
                    Toast.makeText(ProfileEditActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProfileEditActivity.this, ProfileActivity.class));
                    finish();
                }else{
                    Toast.makeText(ProfileEditActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
               fallback();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {

            com.esafirm.imagepicker.model.Image image = ImagePicker.getFirstImageOrNull(data);

            try {
                File compressedImageFile = new Compressor(ProfileEditActivity.this)
                        .setQuality(50)
                        .compressToFile(new File(image.getPath()));

                String updoadImageString = compressedImageFile.toString();
                File showImage = new File(image.getPath());

                if(!image.toString().isEmpty()){
                    Picasso.with(ProfileEditActivity.this).load(showImage).into(profileImage);
                }
                final SweetAlertDialog pDialog = new SweetAlertDialog(ProfileEditActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setContentText("Updating your profile info...");
                pDialog.setCancelable(false);
                pDialog.show();

                String mUseId = details.getId();

                final MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.setType(MultipartBody.FORM);
                builder.addFormDataPart("user_id", mUseId);
                builder.addFormDataPart("action", "edit_profile");
                builder.addFormDataPart("imageonly", "true");
                builder.addFormDataPart("imageUpload", showImage.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), showImage));
                final MultipartBody requestBody = builder.build();
                UserInterface postInterface = ApiClient.getApiClient().create(UserInterface.class);
                Call<UserModel> call = postInterface.updateImage(requestBody);
                call.enqueue(new retrofit2.Callback<UserModel>() {
                    @Override
                    public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                        pDialog.dismiss();
                        if(response.body().getValue()==1){
                            Toast.makeText(ProfileEditActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ProfileEditActivity.this, ProfileActivity.class));
                            finish();

                        }else{
                            Toast.makeText(ProfileEditActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                        pDialog.dismiss();
                        fallback();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
                fallback();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void fallback() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(ProfileEditActivity.this, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Oops...");
        pDialog.setContentText("Something went wrong!");
        pDialog.show();
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(new Intent(ProfileEditActivity.this, ProfileActivity.class));
                finish();
            }
        });
    }
}