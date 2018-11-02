package sewacart.com.sewacart.finalpackage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.model.ReviewModel;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    List<ReviewModel> reviewModels;
    Context context;
    @BindView(R.id.reviewer_profile_image)
    CircleImageView reviewerProfileImage;
    @BindView(R.id.review_title)
    TextView reviewTitle;
    @BindView(R.id.review_rating)
    TextView reviewRating;
    @BindView(R.id.review_descp)
    TextView reviewDescp;

    public ReviewAdapter(List<ReviewModel> reviewModels, Context context) {
        this.reviewModels = reviewModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final ReviewModel reviewModel = reviewModels.get(i);
        viewHolder.reviewTitle.setText(reviewModel.getTitle());
        viewHolder.reviewDescp.setText(reviewModel.getReview());
        viewHolder.reviewRating.setText(reviewModel.getRating() + " / 5");

        if (!reviewModel.getImage().isEmpty()) {
            Picasso.with(context).load(reviewModel.getImage()).placeholder(R.drawable.default_user).networkPolicy(NetworkPolicy.OFFLINE).into(viewHolder.reviewerProfileImage, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(context).load(reviewModel.getImage()).placeholder(R.drawable.default_user).into(viewHolder.reviewerProfileImage);
                }


            });
        }
    }


    @Override
    public int getItemCount() {
        return reviewModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.reviewer_profile_image)
        CircleImageView reviewerProfileImage;
        @BindView(R.id.review_title)
        TextView reviewTitle;
        @BindView(R.id.review_rating)
        TextView reviewRating;
        @BindView(R.id.review_descp)
        TextView reviewDescp;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
