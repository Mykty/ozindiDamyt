package kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_daily;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.user_profile.UserProfileActivity;

public class DailyFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.tv_book_anim_point) TextView tv_book_anim_point;
    @BindView(R.id.tv_sport_anim_point) TextView tv_sport_anim_point;
    @BindView(R.id.tv_marathon_anim_point) TextView tv_marathon_anim_point;

    @BindView(R.id.iv_reading_yes) ImageView iv_reading_yes;
    @BindView(R.id.iv_reading_no) ImageView iv_reading_no;

    @BindView(R.id.iv_sport_yes) ImageView iv_sport_yes;
    @BindView(R.id.iv_sport_no) ImageView iv_sport_no;

    @BindView(R.id.iv_marathon_yes) ImageView iv_marathon_yes;
    @BindView(R.id.iv_marathon_no) ImageView iv_marathon_no;

    View root;
    DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile_daily, container, false);
        initViews();

        return root;
    }

    public void initViews() {
        ButterKnife.bind(this, root);
        iv_reading_yes.setOnClickListener(this);
        iv_reading_no.setOnClickListener(this);
        iv_sport_yes.setOnClickListener(this);
        iv_sport_no.setOnClickListener(this);
        iv_marathon_yes.setOnClickListener(this);
        iv_marathon_no.setOnClickListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_reading_yes:
                pressedYes(iv_reading_yes, iv_reading_no);
//                databaseReference.child("iv_reading_yes").setValue("yes");
                animateIncreasePoint(tv_book_anim_point);

                break;

            case R.id.iv_reading_no:
                pressedNo(iv_reading_no, iv_reading_yes);
                animateDecreasePoint(tv_book_anim_point);

                break;

            case R.id.iv_sport_yes:
                pressedYes(iv_sport_yes, iv_sport_no);
                animateIncreasePoint(tv_sport_anim_point);

                break;

            case R.id.iv_sport_no:
                pressedNo(iv_sport_no, iv_sport_yes);
                animateDecreasePoint(tv_sport_anim_point);
                break;

            case R.id.iv_marathon_yes:
                pressedYes(iv_marathon_yes, iv_marathon_no);
                animateIncreasePoint(tv_marathon_anim_point);

                break;

            case R.id.iv_marathon_no:
                pressedNo(iv_marathon_no, iv_marathon_yes);
                animateDecreasePoint(tv_marathon_anim_point);

                break;
        }
    }

    private void animateIncreasePoint(TextView textView){
        textView.setVisibility(View.VISIBLE);
        textView.setTextColor(getResources().getColor(R.color.dark_green));
        textView.setText("+5");
        textView.animate()
                .translationY(-300)
                .setDuration(1500)
                .alpha(0.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        UserProfileActivity.increasePoint();
//                        textView.setVisibility(View.INVISIBLE);
                    }
                });
    }

    private void animateDecreasePoint(TextView textView){
        textView.setVisibility(View.VISIBLE);
        textView.setTextColor(getResources().getColor(R.color.dark_red));
        textView.setText("-5");
        textView.animate()
                .translationY(-300)
                .setDuration(1500)
                .alpha(0.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        UserProfileActivity.increasePoint();
//                        textView.setVisibility(View.INVISIBLE);
                    }
                });
    }

    private void pressedYes(ImageView imageView1, ImageView imageView2){
//        imageView1.setImageResource(R.drawable.ic_check);
        imageView2.setVisibility(View.GONE);
        imageView1.setVisibility(View.VISIBLE);
    }

    private void pressedNo(ImageView imageView1, ImageView imageView2){
//        imageView1.setImageResource(R.drawable.ic_cancel);
        imageView2.setVisibility(View.GONE);
        imageView1.setVisibility(View.VISIBLE);
    }

}
