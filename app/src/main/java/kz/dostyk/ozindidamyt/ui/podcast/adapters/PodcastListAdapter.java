package kz.dostyk.ozindidamyt.ui.podcast.adapters;

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
import kz.dostyk.ozindidamyt.ui.cinema.models.Cinema;
import kz.dostyk.ozindidamyt.ui.library.models.Book;
import kz.dostyk.ozindidamyt.ui.interfaces.ItemClickListener;
import kz.dostyk.ozindidamyt.ui.podcast.models.Podcast;

public class PodcastListAdapter extends RecyclerView.Adapter<PodcastListAdapter.MyTViewHolder>{
    private Context context;
    private List<Podcast> podcastList;
    DateFormat dateF;
    String date;
    String number;
    onMeClick clickListener;
    private List<Book> exampleList;

    public interface onMeClick{
        void onClickThisUser(View view, int position);
    }

    public class MyTViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView podPhoto;
        public TextView title, topic, duration;
//        public TextView author;
//        RatingBar bookRating;

        ItemClickListener clickListener;

        public MyTViewHolder(View view) {
            super(view);
            podPhoto = view.findViewById(R.id.podPhoto);
            title = view.findViewById(R.id.title);
            topic = view.findViewById(R.id.topic);
            duration = view.findViewById(R.id.duration);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.clickListener.onItemClick(view,getLayoutPosition());
        }

        public void setOnClick(ItemClickListener clickListener){
            this.clickListener = clickListener;
        }
    }

    public PodcastListAdapter(Context context, List<Podcast> podcastList) {
        this.context = context;
        this.podcastList = podcastList;

    }

    @Override
    public MyTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_podcast, parent, false);

        return new MyTViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyTViewHolder holder, int position) {

        Podcast item = podcastList.get(position);

        String imgUrl = "janajayik";

        if(item.getPhoto().contains("janajayik")){
            Glide.with(context.getApplicationContext())
                    .load(R.drawable.janajaik_pod_icon)
                    .placeholder(R.drawable.ic_baseline_movie_24)
                    .into(holder.podPhoto);

        }else{
            Glide.with(context.getApplicationContext())
                    .load(item.getPhoto())
                    .placeholder(R.drawable.ic_baseline_movie_24)
                    .into(holder.podPhoto);
        }

        holder.title.setText(item.getTitle());
        holder.topic.setText(item.getTopic());
        holder.duration.setText(item.getDuration());

        holder.setOnClick((v, pos) -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(item.getLink()));
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return podcastList.size();
    }

}