package kz.dostyk.ozindidamyt.ui.library.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.library.models.Book;


public class OneBookAcvitiy extends AppCompatActivity {

    Toolbar toolbar;
    AppBarLayout appBarLayout;
    ImageView bookImage;
    TextView bookName, bookAuthor, bookDesc;
    Book book;
    Button btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_book);
        initWidgets();

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        book = (Book) bundle.getSerializable("book");

        initializeBundle(bundle, book);
        initializeToolbar();
    }

    public void initializeToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Кітапхана");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initWidgets() {
        toolbar = findViewById(R.id.toolbars);
        appBarLayout = findViewById(R.id.app_bar);

        bookImage = findViewById(R.id.bookImage);
        bookName = findViewById(R.id.bookName);
        bookAuthor = findViewById(R.id.bookAuthor);
        btnUpload = findViewById(R.id.btnUpload);
        bookDesc = findViewById(R.id.book_desc);
        bookDesc.setMovementMethod(new ScrollingMovementMethod());

        btnUpload.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(book.getUrl()));
            startActivity(i);
        });
    }

    public static String bName, bDesc = "", bAuthor;

    public void initializeBundle(Bundle bundle, Book book) {

        if (bundle != null) {
            bName = book.getName();
            bDesc = book.getDesc();

            bAuthor = book.getAuthor();

            Glide.with(getApplicationContext())
                    .load(book.getPhoto())
                    .placeholder(R.drawable.ic_book)
                    .centerCrop()
                    .into(bookImage);

            bookName.setText(bName);
            bookAuthor.setText(bAuthor);
            bookDesc.setText(bDesc);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

        }
        return super.onOptionsItemSelected(item);
    }
}
