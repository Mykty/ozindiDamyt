package kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_quiz.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.adapters.UserRatingAdapter;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.models.User;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_quiz.adapters.QuizRatingAdapter;

public class RatingByQuizFragment extends Fragment /*implements View.OnClickListener */{
    private DatabaseReference mDatabaseRef, ratingUserRef;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView.LayoutManager linearLayoutManager;
    private ArrayList<User> userListCopy;
    private ArrayList<User> userList;
    private SearchView searchView;
    private View view;
    private ProgressBar progressBar;
    private QuizRatingAdapter quizRatingAdapter;
    private String TABLE_USER = "user_store";
    private View filterDialogView;
    private AlertDialog filterDialog;
    private View progressLoading;
    private ArrayList<User> goldUsers, silverUsers, bronzeUsers, otherUsers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rating_user, container, false);

        initView();
//        getRatingUsers();
//        filterDialog();

        return view;
    }

    public void initView() {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        ratingUserRef = mDatabaseRef.child("rating_users");
        userList = new ArrayList<>();
        goldUsers = new ArrayList<>();
        silverUsers = new ArrayList<>();
        bronzeUsers = new ArrayList<>();
        otherUsers = new ArrayList<>();

        progressBar = view.findViewById(R.id.ProgressBar);
        progressLoading = view.findViewById(R.id.llProgressBar);

        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        int resId = R.anim.layout_anim;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutAnimation(animation);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        userList.add(new User("Мақсат", "", "", "", "", "", 1, 130, 10));
        userList.add(new User("Арлан", "", "", "", "", "", 2, 100, 7));
        userList.add(new User("Қатипаш", "", "", "", "", "", 3, 20, 5));

        quizRatingAdapter = new QuizRatingAdapter(getActivity(), userList);
        recyclerView.setAdapter(quizRatingAdapter);

        searchView = view.findViewById(R.id.searchView);
        userListCopy = new ArrayList<>();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                filter(s);
                return false;
            }
        });
        progressLoading.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
