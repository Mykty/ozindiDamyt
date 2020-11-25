package kz.dostyk.ozindidamyt.ui.hobby;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.hobby.activity.TattiActivity;
import kz.dostyk.ozindidamyt.ui.hobby.adapters.HobbyListAdapter;
import kz.dostyk.ozindidamyt.ui.hobby.models.Hobby;
import kz.dostyk.ozindidamyt.ui.interfaces.RecyclerItemClickListener;
import kz.dostyk.ozindidamyt.ui.podcast.models.Podcast;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.adapters.FavAngimelerAdapter;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.models.Angime;

public class HobbyFragment extends Fragment {
    @BindView(R.id.recyclerForBook) RecyclerView recyclerView;

    View view;
    ArrayList<Hobby> hobbies;
    HobbyListAdapter hobbyListAdapter;
    DatabaseReference mDatabaseRef;

    public HobbyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        initViews();
        getHobbiesFromFirebase();

        return view;
    }

    public void initViews() {
        ButterKnife.bind(this, view);
        hobbies = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("hobbies");

        initHobbies();
        hobbyListAdapter = new HobbyListAdapter(getActivity(), hobbies);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(hobbyListAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int pos) {
                        String link = hobbies.get(pos).getLink();

                        if(link.equals("desert")){
                            startActivity(new Intent(getActivity(), TattiActivity.class));

                        }else {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(link));
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    public void initHobbies(){
        hobbies.add(new Hobby("",
                "Программа жазу",
                "Мобильді құрылғыларға арналған программа жазу",
                "https://www.youtube.com/playlist?list=PLZzSBvTgFvr-PAym13KfdRsgnKUlsBTip",
                R.drawable.mobile_dev_hobby));


        hobbies.add(new Hobby("",
                "Ағылшын тілі",
                "Ағылшын тілін үйрену курсы",
                "https://perfectlyspoken.com", R.drawable.eng_hobby3));

        hobbies.add(new Hobby("",
                "Фотошоп",
                "Фотошоп үйрену курсы",
                "https://www.torap.kz/article/10",
                R.drawable.photoshop_hobby));

        hobbies.add(new Hobby("",
                "Шахмат",
                "Шахмат үйрену курсы",
                "https://www.chess.com/ru",
                R.drawable.cheese_hobby2));

        hobbies.add(new Hobby("",
                "Тәтті пісіру",
                "Тәтті пісіру 1 курсы",
                "desert",
                R.drawable.desert_hobby));

    }

    public void getHobbiesFromFirebase(){
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot hobbyData : dataSnapshot.getChildren()) {
                        Hobby hobby = hobbyData.getValue(Hobby.class);
                        hobbies.add(hobby);
                    }

                    Collections.reverse(hobbies);
                    hobbyListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
