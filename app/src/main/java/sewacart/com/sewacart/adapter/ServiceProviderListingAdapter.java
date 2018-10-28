package sewacart.com.sewacart.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.activity.ServiceProviderDetailsActivity;
import sewacart.com.sewacart.finalpackage.model.ProviderModel;

public class ServiceProviderListingAdapter extends RecyclerView.Adapter<ServiceProviderListingAdapter.ViewHolder> {
    Context context;
    List<ProviderModel> providerModels;
    boolean own = false;

    public ServiceProviderListingAdapter(Context context, List<ProviderModel> providerModels, boolean own) {
        this.context = context;
        this.providerModels = providerModels;
        this.own = own;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_provide_listing, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ProviderModel providerModel = providerModels.get(position);

        holder.serviceProviderName.setText(providerModel.getTitle());
        holder.serviceProviderAddress.setText(providerModel.getRegion());
        holder.serviceProviderService.setText(providerModel.getCategories());
        if (providerModel.getOrganization().equals(1)) {
            holder.providerType.setText("Organization");
        } else {
            holder.providerType.setText("Individual");
        }
        holder.serviceProviderHour.setText(providerModel.getMinServiceHour());
        holder.serviceProviderExp.setText(providerModel.getExperience());
        holder.serviceProviderCost.setText("Rs. " + providerModel.getServiceCost() + " / " + providerModel.getCostInterval());
        if (!providerModel.getCropImage().isEmpty()) {
            Picasso.with(context).load(providerModel.getCropImage()).placeholder(R.drawable.default_user).networkPolicy(NetworkPolicy.OFFLINE).into(holder.serviceProviderImage, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(context).load(providerModel.getCropImage()).placeholder(R.drawable.default_user).into(holder.serviceProviderImage);
                }


            });
        }

        if (!own) {
            holder.holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ServiceProviderDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("data", Parcels.wrap(providerModel));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        } else {
            holder.belowWrapper.setVisibility(View.GONE);
            holder.belowWrapperDown.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return providerModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View holder;
        @BindView(R.id.service_provider_image)
        CircleImageView serviceProviderImage;
        @BindView(R.id.service_provider_name)
        TextView serviceProviderName;
        @BindView(R.id.service_provider_address)
        TextView serviceProviderAddress;
        @BindView(R.id.icon_person)
        ImageView iconPerson;
        @BindView(R.id.provider_type)
        TextView providerType;
        @BindView(R.id.myRatingBar)
        RatingBar myRatingBar;
        @BindView(R.id.wrapper_service_provider)
        RelativeLayout wrapperServiceProvider;
        @BindView(R.id.wrapper_info)
        LinearLayout wrapperInfo;
        @BindView(R.id.service_provider_details)
        Button serviceProviderDetails;

        @BindView(R.id.service_provider_service)
        TextView serviceProviderService;

        @BindView(R.id.service_provider_hour)
        TextView serviceProviderHour;

        @BindView(R.id.service_provider_exp)
        TextView serviceProviderExp;

        @BindView(R.id.service_provider_cost)
        TextView serviceProviderCost;

        @BindView(R.id.below_wrapper)
        LinearLayout belowWrapper;
        @BindView(R.id.below_down)
        View belowDown;
        @BindView(R.id.below_wrapper_down)
        LinearLayout belowWrapperDown;

        @BindView(R.id.edit_service)
        Button editService;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            holder = view;
        }
    }
}