//        setupSwipeRefresh();
    }
    /*
    int userTypeRating = 0;

    private User getUserFromDb(String userPhone) {
        Cursor userCursor = storeDb.getUserEntry(userPhone);

        if (((userCursor != null) && (userCursor.getCount() > 0))) {
            userCursor.moveToFirst();
            Log.d("M_UserRatingFragment", "db user: "+userCursor.getString(userCursor.getColumnIndex(COLUMN_INFO)));
            return new User(
                    userTypeRating--,
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_INFO)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_EMAIL)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_PHONE)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_GROUP_ID)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_GROUP)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_PHOTO)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_ENTER_DATE)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_USER_TYPE)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_IMG_STORAGE_NAME)),
                    userCursor.getInt(userCursor.getColumnIndex(COLUMN_BCOUNT)),
                    userCursor.getInt(userCursor.getColumnIndex(COLUMN_POINT)),
                    userCursor.getInt(userCursor.getColumnIndex(COLUMN_REVIEW_SUM)),
                    userCursor.getInt(userCursor.getColumnIndex(COLUMN_RAINTING_IN_GROUPS))
            );
        }

        return null;
    }

    private void getRatingUsers() {
        userList.clear();

        Query goldQuery = ratingUserRef.child("a_gold").orderByChild("point");
        goldQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    goldUsers.clear();
                    userTypeRating = (int) dataSnapshot.getChildrenCount();
                    for (DataSnapshot userData : dataSnapshot.getChildren()) {
                        String userPhone = userData.getKey();
                        User gUser = getUserFromDb(userPhone);
                        goldUsers.add(gUser);
                    }

                    Collections.reverse(goldUsers);
                    userList.addAll(goldUsers);
                    userRatingdapter.notifyDataSetChanged();
                    getSilverUsers();
                } else {
                    getSilverUsers();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void getSilverUsers() {
        Query silverQuery = ratingUserRef.child("b_silver").orderByChild("point");
        Log.d("M_UserRatingFragment", "b_silver");
        silverQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userTypeRating = (int) dataSnapshot.getChildrenCount();
                    silverUsers.clear();
                    for (DataSnapshot userData : dataSnapshot.getChildren()) {
                        String userPhone = userData.getKey();
                        silverUsers.add(getUserFromDb(userPhone));
                    }
                    Collections.reverse(silverUsers);
                    userList.addAll(silverUsers);
                    userRatingdapter.notifyDataSetChanged();
                    getBronzeUsers();
                } else {
                    getBronzeUsers();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getBronzeUsers() {
        Query bronzeQuery = ratingUserRef.child("c_bronze").orderByChild("point");
        Log.d("M_UserRatingFragment", "bronze");
        bronzeQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userTypeRating = (int) dataSnapshot.getChildrenCount();
                    bronzeUsers.clear();
                    for (DataSnapshot userData : dataSnapshot.getChildren()) {
                        String userPhone = userData.getKey();
                        bronzeUsers.add(getUserFromDb(userPhone));
                    }
                    Collections.reverse(bronzeUsers);
                    userList.addAll(bronzeUsers);
                    userRatingdapter.notifyDataSetChanged();
                    getOtherUsers();
                } else {
                    getOtherUsers();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void getOtherUsers() {
        Query otherQuery = ratingUserRef.child("other").orderByChild("point");
        Log.d("M_UserRatingFragment", "other");
        otherQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userTypeRating = (int) dataSnapshot.getChildrenCount();
                    otherUsers.clear();
                    for (DataSnapshot userData : dataSnapshot.getChildren()) {
                        String userPhone = userData.getKey();
                        otherUsers.add(getUserFromDb(userPhone));
                    }
                    Collections.reverse(otherUsers);
                    userList.addAll(otherUsers);
                    userListCopy = (ArrayList<User>) userList.clone();
                    userRatingdapter.notifyDataSetChanged();
                    progressLoading.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    public void filter(String text) {
        userList.clear();
        if (text.isEmpty()) {
            userList.addAll(userListCopy);
        } else {
            text = text.toLowerCase();
            for (User item : userListCopy) {
                if (item.getInfo().toLowerCase().contains(text) || item.getInfo().toUpperCase().contains(text) ||
                        item.getGroupName().toUpperCase().contains(text) || item.getGroupName().toLowerCase().contains(text)) {
                    userList.add(item);
                }
            }
        }
        recyclerView.setAdapter(userRatingdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();

        inflater.inflate(R.menu.menu_card_read, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.login_page:

                break;

            case R.id.filter_user:

                filterDialog.show();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void filterDialog() {
        LayoutInflater factory = LayoutInflater.from(getActivity());

        filterDialogView = factory.inflate(R.layout.dialog_rating_user_filter, null);
        filterDialog = new AlertDialog.Builder(getActivity()).create();

        LinearLayout filter_all = filterDialogView.findViewById(R.id.filter_all);
        LinearLayout filter_gold = filterDialogView.findViewById(R.id.filter_gold);
        LinearLayout filter_silver = filterDialogView.findViewById(R.id.filter_silver);
        LinearLayout filter_bronze = filterDialogView.findViewById(R.id.filter_bronze);
        LinearLayout filter_other = filterDialogView.findViewById(R.id.filter_other);

        filter_all.setOnClickListener(this);
        filter_gold.setOnClickListener(this);
        filter_silver.setOnClickListener(this);
        filter_bronze.setOnClickListener(this);
        filter_other.setOnClickListener(this);

        filterDialog.setView(filterDialogView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter_all:
                userList.clear();
                userList.addAll(goldUsers);
                userList.addAll(silverUsers);
                userList.addAll(bronzeUsers);
                userList.addAll(otherUsers);
                filterFinalCase();

                break;
            case R.id.filter_gold:

                userList.clear();
                userList.addAll(goldUsers);
                filterFinalCase();

                break;
            case R.id.filter_silver:

                userList.clear();
                userList.addAll(silverUsers);
                filterFinalCase();

                break;
            case R.id.filter_bronze:

                userList.clear();
                userList.addAll(bronzeUsers);
                filterFinalCase();

                break;
            case R.id.filter_other:

                userList.clear();
                userList.addAll(otherUsers);
                filterFinalCase();
                break;
        }
    }

    private void filterFinalCase(){
        userListCopy = (ArrayList<User>) userList.clone();
        userRatingdapter.notifyDataSetChanged();
        filterDialog.dismiss();
    }
    public void setupSwipeRefresh() {
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });
    }

    public void refreshItems() {
        if (isOnline()) {
            getRatingUsers();
        }
    }

    private boolean isOnline() {
        if (isNetworkAvailable()) {
            return true;

        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.inetConnection), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    */
}