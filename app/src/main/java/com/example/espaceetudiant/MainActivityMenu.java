package com.example.espaceetudiant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MfragmentlistprocessFragment.OnListFragmentInteractionListener ,MProcessForm.OnFragmentInteractionListener, MBrouillonProcessFragment.OnListFragmentInteractionListener{

private String token;
private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences Tpref = getApplicationContext().getSharedPreferences("TokenPref", 0); // 0 - for private mode
        token = Tpref.getString("access_token", "");
         email = Tpref.getString("email", "");



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.username);
        navUsername.setText("Institut supérieur d'informatique");
        TextView navEmail = (TextView) headerView.findViewById(R.id.email);
        navEmail.setText(email);


        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MfragmentlistprocessFragment fragment = new MfragmentlistprocessFragment();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onBackPressed() {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fr;


        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MfragmentlistprocessFragment fragment = new MfragmentlistprocessFragment();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MBrouillonProcessFragment fragment = new MBrouillonProcessFragment();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_slideshow) {
            fr = new BlankFragment();

        } else if (id == R.id.nav_manage) {
            fr = new BlankFragment();

        } else if (id == R.id.nav_share) {
            fr = new BlankFragment();


        } else if (id == R.id.nav_send) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.remove("access_token");
            editor.remove("refresh_token");
            editor.commit();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{
            fr = new BlankFragment();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Process item){

        final String pro_uid= item.pro_uid;
        final String tas_uid= item.tas_uid;
        final String pro_title= item.pro_title;


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.API_BASE_URL))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit= builder.build();
        LoginService client = retrofit.create(LoginService.class);
        Call<ResponseBody> call= client.get_id_forum("Bearer "+token,pro_uid,tas_uid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        String responseData = response.body().string();
                        JSONArray jsonArray = new JSONArray(responseData);
                        String step_uid_obj = jsonArray.getJSONObject(0).getString("step_uid_obj");
                        Toast.makeText(getApplicationContext(), step_uid_obj+"Im here lalallala", Toast.LENGTH_SHORT).show();
                        //Get form informations

                        Call<ResponseBody> call2= client.getforum("Bearer "+token,pro_uid,step_uid_obj);
                        call2.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()){
                                    MProcessForm fragment= new MProcessForm();
                                    try {
                                        String responseData = response.body().string();

                                        Bundle args = new Bundle();
                                        args.putString("formJsonString", responseData);
                                        args.putString("pro_uid", pro_uid);
                                        args.putString("pro_title", pro_title);
                                        args.putString("tas_uid", tas_uid);
                                        args.putString("token", token);

                                        fragment.setArguments(args);
                                        FragmentManager fragmentManager = getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.frameLayout, fragment);
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(), "response failed", Toast.LENGTH_SHORT).show();

                                }




                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        } );




                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "Cant parse 1", Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "erroooor response not succegul", Toast.LENGTH_SHORT).show();

                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                // TODO: handle error
            }
        });

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DraftProcessItem item) {

    }
}
