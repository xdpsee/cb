package com.zhenhui.apps.cnbeta.view;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;
import com.mob.ums.User;
import com.squareup.picasso.Picasso;
import com.zhenhui.apps.cnbeta.R;
import com.zhenhui.apps.cnbeta.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {

    @BindView(R.id.profile_settings)
    protected ImageView mSettingImageView;

    @BindView(R.id.profile_image)
    protected ImageView mProfileImageView;

    @BindView(R.id.profile_nickname)
    protected TextView mNicknameTextView;


    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mSettingImageView.setImageDrawable(new IconicsDrawable(getActivity())
                .icon(GoogleMaterial.Icon.gmd_settings)
                .color(Color.GRAY)
                .sizeDp(32)
                .paddingDp(6));

        if (!UMSSDK.amILogin()) {
            mProfileImageView.setImageDrawable(new IconicsDrawable(getActivity())
                    .icon(FontAwesome.Icon.faw_user)
                    .color(Color.GRAY)
                    .sizeDp(80));
        } else {
            UMSSDK.getLoginUser(new OperationCallback<User>(){
                @Override
                public void onSuccess(User user) {
                    try {
                        mNicknameTextView.setText(user.nickname.get());
                        String avatar = user.avatar.get()[0];
                        Picasso.with(getContext()).load(avatar).into(mProfileImageView);
                    } catch (Throwable t) {

                    }
                }

                @Override
                public void onFailed(Throwable throwable) {

                }

                @Override
                public void onCancel() {

                }
            });
        }

        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UMSSDK.amILogin()) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {

                }
            }
        });
    }
}


