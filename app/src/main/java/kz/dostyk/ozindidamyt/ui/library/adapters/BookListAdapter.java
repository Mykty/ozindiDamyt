package kz.dostyk.ozindidamyt.ui.library.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.library.activities.OneBookAcvitiy;
import kz.dostyk.ozindidamyt.ui.library.models.Book;
import kz.dostyk.ozindidamyt.ui.interfaces.ItemClickListener;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.MyTViewHolder>{
    private Context context;
    private List<Book> bookList;
    DateFormat dateF;
    String date;
    String number;
    onMeClick clickListener;
    private List<Book> exampleList;

    public interface onMeClick{
        void onClickThisUser(View view, int position);
    }

    public class MyTViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView book_photo;
        public TextView info, author;
        RatingBar bookRating;

        ItemClickListener clickListener;

        public MyTViewHolder(View view) {
            super(view);
            book_photo = view.findViewById(R.id.book_photo);
            info = view.findViewById(R.id.info);
            author = view.findViewById(R.id.author);
            bookRating = view.findViewById(R.id.bookRating);

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

    public BookListAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
        this.exampleList = bookList;

    }

    @Override
    public MyTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_grid, parent, false);
        manageDate();

        return new MyTViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyTViewHolder holder, int position) {

        Book item = bookList.get(position);

        Glide.with(context.getApplicationContext())
                .load(item.getPhoto())
                .placeholder(R.drawable.ic_book)
                .into(holder.book_photo);

        holder.info.setText(item.getName());
        holder.author.setText(item.getAuthor());

        String ratingSplit[] = item.getRating().split(",");
        int ratingFirstPart = Integer.parseInt(ratingSplit[0]);
        int ratingSecondPart = Integer.parseInt(ratingSplit[0]);

        holder.bookRating.setRating(ratingFirstPart);

        holder.setOnClick((v, pos) -> {

            Intent intent = new Intent(v.getContext(), OneBookAcvitiy.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("book", bookList.get(pos));
            intent.putExtras(bundle);
            v.getContext().startActivity(intent);

        });

    }

    public void manageDate() {
        dateF = new SimpleDateFormat("dd.MM");//2001.07.04
        date = dateF.format(Calendar.getInstance().getTime());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

}