package com.simtechinc.radarintrotool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

public class MainActivity extends AppCompatActivity
{

  NavController mNavController;
  private AppBarConfiguration mAppBarConfiguration;
  private static final String PREF_FILE = "com.simtechinc.radarintrotool.prefs";

  @SuppressLint("SourceLockedOrientationActivity")
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        Snackbar.make(view, getString(R.string.noSettingsString), Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    NavigationView navigationView = findViewById(R.id.nav_view);
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    mAppBarConfiguration = new AppBarConfiguration.Builder(
        R.id.nav_blockDiagram, R.id.nav_RadarRangeEquation)
        .setDrawerLayout(drawer)
        .build();

    mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
    SharedPreferences prefs = getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
    mNavController.navigate(prefs.getInt("startDestination",mNavController.getCurrentDestination().getId()));
    mNavController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener()
    {
      @Override
      public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments)
      {
        SharedPreferences prefs = getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        prefs.edit().putInt("startDestination",destination.getId()).apply();
      }
    });
    NavigationUI.setupActionBarWithNavController(this, mNavController, mAppBarConfiguration);
    NavigationUI.setupWithNavController(navigationView, mNavController);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onSupportNavigateUp()
  {
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    return NavigationUI.navigateUp(navController, mAppBarConfiguration)
        || super.onSupportNavigateUp();
  }
}
