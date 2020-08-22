package kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.library.models.Book;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.adapters.UserBookListAdapter;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.models.ProfileBook;


public class MyBooksFragment extends Fragment {
    @BindView(R.id.recyclerForBook) RecyclerView recyclerView;
    @BindView(R.id.currentBName) TextView currentBName;
    @BindView(R.id.currentBAuthor) TextView currentBAuthor;
    @BindView(R.id.currentBProgress) ProgressBar currentBProgress;
    @BindView(R.id.currentBPageCount) TextView currentBPageCount;

    View view;
    ArrayList<ProfileBook> bookList;
    UserBookListAdapter bookAdapter;

    public MyBooksFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_books, container, false);
        initViews();
        initReadingBook();

        return view;
    }


    public void initViews() {
        ButterKnife.bind(this, view);
        bookList = new ArrayList<>();
        bookList.add(new ProfileBook(new Book("123", "Көшпенділер", "Ілияс Есенберлин"), "3 Тамыз"));
        bookList.add(new ProfileBook(new Book("123", "Ұшқан ұя", "Бауыржан Момышұлы"), "1 Тамыз"));

        bookAdapter = new UserBookListAdapter(getActivity(), bookList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(bookAdapter);
    }

    @SuppressLint("SetTextI18n")
    private void initReadingBook() {
        currentBName.setText("Абай жолы");
        currentBAuthor.setText("Мұхтар Әуезов");
        currentBPageCount.setText("420 бет қалды");
    }
}
