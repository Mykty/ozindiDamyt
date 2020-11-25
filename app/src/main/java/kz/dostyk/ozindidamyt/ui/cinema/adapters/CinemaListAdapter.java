package kz.dostyk.ozindidamyt.ui.cinema.adapters;

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

public class CinemaListAdapter extends RecyclerView.Adapter<CinemaListAdapter.MyTViewHolder>{
    private Context context;
    private List<Cinema> cinemaList;
    DateFormat dateF;
    String date;
    String number;
    onMeClick clickListener;
    private List<Book> exampleList;

    public interface onMeClick{
        void onClickThisUser(View view, int position);
    }

    public class MyTViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView cinema_photo;
        public TextView info;
//        public TextView author;
//        RatingBar bookRating;

        ItemClickListener clickListener;

        public MyTViewHolder(View view) {
            super(view);
            cinema_photo = view.findViewById(R.id.c_photo);
            info = view.findViewById(R.id.info);

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

    public CinemaListAdapter(Context context, List<Cinema> cinemaList) {
        this.context = context;
        this.cinemaList = cinemaList;

    }

    @Override
    public MyTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cinema_grid, parent, false);

        return new MyTViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyTViewHolder holder, int position) {

        Cinema item = cinemaList.get(position);
        Glide.with(context.getApplicationContext())
                .load(item.getPhoto())
                .placeholder(R.drawable.ic_baseline_movie_24)
                .into(holder.cinema_photo);

        holder.info.setText(item.getName());

        holder.setOnClick((v, pos) -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(item.getLink()));
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return cinemaList.size();
    }

}