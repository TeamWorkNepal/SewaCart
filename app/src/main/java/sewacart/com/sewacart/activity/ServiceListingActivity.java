package sewacart.com.sewacart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import sewacart.com.sewacart.R;
import sewacart.com.sewacart.activity.test.TestServiceProviderListingItem;

public class ServiceListingActivity extends AppCompatActivity {
    ListView listView;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_listing);
        listView = findViewById(R.id.service_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        setTitle("Services");
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceListingActivity.super.onBackPressed();
            }
        });


        ArrayAdapter adapter = ArrayAdapter.createFromResource(ServiceListingActivity.this, R.array.second_level_service, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(ServiceListingActivity.this, TestServiceProviderListingItem.class));
            }
        });
    }
}
