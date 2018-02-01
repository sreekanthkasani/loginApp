package com.example.sreekanthkasani.login;

import android.graphics.Bitmap;

/**
 * Created by sreekanth kasani  ') on 12/1/2017.
 */

public class ContactsVO {
    private Bitmap contactImage;
    private String ProfileName;
    private String Message;
    private String timeStamp;
    private String contact_no;

    public ContactsVO() {
    }

    public ContactsVO(Bitmap contactImage, String profileName, String message, String timeStamp, String contact_no) {
        this.contactImage = contactImage;
        ProfileName = profileName;
        Message = message;
        this.timeStamp = timeStamp;
        this.contact_no = contact_no;
    }

    public Bitmap getContactImage() {
        return contactImage;
    }

    public void setContactImage(Bitmap contactImage) {
        this.contactImage = contactImage;
    }

    public String getProfileName() {
        return ProfileName;
    }

    public void setProfileName(String profileName) {
        ProfileName = profileName;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    @Override
    public String toString() {
        return "ContactsVO{" +
                "contactImage='" + contactImage + '\'' +
                ", ProfileName='" + ProfileName + '\'' +
                ", Message='" + Message + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
