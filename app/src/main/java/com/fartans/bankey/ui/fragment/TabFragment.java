package com.fartans.bankey.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fartans.bankey.R;
import com.fartans.bankey.adapter.MainFragmentPagerAdapter;

/**
 * Created by anuj on 3/15/16.
 */
public class TabFragment extends Fragment {
public static TabLayout tabLayout;
public static ViewPager viewPager;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            /**
             *Inflate tab_layout and setup Views.
             */
            View allu =  inflater.inflate(R.layout.tab_layout,null);
            tabLayout = (TabLayout) allu.findViewById(R.id.tabs);
            viewPager = (ViewPager) allu.findViewById(R.id.viewpager);

            /**
             *Set an Apater for the View Pager
             */
            MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getFragmentManager());
            adapter.addFragment(new AccountFragment(), "Account");
            adapter.addFragment(new SpecialFragment(), "History");
            adapter.addFragment(new OtherFragment(), "Other");
            viewPager.setAdapter(adapter);

            /**
             * Now , this is a workaround ,
             * The setupWithViewPager dose't works without the runnable .
             * Maybe a Support Library Bug .
             */

            tabLayout.post(new Runnable() {
                @Override
                public void run() {
                    tabLayout.setupWithViewPager(viewPager);
                }
            });

            return allu;

        }
}