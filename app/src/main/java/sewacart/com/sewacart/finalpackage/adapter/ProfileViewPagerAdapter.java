package sewacart.com.sewacart.finalpackage.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import sewacart.com.sewacart.finalpackage.fragments.BriefFragment;
import sewacart.com.sewacart.finalpackage.fragments.InfoFragment;
import sewacart.com.sewacart.finalpackage.fragments.ReviewFragment;


public class ProfileViewPagerAdapter extends FragmentPagerAdapter {
    int size = 0;
    Context context;



    public ProfileViewPagerAdapter(FragmentManager fm, int size, Context context) {
        super(fm);
        this.size = size;
        this.context = context;


    }

    @Override
    public Fragment getItem(int position) {

        if(position==0){
            return new BriefFragment();
        }else if(position==1){
            return new InfoFragment();
        }else{
            return new ReviewFragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {



            case 0:

                return "Brief";
            case 1:

                return "Info";
            case 2:

                return "Reviews";
            default:
                return null;
        }
    }

}
