package kz.dostyk.ozindidamyt.ui.user_profile;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.dostyk.ozindidamyt.MainActivity;
import kz.dostyk.ozindidamyt.R;

public class UserProfileActivity extends AppCompatActivity {
    BottomNavigationMenuView mbottomNavigationMenuView;
    View view;
    BottomNavigationItemView itemView;

    static TextSwitcher totalPointSwitcher;

    @BindView(R.id.userName) TextView userName;
    @BindView(R.id.dostykName) TextView dostykName;
    @BindView(R.id.userImage) ImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initUser();

    }

    public static void increasePoint(){
        totalPointSwitcher.setText("105 ұпай");
    }

    public void initViews(){
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        totalPointSwitcher = (TextSwitcher) findViewById(R.id.totalPointSwitcher);
        totalPointSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(UserProfileActivity.this);
                textView.setTextSize(20);
                textView.setText("100 ұпай");
                textView.setMaxLines(1);
                textView.setTextColor(getColor(R.color.white));
                textView.setGravity(Gravity.CENTER);
                return textView;
            }
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

    public void initUser(){
        userName.setText("Қош келдің, Mykty Baha!");
        dostykName.setText("Актау Достық");
        Glide.with(getApplicationContext())
                .load(R.drawable.user_photo)
                .placeholder(R.drawable.ic_account_circle)
                .dontAnimate()
                .into(userImage);
    }

}
