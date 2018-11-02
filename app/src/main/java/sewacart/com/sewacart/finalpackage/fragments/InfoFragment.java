package sewacart.com.sewacart.finalpackage.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.activity.ServiceProviderDetailsActivity;

public class InfoFragment extends Fragment {

    Context context;
    @BindView(R.id.profile_associate)
    TextView profileAssociate;
    @BindView(R.id.profile_location)
    TextView profileLocation;
    @BindView(R.id.profile_categorye)
    TextView profileCategorye;
    @BindView(R.id.profile_experience)
    TextView profileExperience;
    @BindView(R.id.profile_mobile)
    TextView profileMobile;
    @BindView(R.id.profile_phone)
    TextView profilePhone;
    @BindView(R.id.profile_email)
    TextView profileEmail;
    @BindView(R.id.profile_hour)
    TextView profileHour;
    @BindView(R.id.profile_about)
    TextView profileAbout;
    Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, view);
        if (ServiceProviderDetailsActivity.providerModel.getOrganization() == 1) {
            profileAssociate.setText("Organization");
        } else {
            profileAssociate.setText("Individual");
        }
        profileLocation.setText(ServiceProviderDetailsActivity.providerModel.getRegion());
        profileCategorye.setText(ServiceProviderDetailsActivity.providerModel.getCategories());
        profileExperience.setText(ServiceProviderDetailsActivity.providerModel.getExperience()+" Yrs");
        profileMobile.setText(ServiceProviderDetailsActivity.providerModel.getMobile1());
        profilePhone.setText(ServiceProviderDetailsActivity.providerModel.getMobile2());
        profileEmail.setText(ServiceProviderDetailsActivity.providerModel.getEmail());
        profileHour.setText(ServiceProviderDetailsActivity.providerModel.getMinServiceHour());
        profileAbout.setText(ServiceProviderDetailsActivity.providerModel.getDescription());

        return view;
    }


}
