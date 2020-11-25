package kz.dostyk.ozindidamyt.ui.hobby.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.util.List;

import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.hobby.models.Tatti;
import kz.dostyk.ozindidamyt.ui.interfaces.ItemClickListener;
import kz.dostyk.ozindidamyt.ui.library.models.Book;
import kz.dostyk.ozindidamyt.ui.podcast.models.Podcast;

public class TattiListAdapter extends RecyclerView.Adapter<TattiListAdapter.MyTViewHolder>{
    private Context context;
    private List<Tatti> tattiList;

    public class MyTViewHolder extends RecyclerView.ViewHolder{
        public ImageView tattiPhoto;
        public TextView tattiTitle, tattiDuration;

        public MyTViewHolder(View view) {
            super(view);
            tattiPhoto = view.findViewById(R.id.tattiPhoto);
            tattiTitle = view.findViewById(R.id.tattiTitle);
            tattiDuration = view.findViewById(R.id.tattiDuration);
        }
    }

    public TattiListAdapter(Context context, List<Tatti> tattiList) {
        this.context = context;
        this.tattiList = tattiList;
    }

    @Override
    public MyTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tatti, parent, false);

        return new MyTViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyTViewHolder holder, int position) {

        Tatti item = tattiList.get(position);

        Glide.with(context.getApplicationContext())
                .load(item.getPhoto())
                .placeholder(R.drawable.ic_baseline_movie_24)
                .into(holder.tattiPhoto);

        holder.tattiTitle.setText(item.getTitle());
        holder.tattiDuration.setText(item.getDuration());
    }

    @Override
    public int getItemCount() {
        return tattiList.size();
    }

}