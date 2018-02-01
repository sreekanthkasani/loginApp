package com.example.sreekanthkasani.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by sreekanth kasani  ') on 12/2/2017.
 */

public class GalleryFragment extends Fragment {

    private ImageView imageView;
    private RecyclerView hRecyclerView,vRecyclerView;
    private ArrayList<String> directories;
    private Context mcontext;
    private View progressOverlay;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        directories = new ArrayList<>();
        UniversalImageLoader uil = new UniversalImageLoader(getActivity());
        mcontext = getActivity();
        View view =  inflater.inflate(R.layout.fragment_gallery, container, false);
        hRecyclerView = (RecyclerView) view.findViewById(R.id.location);
        vRecyclerView = (RecyclerView) view.findViewById(R.id.gallery);
        progressOverlay = view.findViewById(R.id.progress_overlay);
        imageView = (ImageView) view.findViewById(R.id.back_btn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        init();
        return  view;
    }

    private void init() {
        if(AndroidUtils.getDirectoryPaths(AndroidUtils.PICTURES)!=null){
            directories = AndroidUtils.getDirectoryPaths(AndroidUtils.PICTURES);
        }
        directories.add(AndroidUtils.CAMERA);
        HreyclerViewAdapter adapter = new HreyclerViewAdapter(mcontext,directories,vRecyclerView,progressOverlay);
        hRecyclerView.setLayoutManager(new LinearLayoutManager(mcontext, LinearLayoutManager.HORIZONTAL, true));
        hRecyclerView.setAdapter(adapter);
    }

    public void createGallery(String selectedDirectory,RecyclerView aRecyclerView,Context context,View prog) {
        View progressOverlay=prog;
        RecyclerView mRecyclerView =aRecyclerView;
        Context ctx = context;
        final ArrayList<String> imageUrls = AndroidUtils.getFilePaths(selectedDirectory);
        vRecyclerViewAdapter adapter = new vRecyclerViewAdapter(ctx,imageUrls,progressOverlay);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getActivity(),"the Gallery_Fragment \n have been killed and you will \n not be able to use any of it refferences further",Toast.LENGTH_LONG).show();
    }
}
