package kz.dostyk.ozindidamyt.ui.cinema;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.cinema.adapters.CinemaListAdapter;
import kz.dostyk.ozindidamyt.ui.cinema.models.Cinema;
import kz.dostyk.ozindidamyt.ui.podcast.models.Podcast;

public class CinemaFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private RecyclerView bookListRv;
    private CinemaListAdapter cinemaListAdapter;
    private ArrayList<Cinema> cinemaList;
    private ArrayList<Cinema> cinemaListCopy, bookListCopy2;
    private RecyclerView.LayoutManager linearLayoutManager, gridLayoutManager;
    DatabaseReference mDatabaseRef;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cinema, container, false);
        setupViews();

        return rootView;
    }

    public void setupViews() {
        bookListRv = rootView.findViewById(R.id.bookListRv);

        cinemaList = new ArrayList<>();
        cinemaListCopy = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef = mDatabaseRef.child("cinema");

        linearLayoutManager = new LinearLayoutManager(getActivity());
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);

        initFilms();
        getCinemaFromFirebase();
        cinemaListCopy.addAll(cinemaList);

        cinemaListAdapter = new CinemaListAdapter(getActivity(), cinemaList);

        int resId = R.anim.layout_anim_bounce;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);

        bookListRv.setLayoutManager(gridLayoutManager);
        bookListRv.setItemAnimator(new DefaultItemAnimator());
        bookListRv.setLayoutAnimation(animation);
        bookListRv.setAdapter(cinemaListAdapter);

    }

    public void initFilms(){

        cinemaList.add(new Cinema(
                "",
                "Звездочки на земле",
                R.drawable.film1,
                "https://t.me/c/1273385499/17",
                "rus"));

        cinemaList.add(new Cinema(
                "",
                "Три идиота",
                R.drawable.film2,
                "https://t.me/c/1273385499/20",
                "rus"));

        cinemaList.add(new Cinema(
                "",
                "В погоне за счастьем",
                R.drawable.film3,
                "https://t.me/c/1273385499/22",
                "rus"));

        cinemaList.add(new Cinema(
                "",
                "Учитель Года",
                R.drawable.film4,
                "https://t.me/c/1273385499/24",
                "rus"));


        cinemaList.add(new Cinema(
                "",
                "Писатели свободы",
                R.drawable.film5,
                "https://t.me/c/1273385499/26",
                "rus"));

        cinemaList.add(new Cinema(
                "",
                "Дангал",
                R.drawable.film6,
                "https://t.me/c/1273385499/28",
                "rus"));

        cinemaList.add(new Cinema(
                "",
                "Супер 30",
                R.drawable.film7,
                "https://t.me/c/1273385499/30",
                "rus"));

        cinemaList.add(new Cinema(
                "",
                "1+1",
                R.drawable.film9,
                "https://t.me/c/1273385499/34",
                "rus"));

        cinemaList.add(new Cinema(
                "",
                "Семь жизней",
                R.drawable.film11,
                "https://t.me/c/1273385499/36",
                "rus"));

        cinemaList.add(new Cinema(
                "",
                "Невидимая сторона",
                R.drawable.film12,
                "https://t.me/c/1273385499/38",
                "rus"));

        cinemaList.add(new Cinema(
                "",
                "Хатико: Самый верный друг",
                R.drawable.film13,
                "https://t.me/c/1273385499/40",
                "rus"));

        cinemaList.add(new Cinema(
                "",
                "Я – препод",
                R.drawable.film14,
                "https://t.me/c/1273385499/42",
                "rus"));
    }

    public void getCinemaFromFirebase(){
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot podcastData : dataSnapshot.getChildren()) {
                        Cinema cinema = podcastData.getValue(Cinema.class);
                        cinemaList.add(cinema);
                    }

                    Collections.reverse(cinemaList);

                    cinemaListCopy.clear();
                    cinemaListCopy.addAll(cinemaList);
                    cinemaListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    View sortDialogView;
    AlertDialog filterDialog;

    public void filterDialog() {
        LayoutInflater factory = LayoutInflater.from(getActivity());

        sortDialogView = factory.inflate(R.layout.dialog_filter_language, null);
        filterDialog = new android.app.AlertDialog.Builder(getActivity()).create();

        LinearLayout bookKaz = sortDialogView.findViewById(R.id.bookKaz);
        LinearLayout bookRus = sortDialogView.findViewById(R.id.bookRus);

        TextView tv_kaz = sortDialogView.findViewById(R.id.tv_kaz);
        TextView tv_rus = sortDialogView.findViewById(R.id.tv_rus);
        tv_kaz.setText("Қазақша кинолар");
        tv_rus.setText("Кино на русском");

        bookKaz.setOnClickListener(this);
        bookRus.setOnClickListener(this);

        filterDialog.setView(sortDialogView);
        filterDialog.show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bookKaz:

                Predicate<Cinema> byKz = cinema -> cinema.getLanguage().equals("kaz");

                ArrayList angimeListFilter = (ArrayList<Cinema>) cinemaListCopy.stream().filter(byKz).collect(Collectors.toList());
                cinemaList.clear();
                cinemaList.addAll(angimeListFilter);

                cinemaListAdapter.notifyDataSetChanged();

                filterDialog.dismiss();
                break;
            case R.id.bookRus:

                Predicate<Cinema> byRus = cinema -> cinema.getLanguage().equals("rus");

                ArrayList angimeListFilter2 = (ArrayList<Cinema>) cinemaListCopy.stream().filter(byRus).collect(Collectors.toList());
                cinemaList.clear();
                cinemaList.addAll(angimeListFilter2);

                cinemaListAdapter.notifyDataSetChanged();

                filterDialog.dismiss();
                break;
        }


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                filterDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void filter(String text) {
        cinemaList.clear();
        if (text.isEmpty()) {
            cinemaList.addAll(cinemaListCopy);
        } else {
            text = text.toLowerCase();
            for (Cinema item : cinemaListCopy) {

                if (item.getName().toLowerCase().contains(text) ||
                        item.getName().toUpperCase().contains(text) ) {

                    cinemaList.add(item);
                }
            }
        }
        cinemaListAdapter.notifyDataSetChanged();
    }

}
