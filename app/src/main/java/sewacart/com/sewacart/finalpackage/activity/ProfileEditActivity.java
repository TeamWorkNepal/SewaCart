package sewacart.com.sewacart.finalpackage.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import sewacart.com.sewacart.R;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);

        toolbar.setTitle("Gaurav Man Shrestha");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileEditActivity.super.onBackPressed();
            }
        });

    }

    @OnClick(R.id.update_profile)
    public void onViewClicked() {
    }
}
