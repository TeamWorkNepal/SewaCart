package sewacart.com.sewacart.finalpackage.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.activity.ServiceListingActivity;
import sewacart.com.sewacart.finalpackage.model.HomeModel;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    Context context;
    List<HomeModel.Category> categories;

    public ServiceAdapter(Context context, List<HomeModel.Category> categories) {
        this.context = context;
        this.categories = categories;
    }


    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ServiceAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceAdapter.ViewHolder holder, int position) {
        final HomeModel.Category category = categories.get(position);
        holder.serviceName.setText(category.getCategoryTitle());
        if (!category.getCategoryImage().isEmpty()) {
            Picasso.with(context).load(category.getCategoryImage()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.serviceImage, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(context).load(category.getCategoryImage()).into(holder.serviceImage);
                }


            });
        }
        holder.holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ServiceListingActivity.class);
                intent.putExtra("category_id", category.getCategoryId());
                intent.putExtra("own", false);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
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
}