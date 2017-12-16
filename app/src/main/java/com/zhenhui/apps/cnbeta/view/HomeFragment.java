package com.zhenhui.apps.cnbeta.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhenhui.apps.cnbeta.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.home_fragment_tab_layout)
    protected TabLayout mTabLayout;
    @BindView(R.id.home_fragment_view_pager)
    protected ViewPager mViewPager;

    private ContentFragmentAdapter contentFragmentAdapter;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        contentFragmentAdapter = new ContentFragmentAdapter(getChildFragmentManager());
        mViewPager.setAdapter(contentFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private class ContentFragmentAdapter extends FragmentStatePagerAdapter {

        public ContentFragmentAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = new BaseFragment();
            Bundle arguments = new Bundle();
            arguments.putString("title", "-- " + position);
            fragment.setArguments(arguments);
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return String.format("标题_%d", position);
        }
    }
}
