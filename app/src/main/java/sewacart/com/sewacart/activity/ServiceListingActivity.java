package sewacart.com.sewacart.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import sewacart.com.sewacart.R;

public class ServiceListingActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_listing);
        listView = findViewById(R.id.service_list);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(ServiceListingActivity.this, R.array.second_level_service,android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
    }
}
