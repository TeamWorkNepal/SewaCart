package sewacart.com.sewacart.activity.test;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import sewacart.com.sewacart.R;

public class TestServiceProviderListingItem extends AppCompatActivity {

    Button service_provider_details;
    private Toolbar toolbar;
    CircleImageView service_provider_image;
    TextView service_provider_name, service_provider_address, service_provider_price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_service_provide_listing);

        service_provider_details = findViewById(R.id.service_provider_details);
        service_provider_image = findViewById(R.id.service_provider_image);
        service_provider_address = findViewById(R.id.service_provider_address);
        service_provider_name = findViewById(R.id.service_provider_name);
        service_provider_price = findViewById(R.id.service_provider_price);

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        setTitle("Service Providers");
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestServiceProviderListingItem.super.onBackPressed();
            }
        });

        service_provider_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pair[] pairs = new Pair[2];

                pairs[0] = new Pair<View, String>(service_provider_image, "fullImage");
                pairs[1] = new Pair<View, String>(service_provider_price, "sharedPrice");

                Intent intent = new Intent(TestServiceProviderListingItem.this, ServiceProviderDetails.class);
                ActivityOptions activityOptions = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    activityOptions = ActivityOptions.makeSceneTransitionAnimation(TestServiceProviderListingItem.this, pairs);
                }

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, activityOptions.toBundle());
                } else {
                    startActivity(intent);
                }

            }
        });

    }
}
