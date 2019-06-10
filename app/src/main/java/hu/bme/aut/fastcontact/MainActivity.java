package hu.bme.aut.fastcontact;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
public void call(String a)
{
    Intent intent = new Intent(Intent.ACTION_DIAL);
    intent.setData(Uri.parse("tel:"+a));
    startActivity(intent);
}
    public static List<Contact> productList=new ArrayList<>();
    //the recyclerview
    RecyclerView recyclerView;
    Realm realm;
    ContactAdapter adapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Add:
                Intent intent=new Intent(MainActivity.this,new_contact.class);
                startActivity(intent);
                break;
           case R.id.Delete:
                Toast.makeText(this, "Delete all selected", Toast.LENGTH_LONG).show();
                realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.deleteAll();
                realm.commitTransaction();
                productList.clear();
                adapter.removeAll();
                productList=new ArrayList<>();
                break;
            case R.id.police:
                call("107");
                break;
            case R.id.hospital:
                call("104");
                break;
            case R.id.fire:
                call("105");
                break;
            case R.id.taxi:
                call("+36612111111");
                break;
            default:
                break;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Realm initialization
        realm.init(this);
        realm = Realm.getDefaultInstance();
        productList=new ArrayList<>();
        RealmResults<Contact> results = realm.where(Contact.class).findAll();
        for(Contact contact : results)
        {
            productList.add(contact);
        }
        adapter = new ContactAdapter(this, productList);
        //creating recyclerview adapter
        //setting adapter to recyclerview
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);





        MyBroadcastReceiver receiver = new MyBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(receiver,intentFilter);




    }
}
