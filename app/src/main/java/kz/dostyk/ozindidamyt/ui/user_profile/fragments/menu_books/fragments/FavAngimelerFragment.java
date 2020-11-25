package kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.interfaces.RecyclerItemClickListener;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.adapters.FavAngimelerAdapter;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.models.Angime;


public class FavAngimelerFragment extends Fragment {
    @BindView(R.id.recyclerForBook) RecyclerView recyclerView;

    View view;
    ArrayList<Angime> angimeList;
    FavAngimelerAdapter favAngimelerAdapter;

    public FavAngimelerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        initViews();

        return view;
    }


    public void initViews() {
        ButterKnife.bind(this, view);
        angimeList = new ArrayList<>();

//        angimeList.add(new Angime("Ыбырай Алтынсарин (Асыл шөп)", "Злиха мен Бәтима деген біреудің қызметінде тұрған екі қыз бала төбесіне бір-бір жәшік жеміс көтеріп қалаға келе жатыпты-мыс. Злиха ахылап-ухілеп шаршадым деп, Бәтима күліп, әзілдесіп келе жатады. Сонда Злиха айтты:\n" +
//                "- Сен неге мәз болып қуанып келесің, төбеңдегі жәшіктің ауырлығыда менің басымдағыдан кем емес, өзіңде менен күшті емессің?\n" +
//                "- Мен жәшігімнің ішіне ауырды жеңілдететін бір шөп салдым деді Бәтима.\n" +
//                "- Ай, ондай болса шөбіңнің атын айтшы, мен де ауырымды жеңілдетейін деді Злиха\n" +
//                "- Ол шөп сенің қолыңа түспей ме деп қорқамын, аты: сабыр деген екен." +
//                ""));
//        angimeList.add(new Angime("Ыбырай Алтынсарин (Жаман жолдас)", "Екi дос кiсi жолдастасып келе жатып, бiр аюға ұшырапты. Бұл екi кiсiнiң бiреуi әлсiз, ауру екен, екiншiсi мықты, жас жiгiт, аюды көрген соң бұл жiгiт ауру жолдасын тастап, өзi бiр үлкен ағаштың басына шығып кеттi дейдi. Ауру байғұс ағашқа шығуға дәрменi жоқ, жерге құлады да созылып, өлген кiсi болды да жатты: есiтуi бар едi, аю өлген кiсiге тимейдi деп. Аю бұл жатқан кiсiнiң қасына келiп иiскелеп тұрды да, дыбысы бiлiнбеген соң тастап жөнiне кеттi. Мұнан соң манағы жолдасы ағаштан түсiп, аурудан сұрапты:\n" +
//                "\n" +
//                "– Достым, аю құлағыңа не сыбырлап кеттi? Ауру айтты дейдi:\n" +
//                "\n" +
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
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(getActivity());
                        builder.setTitle(angimeList.get(pos).getTitle());
                        builder.setMessage(angimeList.get(pos).getDesc());
                        builder.show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }
}
