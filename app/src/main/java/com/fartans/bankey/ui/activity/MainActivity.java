package com.fartans.bankey.ui.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;


import com.fartans.bankey.R;
import com.fartans.bankey.adapter.MainFragmentPagerAdapter;
import com.fartans.bankey.db.DbTableStrings;
import com.fartans.bankey.model.KeyValue;
import com.fartans.bankey.model.Vault;
import com.fartans.bankey.ui.fragment.AccountFragment;
import com.fartans.bankey.ui.fragment.HomeFragment;
import com.fartans.bankey.ui.fragment.OtherFragment;
import com.fartans.bankey.ui.fragment.SpecialFragment;
import com.fartans.bankey.ui.fragment.TabFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    ArrayList<Vault> vaultlist;
    ArrayList<KeyValue> keyvaluelist;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        readfromvault();
        getKeyValues();
        inflateView();
    }

    public void inflateView()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;


        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();


                if (menuItem.getItemId() == R.id.nav_item_home) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_item_account) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
                }

                if(menuItem.getItemId()==R.id.nav_item_card){
                    Intent intent=new Intent(MainActivity.this,VaultActivity.class);
                    startActivity(intent);
                }

                if(menuItem.getItemId()==R.id.nav_item_key){
                    Intent intent=new Intent(MainActivity.this,VaultActivity.class);
                    startActivity(intent);
                }

                return false;
            }

        });

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    public ArrayList getKeyValues() {
        keyvaluelist=new ArrayList<>();
        ContentResolver contentresolver=getContentResolver();
        Cursor cursor=contentresolver.query(Uri.parse(DbTableStrings.DATA_URI),null,null,null,null);
        if(cursor.moveToNext()){
            while (cursor.moveToNext()){
                KeyValue keyvalue=new KeyValue();
                keyvalue.setName(cursor.getString(cursor.getColumnIndex(DbTableStrings.KEY)));
                keyvalue.setValue(cursor.getString(cursor.getColumnIndex(DbTableStrings.VALUE)));
                keyvaluelist.add(keyvalue);
            }
        }
        return keyvaluelist;
    }

    public ArrayList readfromvault() {
        vaultlist = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor=contentResolver.query(Uri.parse(DbTableStrings.VAULT_URI),null,null,null,null);
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()) {
                Vault vault = new Vault();
                vault.setName(cursor.getString(cursor.getColumnIndex(DbTableStrings.VAULT_NAME)));
                vault.setKeyNumber(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbTableStrings.VAULT_ID))));
                vaultlist.add(vault);
            }

        }
        return vaultlist;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
