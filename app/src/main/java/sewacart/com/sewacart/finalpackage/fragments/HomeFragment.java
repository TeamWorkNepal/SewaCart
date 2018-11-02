package sewacart.com.sewacart.finalpackage.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.adapter.ServiceAdapter;
import sewacart.com.sewacart.finalpackage.adapter.ServiceCategoryAdapter;
import sewacart.com.sewacart.finalpackage.controller.NetworkDetectController;
import sewacart.com.sewacart.finalpackage.model.HomeModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class HomeFragment extends Fragment {

    Context context;
    Spinner spinner;
    @BindView(R.id.wrapper_recy)
    LinearLayout wrapperRecy;
    ServiceAdapter serviceAdapter;
    ServiceCategoryAdapter serviceCategoryAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    LayoutInflater inflater1;
    View inflatedView;
    TextView title,viewmore;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        spinner = view.findViewById(R.id.spinner);
        ButterKnife.bind(this, view);
        if (NetworkDetectController.checkConnection(context)) {
            loadAllData();
        } else {
            final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
            pDialog.setTitleText("Oops...");
            pDialog.setContentText("No Internet Connection !");
            pDialog.show();
        }
        inflater1 = (LayoutInflater)getActivity().getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        ArrayAdapter adapter = ArrayAdapter.createFromResource(context, R.array.addresses, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }


    private void loadAllData() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Call<HomeModel> call = userInterface.loadAllData();
        call.enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(@NonNull Call<HomeModel> call, @NonNull Response<HomeModel> response) {

                pDialog.dismiss();
                final HomeModel homeModel = response.body();

                List<HomeModel.Category> categories = new ArrayList<>();
                categories.addAll(homeModel.getCategories());
                serviceAdapter = new ServiceAdapter(context, categories);
                recyclerView = new RecyclerView(context);
                linearLayoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(serviceAdapter);
                inflatedView = inflater1.inflate(R.layout.item_viewmore,null);
                title = inflatedView.findViewById(R.id.item_service_title);
                viewmore = inflatedView.findViewById(R.id.item_service_view_more);
                viewmore.setVisibility(View.GONE);
                title.setText("Our Services");

                wrapperRecy.addView(inflatedView);
                wrapperRecy.addView(recyclerView);

                for(int i =0;i<homeModel.getServiceByCategory().size();i++){

                    serviceCategoryAdapter = new ServiceCategoryAdapter(context,homeModel.getServiceByCategory().get(i).getService());
                    recyclerView = new RecyclerView(context);
                    linearLayoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(serviceCategoryAdapter);
                    inflatedView = inflater1.inflate(R.layout.item_viewmore,null);

                    if (inflatedView.getParent() != null)
                        ((ViewGroup) inflatedView.getParent()).removeView(inflatedView);

                    title = inflatedView.findViewById(R.id.item_service_title);
                    viewmore = inflatedView.findViewById(R.id.item_service_view_more);
                    title.setText(homeModel.getServiceByCategory().get(i).getTitle());
                    final int finalI = i;
                    viewmore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context,homeModel.getServiceByCategory().get(finalI).getTitle(),Toast.LENGTH_SHORT).show();
                        }
                    });

                    wrapperRecy.addView(inflatedView);
                    wrapperRecy.addView(recyclerView);

                }

            }

            @Override
            public void onFailure(@NonNull Call<HomeModel> call, @NonNull Throwable t) {
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
}
