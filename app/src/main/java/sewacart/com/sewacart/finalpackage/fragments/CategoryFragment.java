package sewacart.com.sewacart.finalpackage.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
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
import sewacart.com.sewacart.finalpackage.adapter.CategoryAdapter;
import sewacart.com.sewacart.finalpackage.model.CategoryModel;
import sewacart.com.sewacart.finalpackage.model.HomeModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;


public class CategoryFragment extends Fragment {
    Context context;
    @BindView(R.id.gridview)
    GridView gridview;
    List<CategoryModel> categoryModels = new ArrayList<>();
    CategoryAdapter categoryAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        categoryAdapter = new CategoryAdapter(context, R.layout.item_service_layout, categoryModels);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getCategory();
    }

    private void getCategory() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put("parent", "0");
        Call<List<CategoryModel>> call = userInterface.getCategories(params);
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CategoryModel>> call, @NonNull final Response<List<CategoryModel>> response) {
                pDialog.dismiss();

                categoryModels.addAll(response.body());

                gridview.setAdapter(categoryAdapter);


               /* topCategories = new String[response.body().size()];
                topCategoriesId = new Integer[response.body().size()];

                for (int i = 0; i < response.body().size(); i++) {
                    topCategories[i] = response.body().get(i).getCategoryTitle();
                    topCategoriesId[i] = response.body().get(i).getCategoryId();
                }
                if (response.body().size() > 0) {
                    ArrayAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, topCategories);
                    categoryListview.setAdapter(adapter);
                }

                categoryListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(context, SubCategoryListingActivity.class);
                        intent.putExtra("data",topCategories[position]);
                        intent.putExtra("data1",topCategoriesId[position]);
                        startActivity(intent);
                    }
                });*/


            }

            @Override
            public void onFailure(@NonNull Call<List<CategoryModel>> call, @NonNull Throwable t) {
                pDialog.dismiss();
                fallback();
            }
        });
    }

    private void fallback() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Oops...");
        pDialog.setContentText("Something went wrong!");
        pDialog.show();

    }

    @Override
    public void onPause() {
        super.onPause();
        categoryModels.clear();
        if (categoryAdapter != null)
            categoryAdapter.notifyDataSetChanged();
    }
    
}
