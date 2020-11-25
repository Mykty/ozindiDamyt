package kz.dostyk.ozindidamyt.ui.hobby.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.hobby.adapters.TattiListAdapter;
import kz.dostyk.ozindidamyt.ui.hobby.models.Tatti;
import kz.dostyk.ozindidamyt.ui.interfaces.RecyclerItemClickListener;

public class TattiActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager gridLayoutManager;
    TattiListAdapter tattiListAdapter;
    ArrayList<Tatti> tattiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tattiler);
        setTitle("Тәтті пісіру видеолары");
        initViews();

    }

    public void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        tattiList = new ArrayList<>();

        initTattis();

        tattiListAdapter = new TattiListAdapter(this, tattiList);
        gridLayoutManager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tattiListAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int pos) {
                        String link = tattiList.get(pos).getLink();

                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(link));
                        startActivity(i);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    public void initTattis() {
        tattiList.add(new Tatti("",
                "Молочная девочка",
                "19 min",
                "https://www.youtube.com/watch?v=PQG2Kf56w-0&feature=youtu.be",
                R.drawable.sutti_kyz));

        tattiList.add(new Tatti("",
                "Ең оңай \"Фруктовый торт\"",
                "20 min",
                "https://www.youtube.com/watch?v=MWVSyoOPH_Y&feature=youtu.be",
                R.drawable.fruktovyi));

        tattiList.add(new Tatti("",
                "Ең дәмді \"Вупи пай\"",
                "13 min",
                "https://www.youtube.com/watch?v=Z0tS7YQ_DXY&feature=youtu.be",
                R.drawable.vupi));

        tattiList.add(new Tatti("",
                "торт \"Шагане\"",
                "16 min",
                "https://www.youtube.com/watch?v=2M8z9xqk8kc&feature=youtu.be",
                R.drawable.shagane));

        tattiList.add(new Tatti("",
                "Радужный торт",
                "26 min",
                "https://www.youtube.com/watch?v=-S0xC8gcGBE&feature=youtu.be",
                R.drawable.radujnyi));
    }

}
