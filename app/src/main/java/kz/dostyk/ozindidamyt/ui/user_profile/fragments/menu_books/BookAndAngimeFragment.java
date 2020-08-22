package kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.fragments.FavAngimelerFragment;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.fragments.MyBooksFragment;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.fragments.UserRatingByBooksFragment;

public class BookAndAngimeFragment extends Fragment implements View.OnClickListener {
    private View root;
    private DatabaseReference databaseReference;

    @BindView(R.id.view_pager2) ViewPager2 viewPager;
    @BindView(R.id.bookTabLayout) TabLayout tabLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_view_pager, container, false);
        initViews();

        return root;
    }

    public void initViews() {
        ButterKnife.bind(this, root);
        databaseReference = FirebaseDatabase.getInstance().getReference();

//        tabLayout.setupWithViewPager(viewPager);


        viewPager.setAdapter(new ViewPagerFragmentAdapter(getActivity()));
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(titles[position])).attach();
    }

    @Override
    public void onClick(View view) {

    }

    private String[] titles = new String[]{"Кітаптарым", "Әңгімелер", "Рейтинг"};

    private class ViewPagerFragmentAdapter extends FragmentStateAdapter {

        public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new MyBooksFragment();
                case 1:
                    return new FavAngimelerFragment();
                case 2:
                    return new UserRatingByBooksFragment();
            }

            return new MyBooksFragment();
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }
}
