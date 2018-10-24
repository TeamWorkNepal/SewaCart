package sewacart.com.sewacart.activity.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import sewacart.com.sewacart.R;

public class AddServiceActivty extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        toolbar = findViewById(R.id.toolbar_main_activity);

        toolbar.setTitle("Gaurav Man Shrestha");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddServiceActivty.super.onBackPressed();
            }
        });
    }
}
