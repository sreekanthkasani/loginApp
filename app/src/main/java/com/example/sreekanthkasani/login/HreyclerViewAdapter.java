package com.example.sreekanthkasani.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by sreekanth kasani  ') on 12/2/2017.
 */

public class HreyclerViewAdapter extends RecyclerView.Adapter<HreyclerViewAdapter.LocationCardVH> {

    private Context mcontext;
    private ArrayList<String> directoryPaths = new ArrayList<String>();
    private RecyclerView myRecyclerView;
    private View progressOverlay;

    public HreyclerViewAdapter(Context mcontext, ArrayList<String> directoryPaths,RecyclerView recyclerView,View progressOverlay) {
        this.mcontext = mcontext;
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(mcontext));
        this.directoryPaths = directoryPaths;
        this.myRecyclerView = recyclerView;
        this.progressOverlay = progressOverlay;
    }

    @Override
    public LocationCardVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.location_cards,null);
        LocationCardVH locationCardVH = new LocationCardVH(view,mcontext,directoryPaths,myRecyclerView,progressOverlay);
        return locationCardVH;
    }

    @Override
    public void onBindViewHolder(LocationCardVH holder, int position) {
        String[] temp = (directoryPaths.get(position)).split("/");
        int n = temp.length;
        holder.textView.setText(temp[n-1]);
    }

    @Override
    public int getItemCount() {
        return directoryPaths.size();
    }

    public class LocationCardVH extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        Context Vcontext;
        RecyclerView myRecyclerView2;
        View prog;
        ArrayList<String> directoryPaths = new ArrayList<String>();
        public LocationCardVH(View itemView,Context context,ArrayList<String> directoryPaths,RecyclerView recyclerView,View prog) {
            super(itemView);
            this.myRecyclerView2 = recyclerView;
            this.directoryPaths = directoryPaths;
            this.Vcontext = context;
            this.prog = prog;
            itemView.setOnClickListener(this);
            textView = (TextView) itemView.findViewById(R.id.tvlocation);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String str = directoryPaths.get(position);
            GalleryFragment gf = new GalleryFragment();
            Toast.makeText(Vcontext,directoryPaths.get(position),Toast.LENGTH_LONG).show();
            gf.createGallery(str,myRecyclerView2,Vcontext,prog);
        }
    }
}
