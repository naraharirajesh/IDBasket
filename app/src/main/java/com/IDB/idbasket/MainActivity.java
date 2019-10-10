package com.IDB.idbasket;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.IDB.idbasket.Utils.SaveData;
import com.IDB.idbasket.fragmentClasses.HomeScreen;
import com.IDB.idbasket.fragmentClasses.MyCreations;
import com.IDB.idbasket.model.MenuItemModel;
import com.IDB.idbasket.model.RegDataModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    RecyclerView sideMenuList;
    ArrayList<MenuItemModel> menuListData;
    RegDataModel mainData = null;
    TextView firstLetter,helloText;
    ImageButton menu_icon;
    TextView app_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sideMenuList = findViewById(R.id.sideMenuList);
        firstLetter = findViewById(R.id.firstLetter);
        menu_icon = findViewById(R.id.menu_icon);
        app_title = findViewById(R.id.app_title);
         helloText = findViewById(R.id.helloText);
        menuListData = new ArrayList<>();
        createMenuData();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mainData = new RegDataModel();
        mainData = new SaveData(MainActivity.this).getMainData();

         if(mainData != null && mainData.getFirstName() != null ) {
             firstLetter.setText(""+mainData.getFirstName().charAt(0));
             helloText.setText("Hi " +mainData.getFirstName());
         }
         menu_icon.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 DrawerLayout drawer = findViewById(R.id.drawer_layout);
                 if (drawer.isDrawerOpen(GravityCompat.START)) {
                     drawer.closeDrawer(GravityCompat.START);
                 }else{
                     drawer.openDrawer(GravityCompat.START);
                 }

             }
         });


        getRWPermissionListener();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    /*@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/
   class MenuRecylerListAdapter extends RecyclerView.Adapter<MenuRecylerListAdapter.MyViewHolder>{
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_row_item_layout, viewGroup, false);
            return new MyViewHolder(itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            myViewHolder.menuItem.setText(menuListData.get(i).getMenuItemName());
            myViewHolder.menuItemButton.setBackground(getResources().getDrawable(menuListData.get(i).getMenuItemId()));

        }

        @Override
        public int getItemCount() {
            return menuListData.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
          TextView menuItem;
          ImageButton menuItemButton;
          RelativeLayout totalRow;


          public MyViewHolder(@NonNull View itemView) {
              super(itemView);
            menuItem = itemView.findViewById(R.id.menuItem);
            menuItemButton = itemView.findViewById(R.id.menuItemButton);
            totalRow = itemView.findViewById(R.id.totalRow);
            totalRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callSelectedFragment(menuItem.getText().toString().trim());
                }
            });
          }
      }

    }
    public void callSelectedFragment(String text ){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
       Fragment fragment = null;
       if(text.trim().equalsIgnoreCase("My Creations")){
           fragment = new MyCreations();
       }else if(text.trim().equalsIgnoreCase("My Cards")){
           fragment = new HomeScreen();
       }else if(text.trim().equalsIgnoreCase("Invite")){
           fragment = new HomeScreen();
       }else if(text.trim().equalsIgnoreCase("Profile Settings")){
           fragment = new HomeScreen();
       }else if(text.trim().equalsIgnoreCase("Logout")) {

       }
       FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
       ft.replace(R.id.container_layout,fragment);
       ft.commit();
    }
    public void createMenuData(){
       menuListData = new ArrayList<>();
       MenuItemModel m1 = new MenuItemModel();
       m1.setMenuItemId(R.drawable.my_cards);
       m1.setMenuItemName("My Cards");
       menuListData.add(m1);
        MenuItemModel m2 = new MenuItemModel();
        m2.setMenuItemId(R.drawable.my_list_drawable);
        m2.setMenuItemName("My Creations");
        menuListData.add(m2);
        MenuItemModel m3 = new MenuItemModel();
        m3.setMenuItemId(R.drawable.share_icon);
        m3.setMenuItemName("Invite");
        menuListData.add(m3);
        MenuItemModel m4 = new MenuItemModel();
        m4.setMenuItemId(R.drawable.profile_setting);
        m4.setMenuItemName("Profile Settings");
        menuListData.add(m4);
        MenuItemModel m5 = new MenuItemModel();
        m5.setMenuItemId(R.drawable.profile_setting);
        m5.setMenuItemName("Logout");
        menuListData.add(m5);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        sideMenuList.setLayoutManager(linearLayoutManager);
        sideMenuList.setAdapter(new MenuRecylerListAdapter());
    }
    public void getRWPermissionListener() {
         Dexter.withActivity(MainActivity.this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if(report.areAllPermissionsGranted()){
                    HomeScreen home = new HomeScreen();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container_layout,home);
                    ft.commit();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

}
