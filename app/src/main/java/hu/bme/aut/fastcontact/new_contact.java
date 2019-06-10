package hu.bme.aut.fastcontact;

import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.realm.Realm;


import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class new_contact extends AppCompatActivity {
    public EditText name, Email,FBprofil;
    public EditText phone;
    public Button addContact,browse;
    public ImageView imageView;
    Uri imageURI ;
    public String image="";
    private static  final int PICK_IMAGE=100;
    Realm realm;
    public EditText getName() {
        return name;
    }

    public void setName(EditText name) {
        this.name = name;
    }

    public EditText getEmail() {
        return Email;
    }

    public void setEmail(EditText email) {
        Email = email;
    }

    public EditText getFBprofil() {
        return FBprofil;
    }

    public void setFBprofil(EditText fbprofil) {
        FBprofil = fbprofil;
    }

    public EditText getPhone() {
        return phone;
    }

    public void setPhone(EditText phone) {
        this.phone = phone;
    }

    public Button getAddContact() {
        return addContact;
    }

    public void setAddContact(Button addContact) {
        this.addContact = addContact;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        realm = realm.getDefaultInstance();
        name = (EditText)findViewById(R.id.Name);
        Email = (EditText)findViewById(R.id.Email);
        FBprofil = (EditText)findViewById(R.id.FbProfilID);
        phone=(EditText)findViewById(R.id.Phone);
        browse=(Button)findViewById(R.id.browse);
        addContact=(Button)findViewById(R.id.addcontact);
        imageView=(ImageView)findViewById(R.id.imageView);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((FBprofil.getText().toString().isEmpty())||(name.getText().toString().isEmpty())||(Email.getText().toString().isEmpty())||(phone.getText().toString().isEmpty()))
                {   Snackbar.make(view,"Please enter all the fields, Thanks !",Snackbar.LENGTH_LONG).show();}
                else
                {
                    realm.beginTransaction();
                    Contact contact = realm.createObject(Contact.class, name.getText().toString());
                    contact.setEmailAddress(Email.getText().toString());
                    contact.setFbLink(FBprofil.getText().toString());
                    contact.setPhoneNumber(phone.getText().toString());
                    contact.setImage(image);
                    realm.commitTransaction();
                    Intent intent=new Intent(new_contact.this,MainActivity.class);
                    startActivity(intent);}
                }

        });

    }
    private void openGallery()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE)
        {
            imageURI =data.getData();
            imageView.setImageURI(imageURI);
            image=imageURI.toString();

        }
    }
}
