package kz.dostyk.ozindidamyt.ui.podcast;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import kz.dostyk.ozindidamyt.ui.podcast.adapters.PodcastListAdapter;
import kz.dostyk.ozindidamyt.ui.podcast.models.Podcast;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.models.Angime;

public class PodcastFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private RecyclerView recyclerView;
    private PodcastListAdapter podcastListAdapter;
    private ArrayList<Podcast> podcastList;
    private ArrayList<Podcast> podcastFromFireList;
    private ArrayList<Podcast> podcastListCopy;
    private RecyclerView.LayoutManager linearLayoutManager, gridLayoutManager;
    LinearLayout podcastLayout;
    DatabaseReference mDatabaseRef;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_podcast, container, false);
        setupViews();

        return rootView;
    }

    public void setupViews() {
        recyclerView = rootView.findViewById(R.id.recyclerForBook);
        podcastLayout = rootView.findViewById(R.id.podcastLayout);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef = mDatabaseRef.child("podcasts");
        podcastList = new ArrayList<>();
        podcastListCopy = new ArrayList<>();
        podcastFromFireList = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(getActivity());
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);

        initPodcasts();
        getPodcastsFromFirebase();
        podcastListCopy.addAll(podcastList);

        podcastListAdapter = new PodcastListAdapter(getActivity(), podcastList);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(podcastListAdapter);
        podcastLayout.setOnClickListener(this);

    }

    public void initPodcasts(){
        podcastList.add(new Podcast(
                "",
                "janajaIQ",
                "Adamnyñ qalaulary",
                "janajayik",
                "33 min",
                "https://podcasts.google.com/feed/aHR0cHM6Ly9hbmNob3IuZm0vcy8yNzI1ZDNlNC9wb2RjYXN0L3Jzcw/episode/OTBkODBmYmItMTI4OC00NDNkLTk5MmQtODU3OWFiOGM4ZmNl?sa=X&ved=2ahUKEwius5Hk2OvrAhUDwsQBHfKUBkMQkfYCegQIARAF"
                ,"kaz"
        ));

        podcastList.add(new Podcast(
                "",
                "janajaIQ",
                "ag̃ylshyndy nege oqu kerek?",
                "janajayik",
                "54 min",
                "https://podcasts.google.com/feed/aHR0cHM6Ly9hbmNob3IuZm0vcy8yNzI1ZDNlNC9wb2RjYXN0L3Jzcw/episode/MDIxYTQwZjQtMjEzZC00MjQ3LWFlM2ItNjBlYWMwOTllY2M3?sa=X&ved=0CAUQkfYCahcKEwjI-Zy-k-_rAhUAAAAAHQAAAAAQIQ"
                ,"kaz"
        ));

        podcastList.add(new Podcast(
                "",
                "janajaIQ",
                "data science & tech qyzdar",
                "janajayik",
                "55 min",
                "https://podcasts.google.com/feed/aHR0cHM6Ly9hbmNob3IuZm0vcy8yNzI1ZDNlNC9wb2RjYXN0L3Jzcw/episode/NmYyODkzZjAtMmU4ZS00ZTQxLThlYWItNmM3M2ViNDJhZWNm?sa=X&ved=0CAUQkfYCahcKEwjI-Zy-k-_rAhUAAAAAHQAAAAAQIQ"
                ,"kaz"
        ));

        podcastList.add(new Podcast(
                "",
                "janajaIQ",
                "ruhani damu qajettiligi",
                "janajayik",
                "1 sagat 33 min",
                "https://podcasts.google.com/feed/aHR0cHM6Ly9hbmNob3IuZm0vcy8yNzI1ZDNlNC9wb2RjYXN0L3Jzcw/episode/ODdkYTc3MTEtMmUxZS00NTg2LTg5NzItY2M4NDA5OWVhNDM1?sa=X&ved=0CAUQkfYCahcKEwjI-Zy-k-_rAhUAAAAAHQAAAAAQIQ"
                ,"kaz"
        ));

        podcastList.add(new Podcast(
                "",
                "janajaIQ",
                "virusqa imunitet",
                "janajayik",
                "15 min",
                "https://podcasts.google.com/feed/aHR0cHM6Ly9hbmNob3IuZm0vcy8yNzI1ZDNlNC9wb2RjYXN0L3Jzcw/episode/N2Y5NTM3ZjAtYzNiOS00MDM3LWEwNDUtZTE0MTE0ZWMzZGNl?sa=X&ved=0CAUQkfYCahcKEwjI-Zy-k-_rAhUAAAAAHQAAAAAQIQ"
                ,"kaz"
        ));

        podcastList.add(new Podcast(
                "",
                "janajaIQ",
                "tüpsana tazalau",
                "janajayik",
                "37 min",
                "https://podcasts.google.com/feed/aHR0cHM6Ly9hbmNob3IuZm0vcy8yNzI1ZDNlNC9wb2RjYXN0L3Jzcw/episode/MjgwYjE5YjgtNjk1Mi00ZjA5LTg4NmItYjEzYjQxMTc3NzA4?sa=X&ved=0CAUQkfYCahcKEwjI-Zy-k-_rAhUAAAAAHQAAAAAQIQ"
                ,"kaz"
        ));

        podcastList.add(new Podcast(
                "",
                "janajaIQ",
                "nag̃yz Qazaq - dombyra",
                "janajayik",
                "39 min",
                "https://podcasts.google.com/feed/aHR0cHM6Ly9hbmNob3IuZm0vcy8yNzI1ZDNlNC9wb2RjYXN0L3Jzcw/episode/ZmY2N2RmMzMtZTBlZi00YmI0LTlmMzQtMGQ3MGFkNzdmMDNj?sa=X&ved=0CAUQkfYCahcKEwjI-Zy-k-_rAhUAAAAAHQAAAAAQIQ"
                ,"kaz"
        ));

        podcastList.add(new Podcast(
                "",
                "janajaIQ",
                "köshbasshylyq bag̃darlama",
                "janajayik",
                "48 min",
                "https://podcasts.google.com/feed/aHR0cHM6Ly9hbmNob3IuZm0vcy8yNzI1ZDNlNC9wb2RjYXN0L3Jzcw/episode/Y2Y0Yjg5OGMtYmE2Ni00Mzg4LWJiNTQtNDNhZjg4YjU4MzY0?sa=X&ved=0CAUQkfYCahcKEwjI-Zy-k-_rAhUAAAAAHQAAAAAQIQ"
                ,"kaz"
        ));
    }

    public void getPodcastsFromFirebase(){
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    podcastFromFireList.clear();

                    for (DataSnapshot podcastData : dataSnapshot.getChildren()) {
                        Podcast podcast = podcastData.getValue(Podcast.class);
                        podcastFromFireList.add(podcast);
                    }

                    podcastList.addAll(podcastFromFireList);
                    Collections.reverse(podcastList);

                    podcastListCopy.clear();
                    podcastListCopy.addAll(podcastList);
                    podcastListAdapter.notifyDataSetChanged();
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

        tv_kaz.setText("Қазақша подкасттар");
        tv_rus.setText("Подкасты на русском");

        bookKaz.setOnClickListener(this);
        bookRus.setOnClickListener(this);

        filterDialog.setView(sortDialogView);
        filterDialog.show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.podcastLayout:

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.podcasts"));
                startActivity(i);

                break;
            case R.id.bookKaz:

                Predicate<Podcast> byKz = podcast -> podcast.getLanguage().equals("kaz");

                ArrayList angimeListFilter = (ArrayList<Podcast>) podcastListCopy.stream().filter(byKz).collect(Collectors.toList());
                podcastList.clear();
                podcastList.addAll(angimeListFilter);

                podcastListAdapter.notifyDataSetChanged();

                filterDialog.dismiss();
                break;
            case R.id.bookRus:

                Predicate<Podcast> byRus = podcast -> podcast.getLanguage().equals("rus");

                ArrayList angimeListFilter2 = (ArrayList<Podcast>) podcastListCopy.stream().filter(byRus).collect(Collectors.toList());
                podcastList.clear();
                podcastList.addAll(angimeListFilter2);

                podcastListAdapter.notifyDataSetChanged();

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
        podcastList.clear();
        if (text.isEmpty()) {
            podcastList.addAll(podcastListCopy);
        } else {
            text = text.toLowerCase();
            for (Podcast item : podcastListCopy) {

                if (item.getTitle().toLowerCase().contains(text) || item.getTopic().toLowerCase().contains(text) ||
                        item.getTitle().toUpperCase().contains(text) || item.getTopic().toUpperCase().contains(text)) {

                    podcastList.add(item);
                }
            }
        }
        podcastListAdapter.notifyDataSetChanged();
    }
}
