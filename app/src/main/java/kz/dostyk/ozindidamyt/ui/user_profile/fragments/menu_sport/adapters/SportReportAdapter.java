package kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_sport.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_sport.models.Sport;


public class SportReportAdapter extends RecyclerView.Adapter<SportReportAdapter.MyTViewHolder> {
    private Context context;
    private List<Sport> sportList;


    public class MyTViewHolder extends RecyclerView.ViewHolder{
        TextView sName;
        TextView sDate;
        TextView sTime;

        public MyTViewHolder(View view) {
            super(view);
            sName = view.findViewById(R.id.sName);
            sDate = view.findViewById(R.id.sDate);
            sTime = view.findViewById(R.id.sTime);
        }
    }

    public SportReportAdapter(Context context, List<Sport> sportList) {
        this.context = context;
        this.sportList = sportList;
    }

    @Override
    public MyTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sport_report, parent, false);

        return new MyTViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyTViewHolder holder, int position) {
        Sport item = sportList.get(position);

        holder.sName.setText(item.getName());
        holder.sDate.setText(item.getDate());
        holder.sTime.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return sportList.size();
    }

}