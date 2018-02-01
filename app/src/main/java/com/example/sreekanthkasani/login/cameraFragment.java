package com.example.sreekanthkasani.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class cameraFragment extends Fragment {

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        context = getActivity().getApplicationContext();
        if (((HomeActivity) getActivity()).checkPermissions(AndroidUtils.CAMERA_PERMISSION[0])) {
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 0);
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            //return back the photo
        }
    }
}
















