package com.vfconsulting.barbieri.parroquia;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.vfconsulting.barbieri.parroquia.Beans.HorarioBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbb on 25/06/2017.
 */

public class ScreenSlidePagerActivity extends FragmentActivity  {

    private static final int NUM_PAGES = 7;
    private ViewPager mPager;
    public List<HorarioBean> horarios = new ArrayList<>();
    private PagerAdapter mPagerAdapter;
    public int id_parroquia = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_slide);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        loadSlides(mPager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        //id_parroquia = getIntent().getExtras().getInt("id_parroquia");
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

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

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private void loadSlides( ViewPager viewPager){

        ScreenSlidePagerAdapter adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());

        adapter.addFragment(newInstance("Lunes", id_parroquia));
        adapter.addFragment(newInstance("Martes", id_parroquia));
        adapter.addFragment(newInstance("Miercoles", id_parroquia));
        adapter.addFragment(newInstance("Jueves", id_parroquia));
        adapter.addFragment(newInstance("Viernes", id_parroquia));
        adapter.addFragment(newInstance("Sabado", id_parroquia));
        adapter.addFragment(newInstance("Domingo", id_parroquia));

        viewPager.setAdapter(adapter);
    }

    private Fragment newInstance(String dia, int id_parroquia){
        Bundle bundle = new Bundle();
        bundle.putString("dia",dia);
        bundle.putInt("id_parroquia",id_parroquia);
        Fragment fragment = new Fragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();

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
