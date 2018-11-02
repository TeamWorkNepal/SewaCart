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
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.activity.ServiceProviderDetailsActivity;

public class BriefFragment extends Fragment {

    Context context;
    @BindView(R.id.profile_contact)
    TextView profileContact;
    @BindView(R.id.profile_experience)
    TextView profileExperience;
    @BindView(R.id.profile_service)
    TextView profileService;
    @BindView(R.id.profile_address)
    TextView profileAddress;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brief, container, false);
        ButterKnife.bind(this, view);
        profileContact.setText(ServiceProviderDetailsActivity.providerModel.getMobile1()+" , "+ ServiceProviderDetailsActivity.providerModel.getMobile2());
        profileExperience.setText(ServiceProviderDetailsActivity.providerModel.getExperience()+" Yrs");
        profileAddress.setText(ServiceProviderDetailsActivity.providerModel.getFullAddressSecondary());
        profileService.setText(ServiceProviderDetailsActivity.providerModel.getCategories());
        return view;
    }


}
