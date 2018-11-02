package sewacart.com.sewacart.finalpackage.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.balysv.materialripple.MaterialRippleLayout;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.controller.SharedPreferenceController;
import sewacart.com.sewacart.finalpackage.model.ParentModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class AddServiceActivty extends AppCompatActivity {
    Toolbar toolbar;
    @BindView(R.id.listing_title)
    EditText listingTitle;
    @BindView(R.id.category)
    Spinner category;
    @BindView(R.id.sub_cateogry)
    EditText sub_cateogry;
    @BindView(R.id.address)
    Spinner address;
    @BindView(R.id.sub_address)
    EditText subAddress;
    @BindView(R.id.add_address)
    EditText addAddress;
    @BindView(R.id.provider_type)
    Spinner providerType;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.mobile_alter)
    EditText mobileAlter;
    @BindView(R.id.service_cost)
    EditText serviceCost;
    @BindView(R.id.service_hour)
    EditText serviceHour;
    @BindView(R.id.exp)
    EditText exp;
    @BindView(R.id.addservice_rpl)
    MaterialRippleLayout addserviceRpl;

    final ArrayList<CharSequence> selectedColours = new ArrayList<CharSequence>();
    final ArrayList<Integer> selectedColoursId = new ArrayList<Integer>();
    boolean[] checkedColours;

    File showImage = null;
    final ArrayList<CharSequence> selectedColoursAddress = new ArrayList<CharSequence>();
    final ArrayList<Integer> selectedColoursAddressId = new ArrayList<Integer>();
    boolean[] checkedColoursAddress;


    @BindView(R.id.upload_image)
    ImageView uploadImage;

    String parentRegion = "0";
    String parentCategory = "0";
    @BindView(R.id.listing_email)
    EditText listingEmail;
    @BindView(R.id.cost_interval)
    Spinner costInterval;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbar_main_activity);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddServiceActivty.super.onBackPressed();
            }
        });

        getParents();
    }

    private void getParents() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Call<ParentModel> call = userInterface.getParents();
        call.enqueue(new Callback<ParentModel>() {
            @Override
            public void onResponse(@NonNull Call<ParentModel> call, @NonNull final Response<ParentModel> response) {
                pDialog.dismiss();
                final List<String> addressList = new ArrayList<>();
                final List<String> categoryList = new ArrayList<>();

                for (int i = 0; i < response.body().getRegion().size(); i++) {
                    addressList.add(response.body().getRegion().get(i).getRegionTitle());
                }
                for (int i = 0; i < response.body().getCategory().size(); i++) {
                    categoryList.add(response.body().getCategory().get(i).getCatTitle());
                }

                ArrayAdapter<String> addresses = new ArrayAdapter<String>(AddServiceActivty.this, android.R.layout.simple_spinner_item, addressList);
                ArrayAdapter<String> categories = new ArrayAdapter<String>(AddServiceActivty.this, android.R.layout.simple_spinner_item, categoryList);

                addresses.setDropDownViewResource(R.layout.spinner_dropdown_item);
                categories.setDropDownViewResource(R.layout.spinner_dropdown_item);

                category.setAdapter(categories);
                address.setAdapter(addresses);

                address.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        subAddress.setEnabled(false);
                        subAddress.setText("");
                        parentRegion = response.body().getRegion().get(position).getRegionId() + "";
                        getSubRegion(response.body().getRegion().get(position).getRegionId() + "");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sub_cateogry.setEnabled(false);
                        sub_cateogry.setText("");
                        parentCategory = response.body().getCategory().get(position).getCatId() + "";
                        getSubCategory(response.body().getCategory().get(position).getCatId() + "");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call<ParentModel> call, @NonNull Throwable t) {
                pDialog.dismiss();
                fallback();
            }
        });
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    private void uploadImage() {
        ImagePicker.create(AddServiceActivty.this)
                .folderMode(true)
                .single()
                .start();
    }


    private void fallback() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(AddServiceActivty.this, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Oops...");
        pDialog.setContentText("Something went wrong!");
        pDialog.show();
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(new Intent(AddServiceActivty.this, MainActivity.class));
                finish();
            }
        });
    }

    private void getSubCategory(final String s) {
        UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put("parent_id", s);
        Call<List<ParentModel.Category>> call = userInterface.getChildCategory(params);
        call.enqueue(new Callback<List<ParentModel.Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<ParentModel.Category>> call, @NonNull final Response<List<ParentModel.Category>> response) {

              /*  List<String> addressList = new ArrayList<>();
                List<String> categoryList = new ArrayList<>();

                for (int i = 0; i < response.body().size(); i++) {
                    categoryList.add(response.body().get(i).getCatTitle());
                }

                ArrayAdapter<String> subCategoryAdapter = new ArrayAdapter<String>(AddServiceActivty.this, android.R.layout.simple_spinner_item, categoryList);
                subCategoryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                subCategory.setAdapter(subCategoryAdapter);
*/
                sub_cateogry.setEnabled(true);
                sub_cateogry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        final CharSequence[] colours = new CharSequence[response.body().size()];
                        final Integer[] coloursId = new Integer[response.body().size()];

                        sub_cateogry.setText("");

                        for (int i = 0; i < response.body().size(); i++) {
                            colours[i] = response.body().get(i).getCatTitle();
                            coloursId[i] = response.body().get(i).getCatId();
                        }
                        checkedColours = new boolean[colours.length];

                        int count = colours.length;
                        selectedColours.clear();
                        for (int i = 0; i < count; i++)
                            checkedColours[i] = selectedColours.contains(colours[i]);

                        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {

                            @Override

                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    selectedColours.add(colours[which]);
                                    selectedColoursId.add(coloursId[which]);
                                } else {
                                    selectedColours.remove(colours[which]);
                                    selectedColoursId.remove(coloursId[which]);
                                }


                                onChangeSelectedColours();

                            }

                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(AddServiceActivty.this);

                        builder.setTitle("Select Sub Categories");
                        builder.setCancelable(false);
                        //    builder.setPositiveButtonIcon(getResources().getDrawable(R.drawable.icon_done));
                        builder.setMultiChoiceItems(colours, checkedColours, coloursDialogListener);
                        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();

                        dialog.show();
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call<List<ParentModel.Category>> call, @NonNull Throwable t) {

            }
        });
    }


    private void getSubRegion(String s) {


        UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put("parent_id", s);
        Call<List<ParentModel.Region>> call = userInterface.getChildRegion(params);
        call.enqueue(new Callback<List<ParentModel.Region>>() {
            @Override
            public void onResponse(@NonNull Call<List<ParentModel.Region>> call, @NonNull final Response<List<ParentModel.Region>> response) {

             /*   List<String> addressList = new ArrayList<>();
                List<String> categoryList = new ArrayList<>();

                for (int i = 0; i < response.body().size(); i++) {
                    categoryList.add(response.body().get(i).getRegionTitle());
                }

                ArrayAdapter<String> subRegionAdapter = new ArrayAdapter<String>(AddServiceActivty.this, android.R.layout.simple_spinner_item, categoryList);
                subRegionAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                subAddress.setAdapter(subRegionAdapter);*/
                subAddress.setEnabled(true);
                subAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        final CharSequence[] colours = new CharSequence[response.body().size()];
                        final Integer[] coloursId = new Integer[response.body().size()];
                        subAddress.setText("");

                        for (int i = 0; i < response.body().size(); i++) {
                            colours[i] = response.body().get(i).getRegionTitle();
                            coloursId[i] = response.body().get(i).getRegionId();
                        }
                        checkedColoursAddress = new boolean[colours.length];

                        int count = colours.length;
                        selectedColoursAddress.clear();

                        for (int i = 0; i < count; i++)
                            checkedColoursAddress[i] = selectedColoursAddress.contains(colours[i]);

                        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {

                            @Override

                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    selectedColoursAddress.add(colours[which]);
                                    selectedColoursAddressId.add(coloursId[which]);
                                } else {
                                    selectedColoursAddress.remove(colours[which]);
                                    selectedColoursAddressId.remove(coloursId[which]);
                                }


                                onChangeSelectedColoursAddress();

                            }

                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(AddServiceActivty.this);

                        builder.setTitle("Select Area");
                        builder.setCancelable(false);
                        //    builder.setPositiveButtonIcon(getResources().getDrawable(R.drawable.icon_done));
                        builder.setMultiChoiceItems(colours, checkedColoursAddress, coloursDialogListener);
                        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();

                        dialog.show();
                    }
                });

            }

            @Override
            public void onFailure(@NonNull Call<List<ParentModel.Region>> call, @NonNull Throwable t) {

            }
        });
    }

    private void onChangeSelectedColoursAddress() {
        StringBuilder stringBuilder = new StringBuilder();

        for (CharSequence colour : selectedColoursAddress)
            stringBuilder.append(colour + ",");

        subAddress.setText(stringBuilder.toString());
    }


    protected void onChangeSelectedColours() {

        StringBuilder stringBuilder = new StringBuilder();

        for (CharSequence colour : selectedColours)
            stringBuilder.append(colour + ",");

        sub_cateogry.setText(stringBuilder.toString());

    }


    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {

            Image image = ImagePicker.getFirstImageOrNull(data);

            try {
                File compressedImageFile = new Compressor(AddServiceActivty.this)
                        .setQuality(50)
                        .compressToFile(new File(image.getPath()));

                String updoadImageString = compressedImageFile.toString();
                showImage = new File(image.getPath());

                if (!image.toString().isEmpty()) {
                    Picasso.with(AddServiceActivty.this).load(showImage).into(uploadImage);
                }


            } catch (IOException e) {
                e.printStackTrace();
                fallback();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.addservice_rpl)
    public void onViewClicked() {

        String title = listingTitle.getText().toString();
        String content = description.getText().toString();
        String author = SharedPreferenceController.getUserDetails(AddServiceActivty.this).getId();
        String full_address_secondary = addAddress.getText().toString();
        String email = listingEmail.getText().toString();
        String mPhone = phone.getText().toString();
        String mMobile1 = mobile.getText().toString();
        String mMobile2 = mobileAlter.getText().toString();
        String mServiceCost = serviceCost.getText().toString();
        String mCostInterval = costInterval.getSelectedItem().toString();
        String mServiceHour = serviceHour.getText().toString();
        String mExp = exp.getText().toString();


        final MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("action", "add_listing");
        builder.addFormDataPart("title", title);
        builder.addFormDataPart("content", content);
        builder.addFormDataPart("author", SharedPreferenceController.getUserDetails(AddServiceActivty.this).getId()+"");
        builder.addFormDataPart("service-category[]", parentCategory);

        for (int i = 0; i < selectedColours.size(); i++) {
            builder.addFormDataPart("sub_cat[]", selectedColoursId.get(i) + "");

        }


        builder.addFormDataPart("region[]", parentRegion);

        for (int i = 0; i < selectedColoursAddress.size(); i++) {
            builder.addFormDataPart("city[]", selectedColoursAddressId.get(i) + "");

        }

        builder.addFormDataPart("full_address_secondary", full_address_secondary);
        builder.addFormDataPart("email", email);
        builder.addFormDataPart("phone_no", mPhone);
        builder.addFormDataPart("mobile_1", mMobile1);
        builder.addFormDataPart("mobile_2", mMobile2);
        if (providerType.getSelectedItem().toString().equals("Organization")) {
            builder.addFormDataPart("organization", 1 + "");
        } else {
            builder.addFormDataPart("organization", 0 + "");
        }

        builder.addFormDataPart("service_cost", mServiceCost);
        builder.addFormDataPart("cost_interval", mCostInterval);
        builder.addFormDataPart("min_service_hour", mServiceHour);
        builder.addFormDataPart("experience", mExp);
        builder.addFormDataPart("google_map_script", "data");
        builder.addFormDataPart("show_in_homepage", "0");

        if (showImage != null)
            builder.addFormDataPart("featuredimage", showImage.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), showImage));

        final MultipartBody requestBody = builder.build();
        UserInterface postInterface = ApiClient.getApiClient().create(UserInterface.class);
        Call<Object> call = postInterface.addService(requestBody);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                fallback();
            }
        });
    }
}
