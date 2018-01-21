package com.zhenhui.apps.cnbeta.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mob.MobSDK;
import com.mob.ums.UMSSDK;
import com.zhenhui.apps.cnbeta.R;
import com.zhenhui.apps.cnbeta.ext.view.BottomNavigationViewEx;
import com.zhenhui.apps.cnbeta.view.BaseFragment;
import com.zhenhui.apps.cnbeta.view.HomeFragment;
import com.zhenhui.apps.cnbeta.view.MyFragment;

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

        MobSDK.init(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(android.R.id.content).setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

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

        Fragment myFragment = new MyFragment();
        bundle = new Bundle();
        bundle.putString("title", "4");
        myFragment.setArguments(bundle);
        fragments.add(myFragment);

        fragmentsAdapter = new FragmentsAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(fragmentsAdapter);

        navigation.setupWithViewPager(viewPager, true);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        navigation.setSoundEffectsEnabled(true);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);

        initNavigationIcons();

        if (!UMSSDK.amILogin()) {

        }

    }

    private void initNavigationIcons() {
        final int size = navigation.getMenu().size();
        for (int i = 0; i < size; ++i) {
            MenuItem menuItem = navigation.getMenu().getItem(i);
            IconicsDrawable iconicsDrawable = null;
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    iconicsDrawable = new IconicsDrawable(this)
                            .icon(FontAwesome.Icon.faw_home)
                            .paddingDp(2)
                            .sizeDp(24);
                    break;
                case R.id.navigation_dashboard:
                    iconicsDrawable = new IconicsDrawable(this)
                            .icon(FontAwesome.Icon.faw_youtube_play)
                            .paddingDp(2)
                            .sizeDp(24);
                    break;
                case R.id.navigation_notifications:
                    iconicsDrawable = new IconicsDrawable(this)
                            .icon(FontAwesome.Icon.faw_grav)
                            .paddingDp(2)
                            .sizeDp(24);
                    break;
                case R.id.navigation_my:
                    iconicsDrawable = new IconicsDrawable(this)
                            .icon(FontAwesome.Icon.faw_user)
                            .paddingDp(2)
                            .sizeDp(24);
                    break;
            }

            if (iconicsDrawable != null) {
                menuItem.setIcon(iconicsDrawable);
            }
        }


    }


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
