package kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_quiz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.models.Angime;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_quiz.models.Quiz;


public class QuizReportAdapter extends RecyclerView.Adapter<QuizReportAdapter.MyTViewHolder> {
    private Context context;
    private List<Quiz> angimeList;


    public class MyTViewHolder extends RecyclerView.ViewHolder{
        TextView qTitle;
        TextView qDate;
        TextView qPoint;

        public MyTViewHolder(View view) {
            super(view);
            qTitle = view.findViewById(R.id.qTitle);
            qDate = view.findViewById(R.id.qDate);
            qPoint = view.findViewById(R.id.qPoint);
        }
    }

    public QuizReportAdapter(Context context, List<Quiz> angimeList) {
        this.context = context;
        this.angimeList = angimeList;
    }

    @Override
    public MyTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_report, parent, false);

        return new MyTViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyTViewHolder holder, int position) {
        Quiz item = angimeList.get(position);

        holder.qTitle.setText(item.getTitle());
        holder.qDate.setText(item.getFinishedDate());
        holder.qPoint.setText(item.getEarnedPoint());
    }

    @Override
    public int getItemCount() {
        return angimeList.size();
    }

}