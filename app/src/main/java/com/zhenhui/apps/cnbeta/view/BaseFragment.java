package com.zhenhui.apps.cnbeta.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhenhui.apps.cnbeta.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseFragment extends Fragment {

    private String title;

    @BindView(R.id.text_view_title)
    TextView titleTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_base, null);
        ButterKnife.bind(this, view);

        titleTextView.setText(title);

        return view;
    }

}
