package kz.dostyk.ozindidamyt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import kz.dostyk.ozindidamyt.ui.library.LibraryFragment;
import kz.dostyk.ozindidamyt.ui.prof_orientation.ProfOrientationFragment;
import kz.dostyk.ozindidamyt.ui.slideshow.SlideshowFragment;
import kz.dostyk.ozindidamyt.ui.user_profile.UserProfileActivity;
import kz.dostyk.ozindidamyt.ui.user_profile.models.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    LinearLayout userLayout;
    TextView userName;//, userPhone, userEmail;
    DrawerLayout drawer;
    LibraryFragment libraryFragment;
    NavigationView navigationView;
    MenuItem btn;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseUser currentUser;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        /*
        version 2.0
        initUserId();
         */

//        onClick(new View(this));

    }
    public void initViews(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_library, R.id.nav_angimeler, R.id.nav_slideshow, R.id.nav_cinema, R.id.nav_podcast, R.id.nav_angimeler, R.id.nav_hobby, R.id.nav_prof_orientation)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        userLayout = navigationView.getHeaderView(0).findViewById(R.id.userLayout);
        userName = navigationView.getHeaderView(0).findViewById(R.id.userName);
//        userPhone = navigationView.getHeaderView(0).findViewById(R.id.userPhone);
//        userEmail = navigationView.getHeaderView(0).findViewById(R.id.userEmail);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        /*version 2.0

        btn = navigationView.getMenu().getItem(10);



        btn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                mAuth.signOut();
                finish();
                return false;
            }
        });

         */

        libraryFragment = new LibraryFragment();
        userLayout.setOnClickListener(this);
    }

        /*
        version 2.0
    public void initUserId() {
        userId = "";

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        if (currentUser.getPhoneNumber() != null && currentUser.getPhoneNumber().length() > 0) { // phone login
            userId = currentUser.getPhoneNumber();
        } else {
            userId = currentUser.getDisplayName();
        }
        Log.i("cabinet", "user phone: "+userId);


        databaseReference.child("user_list").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    userName.setText(user.getInfo());
                    userPhone.setText(user.getPhoneNumber());
                    userEmail.setText(user.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

         */

    @Override
    public void onClick(View view) {
        Toast.makeText(this, getString(R.string.cabinetUnderConstruct), Toast.LENGTH_SHORT).show();

        /*
        version 2.0
        startActivity(new Intent(this, UserProfileActivity.class));
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
