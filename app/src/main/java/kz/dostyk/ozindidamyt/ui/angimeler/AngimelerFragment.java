package kz.dostyk.ozindidamyt.ui.angimeler;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.interfaces.RecyclerItemClickListener;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.adapters.FavAngimelerAdapter;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.models.Angime;

public class AngimelerFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.recyclerForBook)
    RecyclerView recyclerView;

    View view;
    ArrayList<Angime> angimeList;
    ArrayList<Angime> angimeListCopy;
    FavAngimelerAdapter favAngimelerAdapter;
    DatabaseReference mDatabaseRef;

    public AngimelerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        initViews();
        getAngimeFromFirebase();

        return view;
    }

    public void initViews() {
        ButterKnife.bind(this, view);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("angimeler");

        angimeList = new ArrayList<>();
        angimeListCopy = new ArrayList<>();
        String angimeler[] = getResources().getStringArray(R.array.angimeler);

        for (int i = 1; i < angimeler.length - 1; i += 2) {
            angimeList.add(new Angime(angimeler[i - 1], angimeler[i], "kaz"));
        }

        angimeListCopy.addAll(angimeList);

//        angimeList.add(new Angime("Ыбырай Алтынсарин (Асыл шөп)", "Злиха мен Бәтима деген біреудің қызметінде тұрған екі қыз бала төбесіне бір-бір жәшік жеміс көтеріп қалаға келе жатыпты-мыс. Злиха ахылап-ухілеп шаршадым деп, Бәтима күліп, әзілдесіп келе жатады. Сонда Злиха айтты:\n" +
//                "- Сен неге мәз болып қуанып келесің, төбеңдегі жәшіктің ауырлығыда менің басымдағыдан кем емес, өзіңде менен күшті емессің?\n" +
//                "- Мен жәшігімнің ішіне ауырды жеңілдететін бір шөп салдым деді Бәтима.\n" +
//                "- Ай, ондай болса шөбіңнің атын айтшы, мен де ауырымды жеңілдетейін деді Злиха\n" +
//                "- Ол шөп сенің қолыңа түспей ме деп қорқамын, аты: сабыр деген екен." +
//                ""));
//
//        angimeList.add(new Angime("Ыбырай Алтынсарин (Жаман жолдас)",
//                "Екi дос кiсi жолдастасып келе жатып, бiр аюға ұшырапты. Бұл екi кiсiнiң бiреуi әлсiз, ауру екен, екiншiсi мықты, жас жiгiт, аюды көрген соң бұл жiгiт ауру жолдасын тастап, өзi бiр үлкен ағаштың басына шығып кеттi дейдi. Ауру байғұс ағашқа шығуға дәрменi жоқ, жерге құлады да созылып, өлген кiсi болды да жатты: есiтуi бар едi, аю өлген кiсiге тимейдi деп. Аю бұл жатқан кiсiнiң қасына келiп иiскелеп тұрды да, дыбысы бiлiнбеген соң тастап жөнiне кеттi. Мұнан соң манағы жолдасы ағаштан түсiп, аурудан сұрапты:\n" +
//                "– Достым, аю құлағыңа не сыбырлап кеттi? Ауру айтты дейдi" +
//                "– Аю құлағыма ақыл сыбырлады, екiншi рет тар жерде жолдасын тастап қашатын достармен жолдас болма дедi, – дейдi."));

        favAngimelerAdapter = new FavAngimelerAdapter(getActivity(), angimeList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(favAngimelerAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int pos) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                        builder.setTitle(angimeList.get(pos).getTitle());
//                        builder.setMessage(angimeList.get(pos).getDesc());
//                        builder.show();

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.dialog_angimeler, null);
                        dialogBuilder.setView(dialogView);

                        TextView author = dialogView.findViewById(R.id.author);
                        TextView content = dialogView.findViewById(R.id.content);

                        author.setText(angimeList.get(pos).getTitle());
                        content.setText(angimeList.get(pos).getDesc());
                        content.setMovementMethod(new ScrollingMovementMethod());

                        ImageView iv_like = dialogView.findViewById(R.id.iv_like);
                        iv_like.setOnClickListener(view1 -> {
                            iv_like.setImageResource(R.drawable.ic_liked);
                        });

                        AlertDialog alertDialog = dialogBuilder.create();
                        alertDialog.show();

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

//    angimeList.add(new Angime("Ыбырай Алтынсарин (Асыл шөп)", "Злиха мен Бәтима деген біреудің қызметінде тұрған екі қыз бала төбесіне бір-бір жәшік жеміс көтеріп қалаға келе жатыпты-мыс. Злиха ахылап-ухілеп шаршадым деп, Бәтима күліп, әзілдесіп келе жатады. Сонда Злиха айтты:\n" +
//                "- Сен неге мәз болып қуанып келесің, төбеңдегі жәшіктің ауырлығыда менің басымдағыдан кем емес, өзіңде менен күшті емессің?\n" +
//                "- Мен жәшігімнің ішіне ауырды жеңілдететін бір шөп салдым деді Бәтима.\n" +
//                "- Ай, ондай болса шөбіңнің атын айтшы, мен де ауырымды жеңілдетейін деді Злиха\n" +
//                "- Ол шөп сенің қолыңа түспей ме деп қорқамын, аты: сабыр деген екен." +
//                ""));

    public void getAngimeFromFirebase() {
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot angimeData : dataSnapshot.getChildren()) {
                        Angime angime = angimeData.getValue(Angime.class);
                        angimeList.add(angime);
                    }

                    Collections.reverse(angimeList);

                    angimeListCopy.clear();
                    angimeListCopy.addAll(angimeList);
                    favAngimelerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bookKaz:

                Predicate<Angime> byKz = angime -> angime.getLanguage().equals("kaz");

                ArrayList angimeListFilter = (ArrayList<Angime>) angimeListCopy.stream().filter(byKz).collect(Collectors.toList());
                angimeList.clear();
                angimeList.addAll(angimeListFilter);

                favAngimelerAdapter.notifyDataSetChanged();

                filterDialog.dismiss();
                break;
            case R.id.bookRus:

                Predicate<Angime> byRus = angime -> angime.getLanguage().equals("rus");

                ArrayList angimeListFilter2 = (ArrayList<Angime>) angimeListCopy.stream().filter(byRus).collect(Collectors.toList());
                angimeList.clear();
                angimeList.addAll(angimeListFilter2);

                favAngimelerAdapter.notifyDataSetChanged();

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
        angimeList.clear();
        if (text.isEmpty()) {
            angimeList.addAll(angimeListCopy);
        } else {
            text = text.toLowerCase();
            for (Angime item : angimeListCopy) {

                if (item.getTitle().toLowerCase().contains(text) || item.getDesc().toLowerCase().contains(text) ||
                        item.getTitle().toUpperCase().contains(text) || item.getDesc().toUpperCase().contains(text)) {

                    angimeList.add(item);
                }
            }
        }
        favAngimelerAdapter.notifyDataSetChanged();
    }

    View sortDialogView;
    android.app.AlertDialog filterDialog;

    public void filterDialog() {
        LayoutInflater factory = LayoutInflater.from(getActivity());

        sortDialogView = factory.inflate(R.layout.dialog_filter_language, null);
        filterDialog = new android.app.AlertDialog.Builder(getActivity()).create();

        LinearLayout bookKaz = sortDialogView.findViewById(R.id.bookKaz);
        LinearLayout bookRus = sortDialogView.findViewById(R.id.bookRus);

        bookKaz.setOnClickListener(this);
        bookRus.setOnClickListener(this);

        filterDialog.setView(sortDialogView);
        filterDialog.show();
    }

}
