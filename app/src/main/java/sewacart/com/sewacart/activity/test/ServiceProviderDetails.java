package sewacart.com.sewacart.activity.test;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.adapter.ProfileViewPagerAdapter;
import sewacart.com.sewacart.finalpackage.activity.CartActivity;
import sewacart.com.sewacart.finalpackage.activity.FullImageActivity;
import sewacart.com.sewacart.finalpackage.activity.LoginActivity;
import sewacart.com.sewacart.finalpackage.activity.ProfileActivity;

public class ServiceProviderDetails extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.add_to_cart)
    Button addToCart;
    @BindView(R.id.service_provider_book_now)
    Button serviceProviderBookNow;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;

    private Toolbar toolbar;

    CollapsingToolbarLayout collapsingToolbarLayout = null;
    ViewPager mViewPager;
    ProfileViewPagerAdapter profileViewPagerAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_details);
        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceProviderDetails.super.onBackPressed();
            }
        });

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
        collapsingToolbarLayout.setTitle("Gaurav Man Shrestha");

        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.viewpager);
        mTabLayout.setupWithViewPager(mViewPager);

        //collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.profileExpandedAppBar);
        //collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        profileViewPagerAdapter = new ProfileViewPagerAdapter(getSupportFragmentManager(), 1, this);
        mViewPager.setAdapter(profileViewPagerAdapter);
        mViewPager.addOnPageChangeListener(this);


    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    @OnClick({R.id.add_to_cart, R.id.service_provider_book_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_to_cart:
                startActivity(new Intent(ServiceProviderDetails.this, CartActivity.class));
                break;
            case R.id.service_provider_book_now:
                startActivity(new Intent(ServiceProviderDetails.this, LoginActivity.class));
                break;
        }
    }

    @OnClick(R.id.profile_image)
    public void onViewClicked() {
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(profileImage, "fullImage");

        Intent chatIntent = new Intent(ServiceProviderDetails.this, FullImageActivity.class);
        ActivityOptions activityOptions = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            activityOptions = ActivityOptions.makeSceneTransitionAnimation(ServiceProviderDetails.this, pairs);
        }
        //TODO: Replace with real image data
        chatIntent.putExtra("imageUrl", "http://sewacart.com/beta/wp-content/uploads/2018/10/wallpaper-android-developer-beautiful-hd-android-robot-design-desktop-wallpapers-android-of-wallpaper-android-developer-1.jpg");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            startActivity(chatIntent, activityOptions.toBundle());
        } else {
            startActivity(chatIntent);
        }

    }
}
