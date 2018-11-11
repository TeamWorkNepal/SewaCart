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
import sewacart.com.sewacart.finalpackage.activity.SubCategoryListingActivity;
import sewacart.com.sewacart.finalpackage.model.CategoryModel;
import sewacart.com.sewacart.finalpackage.model.HomeModel;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    List<CategoryModel> categoryModels;
    boolean isSub = false;

    public CategoryAdapter(Context context, List<CategoryModel> categoryModels, boolean isSub) {
        this.context = context;
        this.categoryModels = categoryModels;
        this.isSub = isSub;
    }


    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_single, parent, false);
        return new CategoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryAdapter.ViewHolder holder, int position) {
        final CategoryModel category = categoryModels.get(position);
        holder.categoryName.setText(category.getCategoryTitle());
        //  final String load = "http://sewacart.com/beta/wp-content/uploads/2018/11/wallpaper-android-developer-beautiful-hd-android-robot-design-desktop-wallpapers-android-of-wallpaper-android-developer-5.jpg";
        if (!category.getCategoryImage().isEmpty()) {
            Picasso.with(context).load(category.getCategoryImage()).placeholder(R.drawable.default_img).networkPolicy(NetworkPolicy.OFFLINE).into(holder.categoryImage, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(context).load(category.getCategoryImage()).placeholder(R.drawable.default_img).into(holder.categoryImage);
                }


            });
        }
        if (!isSub) {
            holder.holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, SubCategoryListingActivity.class);
                    intent.putExtra("data", category.getCategoryTitle());
                    intent.putExtra("data1", category.getCategoryId());
                    context.startActivity(intent);

                }
            });
        } else {
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

    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View holder;
        ImageView categoryImage;
        TextView categoryName;


        ViewHolder(View itemView) {
            super(itemView);
            holder = itemView;
            categoryImage = holder.findViewById(R.id.category_image);
            categoryName = holder.findViewById(R.id.category_name);

        }
    }
}