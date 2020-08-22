package kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.models.Angime;


public class FavAngimelerAdapter extends RecyclerView.Adapter<FavAngimelerAdapter.MyTViewHolder> {
    private Context context;
    private List<Angime> angimeList;


    public class MyTViewHolder extends RecyclerView.ViewHolder{
        TextView aTitle;
        TextView aDesc;

        public MyTViewHolder(View view) {
            super(view);
            aTitle = view.findViewById(R.id.aTitle);
            aDesc = view.findViewById(R.id.aDesc);
        }
    }

    public FavAngimelerAdapter(Context context, List<Angime> angimeList) {
        this.context = context;
        this.angimeList = angimeList;
    }

    @Override
    public MyTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_angimeler, parent, false);

        return new MyTViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyTViewHolder holder, int position) {
        Angime item = angimeList.get(position);

        holder.aTitle.setText(item.getTitle());
        holder.aDesc.setText(item.getDesc());
    }

    @Override
    public int getItemCount() {
        return angimeList.size();
    }

}