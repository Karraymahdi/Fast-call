package hu.bme.aut.fastcontact;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.pm.PackageManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import io.realm.Realm;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.app.PendingIntent.getActivity;
import static android.support.v4.content.ContextCompat.startActivity;
import android.content.pm.PackageManager;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder>{


    public static Bitmap getFacebookProfilePicture(String userID) throws IOException {
        String imageURL = "https://graph.facebook.com/{"+userID+"}?fields=picture.width(720).height(720)&redirect=false";
        InputStream in = (InputStream) new URL(imageURL).getContent();
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeStream(in);

        return bitmap;
       // Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
        //return bitmap;
    }







    private Context mCtx;

    Realm realm;
    public  static List<Contact> itemsList;
    public ContactAdapter(Context mCtx,List<Contact> itemsList) {
        this.mCtx = mCtx;
        this.itemsList = itemsList;}
    public List<Contact> getItemsList() {
        return itemsList;
    }
    @NonNull
    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView image;
        public ImageButton phone,facebook,email,delete;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            phone = (ImageButton)view.findViewById(R.id.phone);
            facebook = (ImageButton) view.findViewById(R.id.facebook);
            email=(ImageButton) view.findViewById(R.id.gmail);
            image = (ImageView) view.findViewById(R.id.image);
            delete=(ImageButton) view.findViewById(R.id.delete1);


        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.contact_list,null);

        return new MyViewHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {

        final Contact item = itemsList.get(position);
        holder.name.setText(item.getName());
        if (item.getImage().isEmpty())
            holder.image.setImageResource(R.drawable.fbpr);
        else
        {
            holder.image.setImageURI(Uri.parse(item.getImage()));
        }
        holder.facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation= AnimationUtils.loadAnimation(mCtx,R.anim.bounce);
                holder.facebook.startAnimation(animation);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(mCtx, "Facebook Profil " , Toast.LENGTH_SHORT).show();
                        //MainActivity.FB(fbID);
                        Intent intent = null;
                        PackageManager pm = mCtx.getPackageManager();
                        try {
                            pm.getPackageInfo("com.facebook.katana", 0);
                            String url = "https://www.facebook.com/"+item.getFbLink();;
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href="+url));
                        } catch (Exception e) {
                            // no Facebook app, revert to browser
                            String url = "https://facebook.com/"+item.getFbLink();;
                            intent = new Intent(Intent.ACTION_VIEW);
                            intent .setData(Uri.parse(url));
                        }
                        ((MainActivity)mCtx).startActivity(intent);

                    }
                }, 1000);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation= AnimationUtils.loadAnimation(mCtx,R.anim.blink_anim);
                holder.delete.startAnimation(animation);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 1s
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        itemsList.get(holder.getAdapterPosition()).deleteFromRealm();
                        realm.commitTransaction();
                        itemsList.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                }, 1000);
            }
        });
        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+item.getPhoneNumber()));
                ((MainActivity)mCtx). startActivity(intent);
            }
        });
    holder.email.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String a =item.getEmailAddress();
            Intent intent = new Intent (Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{a});
            intent.putExtra(Intent.EXTRA_TEXT, "Dear "+ item.getName());
            intent.putExtra(Intent.EXTRA_SUBJECT, "URGENT");
            intent.setPackage("com.google.android.gm");
            PackageManager pm = mCtx.getPackageManager();
            if (intent.resolveActivity(pm)!=null)
                ((MainActivity)mCtx).startActivity(intent);
            else
                Toast.makeText(mCtx, "Gmail App is not installed" , Toast.LENGTH_SHORT).show();

        }
    });

    }
    public void removeAll(){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        itemsList.clear();
        notifyDataSetChanged();
    }

}
