package com.example.sreekanthkasani.login;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by sreekanth kasani  ') on 12/1/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ContactViewHolder> {

    private List<ContactsVO> contactsVOList;
    private  Context mContext;

    public RecyclerViewAdapter(List<ContactsVO> contactsVOList, Context mContext) {
        this.contactsVOList = contactsVOList;
        this.mContext = mContext;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_holder,null);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        ContactsVO contactVO = contactsVOList.get(position);

        holder.DisplayPic.setImageBitmap(contactVO.getContactImage());
        holder.ProfileName.setText(contactVO.getProfileName());
        holder.message.setText(contactVO.getContact_no());
        //holder.timeStamp.setText(contactVO.get);
    }

    @Override
    public int getItemCount() {
        return contactsVOList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{

        ImageView DisplayPic;
        TextView ProfileName,message,timeStamp;

        public ContactViewHolder(View itemView) {
            super(itemView);
            DisplayPic= (ImageView) itemView.findViewById(R.id.DisplayPic);
            ProfileName= (TextView) itemView.findViewById(R.id.ProfileName);
            message= (TextView) itemView.findViewById(R.id.message);
            timeStamp= (TextView) itemView.findViewById(R.id.timeStamp);
        }
    }
}
