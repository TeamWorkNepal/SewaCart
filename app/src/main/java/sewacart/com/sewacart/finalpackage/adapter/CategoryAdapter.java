package sewacart.com.sewacart.finalpackage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.activity.MainActivity;
import sewacart.com.sewacart.finalpackage.activity.SubCategoryListingActivity;
import sewacart.com.sewacart.finalpackage.model.CategoryModel;

public class CategoryAdapter extends ArrayAdapter<CategoryModel> {
    Context context;
    int layoutId;
    List<CategoryModel> categoryModels;

    public CategoryAdapter(@NonNull Context context, int resource, List<CategoryModel> categoryModels) {
        super(context, resource, categoryModels);
        this.context = context;
        this.layoutId = resource;
        this.categoryModels = categoryModels;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;
        if (row == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutId, parent, false);
            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.servicec_name);
            holder.imageItem = (ImageView) row.findViewById(R.id.service_image);
            row.setTag(holder);

        } else {

            holder = (RecordHolder) row.getTag();

        }
        final CategoryModel categoryModel = categoryModels.get(position);
        holder.txtTitle.setText(categoryModel.getCategoryTitle());
        if (!categoryModel.getCategoryImage().isEmpty()) {
            final RecordHolder finalHolder = holder;
            Picasso.with(context).load(categoryModel.getCategoryImage()).placeholder(R.drawable.default_user).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageItem, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(context).load(categoryModel.getCategoryImage()).placeholder(R.drawable.default_user).into(finalHolder.imageItem);
                }


            });
        }
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SubCategoryListingActivity.class);
                intent.putExtra("data", "Sub Category");
                intent.putExtra("data1", categoryModel.getParent());
                context.startActivity(intent);
            }
        });

        return row;
    }

    static class RecordHolder {
        TextView txtTitle;
        ImageView imageItem;

    }
}
