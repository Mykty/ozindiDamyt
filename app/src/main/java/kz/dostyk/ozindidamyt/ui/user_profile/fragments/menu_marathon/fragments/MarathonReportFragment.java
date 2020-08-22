package kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_marathon.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.interfaces.RecyclerItemClickListener;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_marathon.adapters.MarathonReportAdapter;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_sport.models.Marathon;

public class MarathonReportFragment extends Fragment {
    @BindView(R.id.recyclerForBook) RecyclerView recyclerView;

    View view;
    ArrayList<Marathon> marathons;
    MarathonReportAdapter marathonReportAdapter;

    public MarathonReportFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        initViews();

        return view;
    }

    public void initViews() {
        ButterKnife.bind(this, view);
        marathons = new ArrayList<>();
        marathons.add(new Marathon("30 күнде 30 тест", "Тамыз - Қыркуйек", "550"));
        marathons.add(new Marathon("Көктемгі марафон", "Мамыр - Маусым", "400"));
        marathons.add(new Marathon("Күзгі марафон", "Қыркуйек - Қазан", "450"));

        marathonReportAdapter = new MarathonReportAdapter(getActivity(), marathons);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(marathonReportAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int pos) {

                        /*
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(getActivity());
                        builder.setTitle(angimeList.get(pos).getTitle());
                        builder.setMessage(angimeList.get(pos).getDesc());
                        builder.show();


                         */
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }
}
