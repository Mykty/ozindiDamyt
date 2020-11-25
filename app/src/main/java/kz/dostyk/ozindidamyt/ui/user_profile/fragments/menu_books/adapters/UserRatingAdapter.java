package kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.user_profile.models.User;

public class UserRatingAdapter extends RecyclerView.Adapter<UserRatingAdapter.MyTViewHolder> {
    private Context context;
    public ArrayList<User> userList;
    String currectUserPhoneNumber;

    public static class MyTViewHolder extends RecyclerView.ViewHolder {
        public TextView userPoint, info, bookCount, userRating;
        CircleImageView person_photo;
        LinearLayout parentLinear;

        public MyTViewHolder(View view) {
            super(view);
            parentLinear = view.findViewById(R.id.parentLinear);
            person_photo = view.findViewById(R.id.person_photo);
            userPoint = view.findViewById(R.id.userPoint);
            info = view.findViewById(R.id.info);
            bookCount = view.findViewById(R.id.bookCount);
            userRating = view.findViewById(R.id.userRating);
        }

    }

    public UserRatingAdapter(Context context, ArrayList<User> userList) {
        this.context = context;
        this.userList = userList;
        Log.d("M_UserRatingdapter", "get current Firebase user");

//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (currentUser.getPhoneNumber() != null && currentUser.getPhoneNumber().length() > 0) { // phone login
//            currectUserPhoneNumber = currentUser.getPhoneNumber();
//        } else {
//            currectUserPhoneNumber = currentUser.getDisplayName();
//        }
    }

    @Override
    public MyTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rating_user, parent, false);

        return new MyTViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyTViewHolder holder, int position) {
        User item = userList.get(position);

//        if(currectUserPhoneNumber.equals(item.getPhoneNumber())){
//            holder.parentLinear.setBackgroundColor(context.getResources().getColor(R.color.light_green));
//        }else{
//            holder.parentLinear.setBackgroundColor(context.getResources().getColor(R.color.white));
//        }

        holder.userRating.setText("" + item.getUserRating());
        holder.userPoint.setText("" + item.getPoint());

        holder.info.setText(item.getInfo());
        holder.bookCount.setText("" + item.getBookCount());

        Glide.with(context.getApplicationContext())
                .load(item.getPhoto())
                .placeholder(R.drawable.user_def)
                .dontAnimate()
                .into(holder.person_photo);

//        int uTypeIcon = R.color.transparent;
//
//        switch (item.getUserType()) {
//            case "gold":
//                uTypeIcon = R.drawable.ic_gold;
//                break;
//            case "silver":
//                uTypeIcon = R.drawable.ic_silver;
//                break;
//            case "bronze":
//                uTypeIcon = R.drawable.ic_bronze;
//                break;
//        }

//        holder.userTypeIcon.setImageResource(uTypeIcon);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

}