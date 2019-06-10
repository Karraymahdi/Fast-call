package hu.bme.aut.fastcontact;

import android.media.Image;
import android.net.Uri;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Contact extends RealmObject {
    @PrimaryKey
    public String name;
    public String FbLink;
    public String EmailAddress;
    public String phoneNumber;
    public String image;
    public Contact(){
    }

    public Contact(String name, String fbLink, String emailAddress, String phoneNumber,String image) {
        this.name = name;
        FbLink = fbLink;
        EmailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getFbLink() {
        return FbLink;
    }

    public void setFbLink(String fbLink) {
        FbLink = fbLink;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
