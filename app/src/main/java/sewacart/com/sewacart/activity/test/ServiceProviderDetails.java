package sewacart.com.sewacart.activity.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import sewacart.com.sewacart.R;
import sewacart.com.sewacart.adapter.ProfileViewPagerAdapter;

public class ServiceProviderDetails extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout = null;
    ViewPager mViewPager;
    ProfileViewPagerAdapter profileViewPagerAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
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
}
