package sewacart.com.sewacart.finalpackage.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.model.CategoryModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class SubCategoryListingActivity extends AppCompatActivity {
    @BindView(R.id.sub_category_listview)
    ListView subCategoryListview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    String data;
    int parentId;
    String[] topCategories;
    @BindView(R.id.default_msg)
    TextView defaultMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category_listing);
        ButterKnife.bind(this);

        data = getIntent().getStringExtra("data");
        parentId = getIntent().getIntExtra("data1", 0);
        if (data == null) {
            data = "Sub Category";
        }
        toolbar.setTitle(data);
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubCategoryListingActivity.super.onBackPressed();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        getCategory();
    }

    private void getCategory() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(SubCategoryListingActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put("parent", parentId + "");
        Call<List<CategoryModel>> call = userInterface.getCategories(params);
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CategoryModel>> call, @NonNull final Response<List<CategoryModel>> response) {
                pDialog.dismiss();
                topCategories = new String[response.body().size()];
                for (int i = 0; i < response.body().size(); i++) {
                    topCategories[i] = response.body().get(i).getCategoryTitle();
                }
                if (response.body().size() > 0) {
                    defaultMsg.setVisibility(View.GONE);
                    ArrayAdapter adapter = new ArrayAdapter<String>(SubCategoryListingActivity.this, android.R.layout.simple_list_item_1, topCategories);
                    subCategoryListview.setAdapter(adapter);
                    subCategoryListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(SubCategoryListingActivity.this, ServiceListingActivity.class);
                            intent.putExtra("category_id", response.body().get(position).getCategoryId());
                            intent.putExtra("own", false);
                            startActivity(intent);
                        }
                    });
                } else {
                    defaultMsg.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(@NonNull Call<List<CategoryModel>> call, @NonNull Throwable t) {
                pDialog.dismiss();
                fallback();
            }
        });
    }

    private void fallback() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(SubCategoryListingActivity.this, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Oops...");
        pDialog.setContentText("Something went wrong!");
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(new Intent(SubCategoryListingActivity.this, MainActivity.class));
            }
        });
        pDialog.show();

    }

    @Override
    protected void onStop() {
        super.onStop();
        defaultMsg.setVisibility(View.GONE);
    }
}
