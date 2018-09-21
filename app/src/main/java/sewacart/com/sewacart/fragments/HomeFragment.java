package sewacart.com.sewacart.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import sewacart.com.sewacart.R;
import sewacart.com.sewacart.adapter.ServiceAdapter;

public class HomeFragment extends Fragment {

    Context context;
    Spinner spinner;
    RecyclerView serviceRecylerView,serviceRecylerView_1,serviceRecylerView_2,serviceRecylerView_3, serviceRecylerView_4, serviceRecylerView_5,serviceRecylerView_6, serviceRecylerView_7;
    RecyclerView serviceRecylerView_8,serviceRecylerView_9,serviceRecylerView_10;
    ServiceAdapter serviceAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        spinner = view.findViewById(R.id.spinner);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager5 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager6 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager7 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager8 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager9 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager10 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);LinearLayoutManager layoutManager11 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);


        serviceRecylerView = view.findViewById(R.id.serviceRecylerView);
        serviceRecylerView.setLayoutManager(layoutManager);
        serviceRecylerView.setHasFixedSize(true);
        serviceAdapter = new ServiceAdapter(context);
        serviceRecylerView.setAdapter(serviceAdapter);


        serviceRecylerView_1 = view.findViewById(R.id.serviceRecylerView_1);
        serviceRecylerView_1.setLayoutManager(layoutManager1);
        serviceRecylerView_1.setHasFixedSize(true);
        serviceAdapter = new ServiceAdapter(context);
        serviceRecylerView_1.setAdapter(serviceAdapter);

        serviceRecylerView_2 = view.findViewById(R.id.serviceRecylerView_2);
        serviceRecylerView_2.setLayoutManager(layoutManager2);
        serviceRecylerView_2.setHasFixedSize(true);
        serviceAdapter = new ServiceAdapter(context);
        serviceRecylerView_2.setAdapter(serviceAdapter);

        serviceRecylerView_3 = view.findViewById(R.id.serviceRecylerView_3);
        serviceRecylerView_3.setLayoutManager(layoutManager3);
        serviceRecylerView_3.setHasFixedSize(true);
        serviceAdapter = new ServiceAdapter(context);
        serviceRecylerView_3.setAdapter(serviceAdapter);


        serviceRecylerView_4 = view.findViewById(R.id.serviceRecylerView_4);
        serviceRecylerView_4.setLayoutManager(layoutManager4);
        serviceRecylerView_4.setHasFixedSize(true);
        serviceAdapter = new ServiceAdapter(context);
        serviceRecylerView_4.setAdapter(serviceAdapter);


        serviceRecylerView_5 = view.findViewById(R.id.serviceRecylerView_5);
        serviceRecylerView_5.setLayoutManager(layoutManager5);
        serviceRecylerView_5.setHasFixedSize(true);
        serviceAdapter = new ServiceAdapter(context);
        serviceRecylerView_5.setAdapter(serviceAdapter);


        serviceRecylerView_6 = view.findViewById(R.id.serviceRecylerView_6);
        serviceRecylerView_6.setLayoutManager(layoutManager6);
        serviceRecylerView_6.setHasFixedSize(true);
        serviceAdapter = new ServiceAdapter(context);
        serviceRecylerView_6.setAdapter(serviceAdapter);



        serviceRecylerView_7 = view.findViewById(R.id.serviceRecylerView_7);
        serviceRecylerView_7.setLayoutManager(layoutManager7);
        serviceRecylerView_7.setHasFixedSize(true);
        serviceAdapter = new ServiceAdapter(context);
        serviceRecylerView_7.setAdapter(serviceAdapter);




        serviceRecylerView_7 = view.findViewById(R.id.serviceRecylerView_7);
        serviceRecylerView_7.setLayoutManager(layoutManager8);
        serviceRecylerView_7.setHasFixedSize(true);
        serviceAdapter = new ServiceAdapter(context);
        serviceRecylerView_7.setAdapter(serviceAdapter);


        serviceRecylerView_8 = view.findViewById(R.id.serviceRecylerView_8);
        serviceRecylerView_8.setLayoutManager(layoutManager9);
        serviceRecylerView_8.setHasFixedSize(true);
        serviceAdapter = new ServiceAdapter(context);
        serviceRecylerView_8.setAdapter(serviceAdapter);

        serviceRecylerView_9 = view.findViewById(R.id.serviceRecylerView_9);
        serviceRecylerView_9.setLayoutManager(layoutManager10);
        serviceRecylerView_9.setHasFixedSize(true);
        serviceAdapter = new ServiceAdapter(context);
        serviceRecylerView_9.setAdapter(serviceAdapter);



        serviceRecylerView_10 = view.findViewById(R.id.serviceRecylerView_10);
        serviceRecylerView_10.setLayoutManager(layoutManager11);
        serviceRecylerView_10.setHasFixedSize(true);
        serviceAdapter = new ServiceAdapter(context);
        serviceRecylerView_10.setAdapter(serviceAdapter);


        serviceRecylerView.setNestedScrollingEnabled(false);
        serviceRecylerView_1.setNestedScrollingEnabled(false);
        serviceRecylerView_2.setNestedScrollingEnabled(false);
        serviceRecylerView_3.setNestedScrollingEnabled(false);
        serviceRecylerView_4.setNestedScrollingEnabled(false);
        serviceRecylerView_5.setNestedScrollingEnabled(false);
        serviceRecylerView_6.setNestedScrollingEnabled(false);
        serviceRecylerView_7.setNestedScrollingEnabled(false);
        serviceRecylerView_8.setNestedScrollingEnabled(false);
        serviceRecylerView_9.setNestedScrollingEnabled(false);
        serviceRecylerView_10.setNestedScrollingEnabled(false);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        ArrayAdapter adapter = ArrayAdapter.createFromResource(context, R.array.addresses, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }


}
