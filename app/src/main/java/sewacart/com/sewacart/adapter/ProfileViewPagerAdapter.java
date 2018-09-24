package sewacart.com.sewacart.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import sewacart.com.sewacart.fragments.CategoryFragment;
import sewacart.com.sewacart.fragments.ReviewFragment;


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
        if(position==2){
            return new ReviewFragment();
        }else{
            return new CategoryFragment();
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
