package sewacart.com.sewacart.finalpackage.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.controller.SharedPreferenceController;
import sewacart.com.sewacart.finalpackage.model.UserModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class LoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    @BindView(R.id.ripple)
    MaterialRippleLayout ripple;
    @BindView(R.id.link_signup)
    TextView linkSignup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar_main_activity);
        setSupportActionBar(toolbar);
        setTitle("");


    }


    private void login() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (email.length() != 0 && password.length() != 0) {

            final SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setContentText("Signing you in... please wait !");
            pDialog.setCancelable(false);
            pDialog.show();

            final UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
            Call<UserModel> call = userInterface.signInUser("login_user", email, password);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                    pDialog.dismiss();
                    UserModel userModel = response.body();
                    if (userModel.getValue() == 1) {
                        Toast.makeText(LoginActivity.this, "Login Successful !", Toast.LENGTH_SHORT).show();
                        SharedPreferenceController.saveLoginLog(LoginActivity.this, true);
                        SharedPreferenceController.saveUserDetails(LoginActivity.this, userModel.getUserDetails());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoginActivity.this, userModel.getData_mob(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                    pDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Something Went Wrong !", Toast.LENGTH_SHORT).show();

                }
            });

        } else {
            Toast.makeText(LoginActivity.this, "All Fields are Required !", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.ripple)
    public void onRippleClicked() {
        login();
    }

    @OnClick(R.id.link_signup)
    public void onLinkSignupClicked() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      /*  moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);*/
        finish();
    }
}
