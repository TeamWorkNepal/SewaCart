package sewacart.com.sewacart.finalpackage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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

    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        homeFragment = new HomeFragment();
        categoryFragment = new CategoryFragment();
        contactFragment = new ContactFragment();

        toolbar = findViewById(R.id.toolbar_main_activity);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        //  actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeAsUpIndicator(R.drawable.icon_ham);

        setTitle("");
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custumBarLayout = layoutInflater.inflate(R.layout.cutom_toolbar, null);
        actionBar.setCustomView(custumBarLayout);
        hambuger = custumBarLayout.findViewById(R.id.hambuger);
        hambuger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        /* cartIcon = custumBarLayout.findViewById(R.id.my_cart);
         cartIcon.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, TestServiceProviderListingItem.class));
             }
         });*/
       /* hambuger = custumBarLayout.findViewById(R.id.hambuger);
        //  cartIcon.setVisibility(View.VISIBLE);
        hambuger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProviderDashboard.class));
            }
        });

*/


        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {


                        drawerLayout.closeDrawers();
                        switch (item.getItemId()) {

                            case R.id.dashboard:
                                startActivity(new Intent(MainActivity.this, ProviderDashboard.class));
                                return true;
                            case R.id.listing:
                                startActivity(new Intent(MainActivity.this, AddServiceActivty.class));
                                return true;
                            case R.id.myservices:

                                Intent intent = new Intent(MainActivity.this, ServiceListingActivity.class);
                                intent.putExtra("own", true);
                                startActivity(intent);
                                return true;

                            case R.id.profile:
                                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                                return true;
                        }
                        item.setChecked(false);

                        return false;


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);

        }
        return super.onOptionsItemSelected(item);
    }
}
