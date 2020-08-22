package kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_daily;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.user_profile.UserProfileActivity;

public class DailyAppWidgetProvider extends AppWidgetProvider {

    private static final String bookLayoutClicked = "bookLayoutClicked";
    private static final String bookLayoutCancel = "bookLayoutCancel";
    private static final String MyOnClick2 = "myOnClickTag2";
    private static final String MyOnClick3 = "myOnClickTag3";
    DatabaseReference databaseReference;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


         for(int appWidget: appWidgetIds){
             Intent intent = new Intent(context, UserProfileActivity.class);

             PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
             RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.daily_widget);

             views.setOnClickPendingIntent(R.id.iv_cancel, getPendingSelfIntent(context, bookLayoutCancel));
             views.setOnClickPendingIntent(R.id.bookLayout, getPendingSelfIntent(context, bookLayoutClicked));
             views.setOnClickPendingIntent(R.id.iv_user_profile, pendingIntent);


             appWidgetManager.updateAppWidget(appWidget, views);
         }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
    int count = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.daily_widget);
        ComponentName watchWidget = new ComponentName(context, DailyAppWidgetProvider.class);

        if (bookLayoutClicked.equals(intent.getAction())) {

            databaseReference.child("iv_reading_yes").setValue("yes");
            remoteViews.setInt(R.id.bookLayout, "setBackgroundColor", context.getColor(R.color.dark_green));
            remoteViews.setInt(R.id.iv_cancel, "setVisibility", View.VISIBLE);

            remoteViews.setInt(R.id.iv_book_photo, "setImageResource", R.drawable.ic_reading_white);

            remoteViews.setInt(R.id.tv_book_title, "setTextColor", context.getColor(R.color.white));
            remoteViews.setInt(R.id.tv_book_name, "setTextColor", context.getColor(R.color.white));
            remoteViews.setInt(R.id.tv_book_count, "setTextColor", context.getColor(R.color.white));

        } else if (bookLayoutCancel.equals(intent.getAction())) {

            databaseReference.child("iv_reading_yes").setValue("no");
            remoteViews.setInt(R.id.bookLayout, "setBackgroundColor", context.getColor(R.color.widget_grey));
            remoteViews.setInt(R.id.iv_cancel, "setVisibility", View.INVISIBLE);

            remoteViews.setInt(R.id.iv_book_photo, "setImageResource", R.drawable.ic_reading);

            remoteViews.setInt(R.id.tv_book_title, "setTextColor", context.getColor(R.color.black));
            remoteViews.setInt(R.id.tv_book_name, "setTextColor", context.getColor(R.color.black));
            remoteViews.setInt(R.id.tv_book_count, "setTextColor", context.getColor(R.color.black));

        }

        else if (MyOnClick2.equals(intent.getAction())) {
            Toast.makeText(context, "Button2", Toast.LENGTH_SHORT).show();
            Log.w("Widget", "Clicked button2");
        } else if (MyOnClick3.equals(intent.getAction())) {
            Toast.makeText(context, "Button3", Toast.LENGTH_SHORT).show();
            Log.w("Widget", "Clicked button3");
        }


        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

}
