package sewacart.com.sewacart.finalpackage.activity;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.controller.SharedPreferenceController;
import sewacart.com.sewacart.finalpackage.model.UserModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView editIconImage;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.profile_name_title)
    TextView profileNameTitle;
    @BindView(R.id.profile_service)
    TextView profileService;
    @BindView(R.id.profile_name)
    TextView profileName;
    @BindView(R.id.profile_email)
    TextView profileEmail;
    @BindView(R.id.profile_phone)
    TextView profilePhone;
    @BindView(R.id.profile_address)
    TextView profileAddress;
    @BindView(R.id.main_wrapper)
    LinearLayout mainWrapper;
    @BindView(R.id.profile_mobile)
    TextView profileMobile;
    @BindView(R.id.profile_state)
    TextView profileState;
    @BindView(R.id.profile_city)
    TextView profileCity;
    @BindView(R.id.profile_street)
    TextView profileStreet;
    @BindView(R.id.profile_description)
    TextView profileDescription;
    @BindView(R.id.profile_zip)
    TextView profileZip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbar2);
        editIconImage = toolbar.findViewById(R.id.editIconImage);

        loadProfile();
    }


    private void loadProfile() {
        mainWrapper.setVisibility(View.GONE);

        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Map<String, String> params = new HashMap<String, String>();
         params.put("user_id", SharedPreferenceController.getUserDetails(ProfileActivity.this).getId());
        //TODO: make it dynamic
       // params.put("user_id", "1");
        Call<UserModel> call = userInterface.getUserDetailsById(params);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                pDialog.dismiss();
                if (response.body().getValue() == 1) {
                    final UserModel.UserDetails userDetails = response.body().getUserDetails();
                    mainWrapper.setVisibility(View.VISIBLE);

                    setSupportActionBar(toolbar);
                    toolbar.setNavigationIcon(R.drawable.arrow_back_white);
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(ProfileActivity.this, MainActivity.class));

                        }
                    });
                    if (userDetails != null) {
                        editIconImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("data", Parcels.wrap(userDetails));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

                    }


                    toolbar.setTitle(userDetails.getName());
                    profileName.setText(userDetails.getName());
                    profileNameTitle.setText(userDetails.getName());
                    profileEmail.setText(userDetails.getEmail());
                    profilePhone.setText(userDetails.getPhone());
                    profileMobile.setText(userDetails.getMobile());
                    profileState.setText(userDetails.getState());
                    profileCity.setText(userDetails.getCity());
                    profileZip.setText(userDetails.getZip());
                    profileStreet.setText(userDetails.getStreet());
                    profileAddress.setText(userDetails.getExtraAddress());
                    profileDescription.setText(userDetails.getDescription());

                    profileImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Pair[] pairs = new Pair[1];
                            pairs[0] = new Pair<View, String>(profileImage, "fullImage");

                            Intent chatIntent = new Intent(ProfileActivity.this, FullImageActivity.class);
                            ActivityOptions activityOptions = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                activityOptions = ActivityOptions.makeSceneTransitionAnimation(ProfileActivity.this, pairs);
                            }
                            chatIntent.putExtra("imageUrl", userDetails.getUserPhoto());
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                startActivity(chatIntent, activityOptions.toBundle());
                            } else {
                                startActivity(chatIntent);
                            }

                        }
                    });
                    if (!userDetails.getUserPhoto().isEmpty()) {
                        Picasso.with(ProfileActivity.this).load(userDetails.getUserPhoto()).placeholder(R.drawable.default_user).networkPolicy(NetworkPolicy.OFFLINE).into(profileImage, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Picasso.with(ProfileActivity.this).load(userDetails.getUserPhoto()).placeholder(R.drawable.default_user).into(profileImage);
                            }


                        });
                    }


                } else {
                    fallback();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                pDialog.dismiss();

                fallback();

            }
        });
    }

    private void fallback() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Oops...");
        pDialog.setContentText("Something went wrong!");
        pDialog.show();
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }
}
