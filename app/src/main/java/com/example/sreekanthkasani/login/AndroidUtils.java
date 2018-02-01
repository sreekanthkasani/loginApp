package com.example.sreekanthkasani.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.Manifest;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sreekanth kasani  ') on 11/30/2017.
 */

public class AndroidUtils {


    //------------------------------------GLOBAL PERMISSION UTILS----------------------------------//

    public static final String[] permissions = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    public static final String[] CAMERA_PERMISSION = {
            Manifest.permission.CAMERA,
    };

    //--------------------------------GLOBAL FILE PATHS FOR IMAGES--------------------------------//

    //(this usually returns the root directory where Images are stored) "Storage/emulated/0"
    public static final String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();

    public static final String PICTURES = ROOT_DIR + "/Pictures";
    public static final String CAMERA = ROOT_DIR + "/DCIM/camera";

    //--------------------------------------------------------------------------------------------//


    public static void print(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    /**
     * @param view         View to animate
     * @param toVisibility Visibility at the end of animation
     * @param toAlpha      Alpha at the end of animation
     * @param duration     Animation duration in ms
     */
    public static void animateView(final View view, final int toVisibility, float toAlpha, int duration) {
        boolean show = toVisibility == View.VISIBLE;
        if (show) {
            view.setAlpha(0);
        }
        view.setVisibility(View.VISIBLE);
        view.animate()
                .setDuration(duration)
                .alpha(show ? toAlpha : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(toVisibility);
                    }

                });
    }

    /**
     * This method fetches contacts from android and populates ContactsVO object array
     * @param mcontext
     * @param recyclerView
     * @return
     */

    public static List<ContactsVO> getMobileContactsAndSetAdapter(Context mcontext, RecyclerView recyclerView){
        List<ContactsVO> contactVOList = new ArrayList();
        ContactsVO contactVO;
        RecyclerView rvContacts = recyclerView;

        ContentResolver contentResolver = mcontext.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    contactVO = new ContactsVO();
                    contactVO.setProfileName(name);

                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactVO.setContact_no(phoneNumber);
                    }

                    phoneCursor.close();

                    Cursor emailCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);

                    while (emailCursor.moveToNext()) {
                        String emailId = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    }
                    contactVOList.add(contactVO);
                }
            }

            RecyclerViewAdapter contactAdapter = new RecyclerViewAdapter(contactVOList, mcontext);
            rvContacts.setLayoutManager(new LinearLayoutManager(mcontext));
            rvContacts.setAdapter(contactAdapter);
        }
        return contactVOList;
    }


    public static void  setHrecyclerView() {
    }

    public static void getImagesAndsetVrecyclerView() {
    }

    public static ArrayList<String> getDirectoryPaths(String directory){
        ArrayList<String> Directorypaths = new ArrayList<>();
        File file = new File(directory);
        File[] listfiles = file.listFiles();
        for(int i=0;i<listfiles.length;i++){
            if (listfiles[i].isDirectory()){
                Directorypaths.add(listfiles[i].getAbsolutePath());
            }
        }
        return Directorypaths;
    }

    public static ArrayList<String> getFilePaths(String directory){
        ArrayList<String> filePaths = new ArrayList<>();
        File file = new File(directory);
        File[] listfiles = file.listFiles();
        for(int i=0;i<listfiles.length;i++){
            if (listfiles[i].isFile()){
                filePaths.add(listfiles[i].getAbsolutePath());
            }
        }
        return filePaths;
    }


}
