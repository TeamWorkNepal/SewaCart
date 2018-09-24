package sewacart.com.sewacart.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.activity.test.ProviderDashboard;
import sewacart.com.sewacart.fragments.CategoryFragment;
import sewacart.com.sewacart.fragments.ContactFragment;
import sewacart.com.sewacart.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    Toolbar toolbar;

    HomeFragment homeFragment;
    CategoryFragment categoryFragment;
    ContactFragment contactFragment;
    ImageView cartIcon, hambuger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        homeFragment = new HomeFragment();
        categoryFragment = new CategoryFragment();
        contactFragment = new ContactFragment();

        toolbar = (Toolbar) findViewById(R.id.toolbar_main_activity);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        setTitle("");
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custumBarLayout = layoutInflater.inflate(R.layout.cutom_toolbar, null);
        actionBar.setCustomView(custumBarLayout);
        //   cartIcon = custumBarLayout.findViewById(R.id.my_cart);
        hambuger = custumBarLayout.findViewById(R.id.hambuger);
        //  cartIcon.setVisibility(View.VISIBLE);
        hambuger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, ProviderDashboard.class));
            }
        });


        bottomNavigation.inflateMenu(R.menu.bottom_navigation_main);
        bottomNavigation.setItemBackgroundResource(R.color.colorPrimary);
        bottomNavigation.setItemTextColor(ContextCompat.getColorStateList(bottomNavigation.getContext(), R.color.nav_item_colors));
        bottomNavigation.setItemIconTintList(ContextCompat.getColorStateList(bottomNavigation.getContext(), R.color.nav_item_colors));
        bottomNavigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        setFragment1(homeFragment);
        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.icon_home:

                                setFragment1(homeFragment);
                                break;
                            case R.id.icon_category:
                                setFragment1(categoryFragment);

                                break;
                            case R.id.icon_contact:
                                setFragment1(contactFragment);

                        }
                        return true;
                    }
                });


    }

    public void setFragment1(Fragment fragment) {

        FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction3.replace(R.id.mainFramelayout, fragment);
        fragmentTransaction3.commit();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}
