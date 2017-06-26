package com.vfconsulting.barbieri.parroquia;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbb on 25/06/2017.
 */

public class ScreenSlidePagerActivity extends FragmentActivity  {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    List<Fragment> fragmentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_slide);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        loadSlides(mPager);

        Log.e("tamaño de fragments",String.valueOf(fragmentList.size()));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT ,ViewGroup.LayoutParams.MATCH_PARENT);

    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private void loadSlides( ViewPager viewPager){

        Log.e("id_parroquia", String.valueOf(getIntent().getExtras().getInt("id_parroquia")));

        ScreenSlidePagerAdapter adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(newInstance("Lunes", getIntent().getExtras().getInt("id_parroquia")));
        adapter.addFragment(newInstance("Martes", getIntent().getExtras().getInt("id_parroquia")));
        adapter.addFragment(newInstance("Miercoles", getIntent().getExtras().getInt("id_parroquia")));
        adapter.addFragment(newInstance("Jueves", getIntent().getExtras().getInt("id_parroquia")));
        adapter.addFragment(newInstance("Viernes", getIntent().getExtras().getInt("id_parroquia")));
        adapter.addFragment(newInstance("Sabado", getIntent().getExtras().getInt("id_parroquia")));
        adapter.addFragment(newInstance("Domingo", getIntent().getExtras().getInt("id_parroquia")));

        viewPager.setAdapter(adapter);
    }

    private ScreenSlidePageFragment newInstance(String dia, int id_parroquia){
        final Bundle bundle = new Bundle();
        bundle.putString("dia",dia);
        bundle.putInt("id_parroquia",id_parroquia);
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Log.e("argumentos -->",bundle.toString());
        fragment.setArguments(bundle);
        return fragment;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ScreenSlidePageFragment();
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment){

            fragmentList.add(fragment);

        }
    }

}
