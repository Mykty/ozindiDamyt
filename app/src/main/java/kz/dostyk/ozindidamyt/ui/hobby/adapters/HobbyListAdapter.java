package kz.dostyk.ozindidamyt.ui.hobby.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.hobby.models.Hobby;
import kz.dostyk.ozindidamyt.ui.interfaces.ItemClickListener;

public class HobbyListAdapter extends RecyclerView.Adapter<HobbyListAdapter.MyTViewHolder> {

    private Context context;
    private List<Hobby> groupList;
    TypedArray gradientStore, subjectStore;

    public class MyTViewHolder extends RecyclerView.ViewHolder {
        public TextView hobbyTitle, hobbyDesc;
//        hobbyLink,
        RelativeLayout gradientLayout;
        ImageView hobbyPhoto;

        public MyTViewHolder(View view) {
            super(view);
            hobbyTitle = view.findViewById(R.id.hobbyTitle);
            hobbyDesc = view.findViewById(R.id.hobbyDesc);
//            hobbyLink = view.findViewById(R.id.hobbyLink);
            hobbyPhoto = view.findViewById(R.id.hobbyPhoto);
            gradientLayout = view.findViewById(R.id.gradientLayout);

        }
    }

    public HobbyListAdapter(Context context, List<Hobby> groupList) {
        this.context = context;
        this.groupList = groupList;
        gradientStore = context.getResources().obtainTypedArray(R.array.gradientStore);
        subjectStore = context.getResources().obtainTypedArray(R.array.subjectStore);
    }

    @Override
    public MyTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hobby, parent, false);

        return new MyTViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyTViewHolder holder, int position) {

        final Hobby item = groupList.get(position);

        Glide.with(context.getApplicationContext())
                .load(item.getPhoto())
                .placeholder(R.drawable.subject1)
                .into(holder.hobbyPhoto);

        holder.hobbyTitle.setText(item.getTitle());
        holder.hobbyDesc.setText(item.getDesc());
//        holder.hobbyLink.setText(item.getLink());
        int gradientBack = 0;

        if(gradientStore.length() <= position) {
            int pos = position % gradientStore.length();
            gradientBack = gradientStore.getResourceId(pos, 0);

        }else{
            gradientBack = gradientStore.getResourceId(position, 0);
        }

        holder.gradientLayout.setBackground(context.getResources().getDrawable(gradientBack));

//        holder.setOnClick(new ItemClickListener() {
//            @Override
//            public void onItemClick(View v, int pos) {
//
//                Intent intent = new Intent(context, GroupUsersActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("groupName", item.getGroup_name());
//                bundle.putSerializable("groupId", item.getGroup_id());
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

}