package sewacart.com.sewacart.finalpackage.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.activity.ServiceProviderDetailsActivity;
import sewacart.com.sewacart.finalpackage.model.HomeModel;
import sewacart.com.sewacart.finalpackage.model.ProviderModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class ServiceCategoryAdapter extends RecyclerView.Adapter<ServiceCategoryAdapter.ViewHolder> {
    Context context;
    List<HomeModel.Service> services;

    public ServiceCategoryAdapter(Context context, List<HomeModel.Service> services) {
        this.context = context;
        this.services = services;
    }


    @NonNull
    @Override
    public ServiceCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_layout, parent, false);
        return new ServiceCategoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceCategoryAdapter.ViewHolder holder, int position) {
        final HomeModel.Service service = services.get(position);
        holder.serviceName.setText(service.getTitle());
        if (!service.getCropImage().isEmpty()) {
            Picasso.with(context).load(service.getCropImage()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.serviceImage, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(context).load(service.getCropImage()).into(holder.serviceImage);
                }


            });
        }
        holder.holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();

                UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
                Map<String, String> params = new HashMap<String, String>();
                params.put("service_id", service.getId() + "");
                Call<ProviderModel> call = userInterface.getProvider(params);
                call.enqueue(new Callback<ProviderModel>() {
                    @Override
                    public void onResponse(@NonNull Call<ProviderModel> call, @NonNull Response<ProviderModel> response) {
                        pDialog.dismiss();
                        Intent intent = new Intent(context, ServiceProviderDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("data", Parcels.wrap(response.body()));
                        intent.putExtras(bundle);
                        context.startActivity(intent);

                    }

                    @Override
                    public void onFailure(@NonNull Call<ProviderModel> call, @NonNull Throwable t) {
                        pDialog.dismiss();
                        fallback();
                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View holder;
        ImageView serviceImage;
        TextView serviceName, serviceDetails;


        ViewHolder(View itemView) {
            super(itemView);
            holder = itemView;
            serviceImage = holder.findViewById(R.id.service_image);
            serviceName = holder.findViewById(R.id.servicec_name);
            serviceDetails = holder.findViewById(R.id.service_details);

        }
    }

    private void fallback() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Oops...");
        pDialog.setContentText("Something went wrong!");
        pDialog.show();

    }
}

