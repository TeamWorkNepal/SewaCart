package sewacart.com.sewacart.finalpackage.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class RegisterServiceProviderActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_main_activity)
    Toolbar toolbarMainActivity;
    @BindView(R.id.signup_message)
    TextView signupMessage;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.input_password_again)
    EditText inputPasswordAgain;
    @BindView(R.id.ripple_register)
    MaterialRippleLayout rippleRegister;
    @BindView(R.id.link_signin)
    TextView linkSignin;
    @BindView(R.id.input_username)
    EditText inputUsername;
    @BindView(R.id.btn_message)
    TextView btnMessage;
    @BindView(R.id.become_provider)
    MaterialRippleLayout becomeProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        toolbarMainActivity = findViewById(R.id.toolbar_main_activity);
        setSupportActionBar(toolbarMainActivity);
        becomeProvider.setVisibility(View.GONE);
        btnMessage.setVisibility(View.GONE);
        linkSignin.setVisibility(View.GONE);
        setTitle("");
    }

    @OnClick(R.id.ripple_register)
    public void onRippleRegisterClicked() {
        register();
    }

    private void register() {

        String username = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String passwordAgain = inputPasswordAgain.getText().toString();

        if (username.length() != 0 && email.length() != 0 && password.length() != 0 && passwordAgain.length() != 0) {
            if (username.length() > 2) {
                if (password.equals(passwordAgain)) {

                    final SweetAlertDialog pDialog = new SweetAlertDialog(RegisterServiceProviderActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setContentText("Signing you in... please wait !");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    final UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
                    Call<UserModel> call = userInterface.registerUser("register_service_provider", username, email, password);
                    call.enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                            pDialog.dismiss();
                            UserModel userModel = response.body();
                            if (userModel.getValue() == 1) {

                                Toast.makeText(RegisterServiceProviderActivity.this, "Registration Successful !", Toast.LENGTH_SHORT).show();
                                SharedPreferenceController.saveLoginLog(RegisterServiceProviderActivity.this, true);
                                SharedPreferenceController.saveUserDetails(RegisterServiceProviderActivity.this, userModel.getUserDetails());
                                Intent intent = new Intent(RegisterServiceProviderActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);


                            } else {
                                Toast.makeText(RegisterServiceProviderActivity.this, userModel.getData_mob(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                            pDialog.dismiss();
                            Toast.makeText(RegisterServiceProviderActivity.this, "Something Went Wrong !", Toast.LENGTH_SHORT).show();

                        }
                    });
                } else {
                    Toast.makeText(RegisterServiceProviderActivity.this, "Password doesn't match !", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterServiceProviderActivity.this, "User name is too Short !", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterServiceProviderActivity.this, "All Fields are Required !", Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  startActivity(new Intent(RegisterServiceProviderActivity.this, RegisterActivity.class));

    }
}


