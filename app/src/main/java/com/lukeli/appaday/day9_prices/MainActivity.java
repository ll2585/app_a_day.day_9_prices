package com.lukeli.appaday.day9_prices;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    private static final int SETTINGS_INFO = 1;
    private boolean show_name = true;
    private boolean show_brand = true;
    private boolean show_category = true;
    private boolean show_subcategory = true;
    private boolean show_store = true;
    private boolean show_sale = true;
    private boolean[] setting_vals = {show_name,show_brand,show_category,show_subcategory,show_store,show_sale};
    private String[] setting_keys = {"SHOW_NAME", "SHOW_BRAND", "SHOW_CATEGORY", "SHOW_SUBCATEGORY", "SHOW_STORE", "SHOW_SALE"};
    private LinearLayout[] linearLayouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSavedInstance(savedInstanceState);
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        loadPreferences(preferences);

        setContentView(R.layout.activity_main);
        LinearLayout[] l = {(LinearLayout) findViewById(R.id.show_name),
                (LinearLayout) findViewById(R.id.show_brand),
                (LinearLayout) findViewById(R.id.show_category),
                (LinearLayout) findViewById(R.id.show_subcategory),
                (LinearLayout) findViewById(R.id.show_store),
                (LinearLayout) findViewById(R.id.show_sale)};
        linearLayouts = l;
        setViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);

        // 3. Check that the intent with the id SETTINGS_INFO called here
        if(requestCode == SETTINGS_INFO){

            setViews();

        }

    }

    private void setViews() {
        for(int i = 0; i < setting_vals.length; i++){
            Log.d("SETTING", setting_keys[i]);
            Log.d("SETTING", String.valueOf(setting_vals[i]));
            linearLayouts[i].setVisibility(setting_vals[i] ? View.VISIBLE : View.GONE);
        }
    }

    private void loadSavedInstance(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            for(int i = 0; i < setting_vals.length; i++){
                setting_vals[i] = savedInstanceState.getBoolean(setting_keys[i]);
            }
        }
    }

    private void loadPreferences(SharedPreferences pref) {
        if(pref.getBoolean("PREFERENCES_SAVED", false)){
            for(int i = 0; i < setting_vals.length; i++){
                setting_vals[i] = pref.getBoolean(setting_keys[i], true);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        for(int i = 0; i < setting_vals.length; i++){
            outState.putBoolean(setting_keys[i], setting_vals[i]);
        }
        super.onSaveInstanceState(outState);
    }

    private void saveSettings(){

        // SharedPreferences allow you to save data even if the user kills the app
        // MODE_PRIVATE : Preferences shared only by your app
        // MODE_WORLD_READABLE : All apps can read
        // MODE_WORLD_WRITABLE : All apps can write
        // edit() allows us to enter key vale pairs
        SharedPreferences.Editor sPEditor = getPreferences(Context.MODE_PRIVATE).edit();
        for(int i = 0; i < setting_vals.length; i++){
            sPEditor.putBoolean(setting_keys[i], setting_vals[i]);
        }
        // Save the shared preferences
        sPEditor.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStop() {

        saveSettings();

        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intentPreferences = new Intent(getApplicationContext(),
                    SettingsActivity.class);

            // 3. Start the activity and then pass results to onActivityResult
            startActivityForResult(intentPreferences, SETTINGS_INFO);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createOrDeleteDB(View view) {
    }

    public void addItem(View view) {
    }

    public void search(View view) {
    }
}
