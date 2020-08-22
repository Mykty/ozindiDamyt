package kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_marathon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_quiz.models.Quiz;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_sport.models.Marathon;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_sport.models.Sport;


public class MarathonReportAdapter extends RecyclerView.Adapter<MarathonReportAdapter.MyTViewHolder> {
    private Context context;
    private List<Marathon> marathonList;


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

    public MarathonReportAdapter(Context context, List<Marathon> marathonList) {
        this.context = context;
        this.marathonList = marathonList;
    }

    @Override
    public MyTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_report, parent, false);

        return new MyTViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyTViewHolder holder, int position) {
        Marathon item = marathonList.get(position);

        holder.qTitle.setText(item.getTitle());
        holder.qDate.setText(item.getFinishedDate());
        holder.qPoint.setText(item.getEarnedPoint());
    }

    @Override
    public int getItemCount() {
        return marathonList.size();
    }

}