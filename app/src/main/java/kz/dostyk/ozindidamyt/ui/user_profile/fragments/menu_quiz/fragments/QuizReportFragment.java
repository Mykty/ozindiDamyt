package kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_quiz.fragments;

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
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_quiz.adapters.QuizReportAdapter;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_quiz.models.Quiz;

public class QuizReportFragment extends Fragment {
    @BindView(R.id.recyclerForBook) RecyclerView recyclerView;

    View view;
    ArrayList<Quiz> quizList;
    QuizReportAdapter quizReportAdapter;

    public QuizReportFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        initViews();

        return view;
    }

    public void initViews() {
        ButterKnife.bind(this, view);
        quizList = new ArrayList<>();
        quizList.add(new Quiz("Тарих", "6 Тамыз", "100/100 ұпай"));
        quizList.add(new Quiz("Математика", "3 Тамыз", "80/100 ұпай"));
        quizList.add(new Quiz("Физика", "1 Тамыз", "90/100 ұпай"));

        quizReportAdapter = new QuizReportAdapter(getActivity(), quizList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(quizReportAdapter);

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
