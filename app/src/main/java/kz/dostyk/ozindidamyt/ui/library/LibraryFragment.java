package kz.dostyk.ozindidamyt.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;

import java.util.function.Predicate;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.stream.Collectors;

import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.library.adapters.BookListAdapter;
import kz.dostyk.ozindidamyt.ui.library.models.Book;

public class LibraryFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private RecyclerView bookListRv;
    private BookListAdapter bookListAdapter;
    private ArrayList<Book> bookList;
    private ArrayList<Book> bookListCopy, bookListCopy2;
    private RecyclerView.LayoutManager linearLayoutManager, gridLayoutManager;
    Button btn_kz, btn_rus;
    SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_library, container, false);
        setupViews();
        initializeSearchView();

        return rootView;
    }

    public void setupViews() {
        bookListRv = rootView.findViewById(R.id.bookListRv);
        btn_kz = rootView.findViewById(R.id.btn_kz_lang);
        btn_rus = rootView.findViewById(R.id.btn_rus_lang);

        btn_kz.setOnClickListener(this);
        btn_rus.setOnClickListener(this);

        bookList = new ArrayList<>();
        bookListCopy = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(getActivity());
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);

//        Book(String firebaseKey, String name, String author, String desc, int page_number, String rating, String photo, String url, String imgStorageName)

        bookList.add(new Book("", "Абай Жолы", "Мұхтар Әуезов", "Desc", "kz", 100, "4,5", "", "", ""));
        bookList.add(new Book("", "Көшпенділер", "Ілияс Есенберлин", "Desc", "kz", 100, "4,5", "", "", ""));
        bookList.add(new Book("", "Қара Сөздер", "Абай Құнанбаев", "Desc", "kz", 100, "4,5", "", "", ""));

        bookList.add(new Book("", "Волоколамское шоссе", "Александра Бек", "Desc", "rus", 100, "4,5", "", "", ""));
        bookList.add(new Book("", "Путь Абая", "Мұхтар Әуезов", "Desc", "rus", 100, "4,5", "", "", ""));
        bookList.add(new Book("", "Война и Мир", "Лев Толстой", "Desc", "rus", 100, "4,5", "", "", ""));
        bookListCopy.addAll(bookList);

        bookListAdapter = new BookListAdapter(getActivity(), bookList);

        int resId = R.anim.layout_anim_book;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);

        bookListRv.setLayoutManager(linearLayoutManager);
        bookListRv.setItemAnimator(new DefaultItemAnimator());
        bookListRv.setLayoutAnimation(animation);
        bookListRv.setAdapter(bookListAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_kz_lang:
                btnPressedState(btn_kz);
                normalState(btn_rus);

                Predicate<Book> byKz = book -> book.getLanguage().equals("kz");

                ArrayList bookListFilter = (ArrayList<Book>) bookListCopy.stream().filter(byKz).collect(Collectors.toList());
                bookList.clear();
                bookList.addAll(bookListFilter);

                bookListAdapter.notifyDataSetChanged();
                break;

            case R.id.btn_rus_lang:
                normalState(btn_kz);
                btnPressedState(btn_rus);

                Predicate<Book> byRus = book -> book.getLanguage().equals("rus");

                ArrayList bookListFilter2 = (ArrayList<Book>) bookListCopy.stream().filter(byRus).collect(Collectors.toList());
                bookList.clear();
                bookList.addAll(bookListFilter2);

                bookListAdapter.notifyDataSetChanged();

                break;
        }
    }

    public void initializeSearchView(){
        searchView = rootView.findViewById(R.id.searchView);
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
    }

    public void filter(String text) {
        normalState(btn_kz);
        normalState(btn_rus);
        bookList.clear();
        if (text.isEmpty()) {
            bookList.addAll(bookListCopy);
        } else {
            text = text.toLowerCase();
            for (Book item : bookListCopy) {

                if (item.getName().toLowerCase().contains(text) || item.getAuthor().toLowerCase().contains(text) ||
                        item.getName().toUpperCase().contains(text) || item.getAuthor().toUpperCase().contains(text)) {

                    bookList.add(item);
                }
            }
        }
        bookListAdapter.notifyDataSetChanged();
    }

    public void btnPressedState(Button btn){
        btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        btn.setTextColor(getResources().getColor(R.color.white));
    }

    public void normalState(Button btn){
        btn.setBackgroundColor(getResources().getColor(R.color.white));
        btn.setTextColor(getResources().getColor(R.color.colorAccent));
    }
}
