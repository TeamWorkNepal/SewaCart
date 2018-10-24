package sewacart.com.sewacart.finalpackage.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import sewacart.com.sewacart.R;

public class FullImageActivity extends AppCompatActivity {
    PhotoView mPhotoDraweeView;
    private ImageView imageView;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        final String imageUrl = getIntent().getStringExtra("imageUrl");

        mPhotoDraweeView = (PhotoView) findViewById(R.id.full_image_id);
        Picasso.with(FullImageActivity.this).load(imageUrl).placeholder(R.drawable.default_user).networkPolicy(NetworkPolicy.OFFLINE).into(mPhotoDraweeView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(FullImageActivity.this).load(imageUrl).placeholder(R.drawable.default_user).into(mPhotoDraweeView);
            }
        });


        imageView = (ImageView) findViewById(R.id.goBack);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FullImageActivity.super.onBackPressed();

            }
        });
    }
}
