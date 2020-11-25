package kz.dostyk.ozindidamyt.ui.user_profile;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.dostyk.ozindidamyt.MainActivity;
import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.user_profile.models.User;

public class UserProfileActivity extends AppCompatActivity {
    BottomNavigationMenuView mbottomNavigationMenuView;
    View view;
    BottomNavigationItemView itemView;

    static TextSwitcher totalPointSwitcher;

    @BindView(R.id.userName) TextView userName;
    @BindView(R.id.dostykName) TextView dostykName;
    @BindView(R.id.userImage) ImageView userImage;
    DatabaseReference databaseReference;
    FirebaseUser currentUser;

    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initUserId();

    }

    public static void increasePoint(){
        totalPointSwitcher.setText("105 ұпай");
    }

    public void initViews(){
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        BottomNavigationView navView = findViewById(R.id.nav_view);

        totalPointSwitcher = findViewById(R.id.totalPointSwitcher);
        totalPointSwitcher.setFactory(() -> {
            TextView textView = new TextView(UserProfileActivity.this);
            textView.setTextSize(20);
            textView.setText("100 ұпай");
            textView.setMaxLines(1);
            textView.setTextColor(getColor(R.color.white));
            textView.setGravity(Gravity.CENTER);
            return textView;
        });

        totalPointSwitcher.setInAnimation(this, android.R.anim.fade_in);
        totalPointSwitcher.setOutAnimation(this, android.R.anim.fade_out);


        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_daily, R.id.nav_book, R.id.nav_quiz, R.id.nav_sport,R.id.nav_marathon)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);



//        mbottomNavigationMenuView= (BottomNavigationMenuView) navView.getChildAt(0);
//        view = mbottomNavigationMenuView.getChildAt(3);
//        itemView = (BottomNavigationItemView) view;
    }

    public void initUserId() {
        userId = "";

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser.getPhoneNumber() != null && currentUser.getPhoneNumber().length() > 0) { // phone login
            userId = currentUser.getPhoneNumber();
        } else {
            userId = currentUser.getDisplayName();
        }

        databaseReference.child("user_list").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                initUser(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void initUser(User user){

        userName.setText("Қош келдің, "+user.getInfo()+" !");
        dostykName.setText(user.getDostykName()+" Достық");

        Log.i("cabinet", user.toString());

        Glide.with(getApplicationContext())
                .load(user.getPhoto())
                .placeholder(R.drawable.ic_account_circle)
                .dontAnimate()
                .into(userImage);
    }
}
