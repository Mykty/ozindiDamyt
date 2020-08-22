package kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.library.models.Book;
import kz.dostyk.ozindidamyt.ui.interfaces.ItemClickListener;
import kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.models.ProfileBook;


public class UserBookListAdapter extends RecyclerView.Adapter<UserBookListAdapter.MyTViewHolder> {
    private Context context;
    private List<ProfileBook> bookList;
    DateFormat dateF;
    String date;
    String number;
    ItemClickListener itemClickListener;

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class MyTViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView bName;
        TextView bAuthor;
        TextView bFinishedDate;
        ImageView bPhoto;

        public MyTViewHolder(View view) {
            super(view);
            bPhoto = view.findViewById(R.id.bPhoto);
            bName = view.findViewById(R.id.bName);
            bAuthor = view.findViewById(R.id.bAuthor);
            bFinishedDate = view.findViewById(R.id.bFinishedDate);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getLayoutPosition());
        }
    }

    public UserBookListAdapter(Context context, List<ProfileBook> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @Override
    public MyTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_book, parent, false);
        manageDate();
        return new MyTViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyTViewHolder holder, int position) {
        ProfileBook item = bookList.get(position);
        Book book = item.getBook();

        holder.bName.setText(book.getName());
        holder.bAuthor.setText(book.getAuthor());
        holder.bFinishedDate.setText("Бітірген күні: "+item.getFinishedDate());
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