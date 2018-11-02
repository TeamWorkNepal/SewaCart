package sewacart.com.sewacart.finalpackage.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.controller.SharedPreferenceController;
import sewacart.com.sewacart.finalpackage.fragments.CategoryFragment;
import sewacart.com.sewacart.finalpackage.fragments.ContactFragment;
import sewacart.com.sewacart.finalpackage.fragments.HomeFragment;
import sewacart.com.sewacart.finalpackage.model.UserModel;

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
    TextView header_name, header_title;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //TODO: Make sure about adminster part and remove all the action part
        Menu nav_Menu = navView.getMenu();

        View header = navView.getHeaderView(0);
        header_name = (TextView) header.findViewById(R.id.header_name);
        header_title = (TextView) header.findViewById(R.id.header_title);
        circleImageView = (CircleImageView) header.findViewById(R.id.header_profile_image);

        if (SharedPreferenceController.getLoginLog(MainActivity.this)) {

            nav_Menu.findItem(R.id.login).setVisible(false);
            final UserModel.UserDetails userDetails = SharedPreferenceController.getUserDetails(MainActivity.this);
            header_name.setText(userDetails.getName());
            header_title.setText("- " + userDetails.getRole());


            if (!userDetails.getUserPhoto().isEmpty()) {
                Picasso.with(MainActivity.this).load(userDetails.getUserPhoto()).placeholder(R.drawable.default_user).networkPolicy(NetworkPolicy.OFFLINE).into(circleImageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(MainActivity.this).load(userDetails.getUserPhoto()).placeholder(R.drawable.default_user).into(circleImageView);
                    }


                });
            }


            if (userDetails.getRole().equalsIgnoreCase("administrator")) {

               // nav_Menu.findItem(R.id.myservices).setVisible(false);
               // nav_Menu.findItem(R.id.purchase).setVisible(false);
               // nav_Menu.findItem(R.id.listing).setVisible(false);
              //  nav_Menu.findItem(R.id.dashboard).setVisible(false);
               // nav_Menu.findItem(R.id.profile).setVisible(false);

            } else if (userDetails.getRole().equalsIgnoreCase("customer")) {

                nav_Menu.findItem(R.id.myservices).setVisible(false);
                nav_Menu.findItem(R.id.purchase).setVisible(false);
                nav_Menu.findItem(R.id.listing).setVisible(false);

            } else if (userDetails.getRole().equalsIgnoreCase("editor")) {


            }
        } else {
            nav_Menu.findItem(R.id.logout).setVisible(false);
            nav_Menu.findItem(R.id.myservices).setVisible(false);
            nav_Menu.findItem(R.id.purchase).setVisible(false);
            nav_Menu.findItem(R.id.listing).setVisible(false);
            nav_Menu.findItem(R.id.dashboard).setVisible(false);
            nav_Menu.findItem(R.id.profile).setVisible(false);
        }


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

        cartIcon = custumBarLayout.findViewById(R.id.my_cart);
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreferenceController.getLoginLog(MainActivity.this)) {
                    startActivity(new Intent(MainActivity.this, ViewCartActivity.class));
                } else {
                    final SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
                    pDialog.setTitleText("Hm...");
                    pDialog.setCancelText("Cancel");
                    pDialog.setConfirmText("Log In");
                    pDialog.setContentText("Looks like you are not logged in !");
                    pDialog.show();
                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    });

                }


            }
        });


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
                                if (!SharedPreferenceController.getLoginLog(MainActivity.this)) {
                                    fallback();
                                    return true;
                                } else {
                                    if (SharedPreferenceController.getUserDetails(MainActivity.this).getRole().equalsIgnoreCase("customer")) {
                                        startActivity(new Intent(MainActivity.this, CustomerDashboard.class));
                                        return true;
                                    } else {
                                        startActivity(new Intent(MainActivity.this, ProviderDashboard.class));
                                        return true;
                                    }

                                }


                            case R.id.listing:
                                if (!SharedPreferenceController.getLoginLog(MainActivity.this)) {
                                    fallback();
                                    return true;
                                } else {
                                    startActivity(new Intent(MainActivity.this, AddServiceActivty.class));
                                    return true;
                                }


                            case R.id.myservices:
                                if (!SharedPreferenceController.getLoginLog(MainActivity.this)) {
                                    fallback();
                                    return true;
                                } else {


                                    Intent intent = new Intent(MainActivity.this, ServiceListingActivity.class);
                                    intent.putExtra("own", true);
                                    startActivity(intent);
                                    return true;
                                }


                            case R.id.profile:
                                if (!SharedPreferenceController.getLoginLog(MainActivity.this)) {
                                    fallback();
                                    return true;
                                } else {
                                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                                    return true;
                                }


                            case R.id.purchase:
                                if (!SharedPreferenceController.getLoginLog(MainActivity.this)) {
                                    fallback();
                                    return true;
                                } else {
                                    startActivity(new Intent(MainActivity.this, PurchaseActivity.class));
                                    return true;
                                }


                            case R.id.logout:
                                if (!SharedPreferenceController.getLoginLog(MainActivity.this)) {
                                    fallback();
                                    return true;
                                } else {
                                    SharedPreferenceController.clearSharedPrefernce(MainActivity.this);
                                    final SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                    pDialog.setTitleText("Loading");
                                    pDialog.setContentText("Logging you out... please wait !");
                                    pDialog.setCancelable(false);
                                    pDialog.show();

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            pDialog.dismiss();
                                            Toast.makeText(MainActivity.this, "Logged Out Successfully !", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                                            finish();

                                        }
                                    }, 2000);

                                    return true;
                                }
                            case R.id.login:
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);

        }
        return super.onOptionsItemSelected(item);
    }

    public void fallback() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText("Hm...");
        pDialog.setCancelText("Cancel");
        pDialog.setConfirmText("Log In");
        pDialog.setContentText("Looks like you are not logged in !");
        pDialog.show();
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                sweetAlertDialog.dismissWithAnimation();
            }
        });
    }
}
