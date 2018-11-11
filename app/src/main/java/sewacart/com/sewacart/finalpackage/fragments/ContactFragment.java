package sewacart.com.sewacart.finalpackage.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.activity.ServiceListingActivity;
import sewacart.com.sewacart.finalpackage.controller.ControllerPixels;
import sewacart.com.sewacart.finalpackage.model.ContactModel;
import sewacart.com.sewacart.finalpackage.model.OrderDetailsModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;


public class ContactFragment extends Fragment implements OnMapReadyCallback {

    Context context;
    @BindView(R.id.icon_phone)
    ImageView iconPhone;
    @BindView(R.id.phone_txt)
    TextView phoneTxt;
    @BindView(R.id.phone_wrapper)
    RelativeLayout phoneWrapper;
    @BindView(R.id.icon_address)
    ImageView iconAddress;
    @BindView(R.id.address_txt)
    TextView addressTxt;
    @BindView(R.id.address_wrapper)
    RelativeLayout addressWrapper;
    @BindView(R.id.icon_email)
    ImageView iconEmail;
    @BindView(R.id.email_txt)
    TextView emailTxt;
    @BindView(R.id.email_wrapper)
    RelativeLayout emailWrapper;
    @BindView(R.id.wrapper_layout)
    LinearLayout wrapperLayout;
    @BindView(R.id.wrapper_address)
    CardView wrapperAddress;
    @BindView(R.id.wrapper_map)
    CardView wrapperMap;
    @BindView(R.id.get_in_touch)
    TextView getInTouch;
    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_phone)
    EditText inputPhone;
    @BindView(R.id.input_message)
    EditText inputMessage;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.ripple)
    MaterialRippleLayout ripple;
    GoogleMap mMap;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private ProgressDialog progressBar;

    SweetAlertDialog pDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.bind(this, view);
        progressBar = new ProgressDialog(getContext());
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Sending Your Message");
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        getContact();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(ContactFragment.this);
    }

    private void getContact() {


        final ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();

        UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Call<ContactModel> call = userInterface.getContact();
        call.enqueue(new Callback<ContactModel>() {
            @Override
            public void onResponse(@NonNull Call<ContactModel> call, @NonNull Response<ContactModel> response) {
                progressBar.dismiss();
               if(response.body()!=null){
                   scrollView.setVisibility(View.VISIBLE);
                   addressTxt.setText(response.body().getAddress());
                   phoneTxt.setText(response.body().getPhone()+" / "+response.body().getMobile());
                   emailTxt.setText(response.body().getEmail());
               }else{
                   Toast.makeText(context, "Something went wrong !", Toast.LENGTH_SHORT);
               }

            }

            @Override
            public void onFailure(@NonNull Call<ContactModel> call, @NonNull Throwable t) {
                progressBar.dismiss();
                Toast.makeText(context, "Something went wrong !", Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);


        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);

        //TODO: add actual LOCATION OF SEWA CART
        LatLng latLng = new LatLng(27.7172, 85.3240);
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Sewa Cart"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
    }

    @OnClick(R.id.ripple)
    public void onViewClicked() {
        final String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String phone = inputPhone.getText().toString();
        String message = inputMessage.getText().toString();


        if (name.length() != 0 && email.length() != 0 && phone.length() != 0 && message.length() != 0) {
            if (ControllerPixels.isValidEmail(email)) {

                progressBar.show();

                UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("phone_number", phone);
                params.put("message", message);

                Call<Integer> call = userInterface.sendMail(params);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                        progressBar.dismiss();

                        if (response.body() == 1) {

                            inputEmail.setText("");
                            inputName.setText("");
                            inputMessage.setText("");
                            inputPhone.setText("");


                            Toast.makeText(getContext(), "Message sent Successfully... !", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Something Went Wrong !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                        progressBar.dismiss();
                        Toast.makeText(getContext(), "Something Went Wrong !", Toast.LENGTH_SHORT).show();

                    }
                });
            }else{
                Toast.makeText(getContext(), "Invlid Email !", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getContext(), "All Fields are required", Toast.LENGTH_SHORT).show();
        }

    }


}
