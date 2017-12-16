package com.zhenhui.apps.cnbeta.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.zhenhui.apps.cnbeta.R;
import com.zhenhui.apps.cnbeta.ext.view.BottomNavigationViewEx;
import com.zhenhui.apps.cnbeta.view.BaseFragment;
import com.zhenhui.apps.cnbeta.view.HomeFragment;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.navigation_view)
    BottomNavigationViewEx navigation;
    @BindView(R.id.activity_main_view_pager)
    ViewPager viewPager;

    private final List<Fragment> fragments = new LinkedList<>();

    private FragmentsAdapter fragmentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        HomeFragment homeFragment = new HomeFragment();
        fragments.add(homeFragment);

        BaseFragment fragment = new BaseFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", "2");
        fragment.setArguments(bundle);
        fragments.add(fragment);

        fragment = new BaseFragment();
        bundle = new Bundle();
        bundle.putString("title", "3");
        fragment.setArguments(bundle);
        fragments.add(fragment);

        fragmentsAdapter = new FragmentsAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(fragmentsAdapter);

        navigation.setupWithViewPager(viewPager);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    private static class FragmentsAdapter extends FragmentPagerAdapter {
        private List<Fragment> data;

        public FragmentsAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
            super(fragmentManager);
            this.data = fragments;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }
    }
}
