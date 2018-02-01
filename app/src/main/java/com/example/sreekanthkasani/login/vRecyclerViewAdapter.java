package com.example.sreekanthkasani.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sreekanth kasani  ') on 12/2/2017.
 */

public class vRecyclerViewAdapter extends RecyclerView.Adapter<vRecyclerViewAdapter.ImageViewHolder> {


    private Context mcontext;
    private ArrayList<String> filePaths;
    private UniversalImageLoader uil;
    private View progressOverlay;

    public vRecyclerViewAdapter(Context mcontext, ArrayList<String> filePaths,View progressOverlay) {
        this.mcontext = mcontext;
        this.filePaths = filePaths;
        this.progressOverlay = progressOverlay;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.staggered_grid,null);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view,mcontext,filePaths);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        final String pathToFile = filePaths.get(position);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("file:/" + pathToFile, holder.imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if(holder.loader !=null){
                    AndroidUtils.animateView(holder.loader, View.VISIBLE, 0.4f, 200);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if(holder.loader !=null){
                    AndroidUtils.animateView(holder.loader, View.GONE, 0, 200);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(holder.loader !=null){
                    AndroidUtils.animateView(holder.loader, View.GONE, 0, 200);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if(holder.loader !=null){
                    AndroidUtils.animateView(holder.loader,  View.GONE, 0, 200);
                }
            }
        });

        ExifInterface intf = null;
        try {
            intf = new ExifInterface(pathToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (intf == null) {
            /* File doesn't exist or isn't an image */
        }

        String dateString = intf.getAttribute(ExifInterface.TAG_DATETIME);
        holder.textView.setText(dateString);


        holder.shareIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Share Intent
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/*");
                    Uri uri = Uri.parse("file:/" + pathToFile);
                    share.putExtra(Intent.EXTRA_STREAM, "file:/" + pathToFile);
                    mcontext.startActivity(Intent.createChooser(share, "Share Image Tutorial"));

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filePaths.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView,shareIt;
        private TextView textView;
        private ArrayList<String> TfilePaths = new ArrayList<>();
        private Context Vcontext;
        private View loader;

        public ImageViewHolder(View itemView, Context context, ArrayList<String> filePaths) {
            super(itemView);
            this.Vcontext = context;
            this.TfilePaths = filePaths;
            imageView = (ImageView) itemView.findViewById(R.id.image);
            shareIt = (ImageView) itemView.findViewById(R.id.shareIt);
            textView = (TextView) itemView.findViewById(R.id.location);
            loader = itemView.findViewById(R.id.progress_overlay);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Toast.makeText(Vcontext,filePaths.get(position),Toast.LENGTH_LONG).show();
        }
    }


}
