package sewacart.com.sewacart.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import sewacart.com.sewacart.R;
import sewacart.com.sewacart.activity.ServiceListingActivity;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    Context context;

    public  ServiceAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_layout, parent, false);
        return new ServiceAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceAdapter.ViewHolder holder, int position) {
        holder.holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ServiceListingActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
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