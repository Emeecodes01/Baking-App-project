package com.emma.bakingapp.WidgetPackages;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.emma.bakingapp.BakingAppDataBase.BakingAppContract;
import com.emma.bakingapp.Models.RecipeModels;
import com.emma.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class WidgetRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory{
    private Context context;
    private Cursor mCursor;


    public WidgetRemoteViewFactory(Context context, Intent intent){
        this.context = context;
    }

    @Override
    public void onCreate() {
        mCursor = getCursor(context);
    }


    @Override
    public void onDataSetChanged() {
        mCursor = getCursor(context);

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        mCursor = getCursor(context);

        if (mCursor == null){
            return 0;
        }
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        mCursor = getCursor(context);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.list_item_widget_view);
        if (mCursor.moveToPosition(i)){
            String recipeName = mCursor.getString(mCursor.getColumnIndex(BakingAppContract.BakingAppContractFiles.RECIPE_NAME));
            String recipeIngredient = mCursor.getString(mCursor.getColumnIndex(BakingAppContract.BakingAppContractFiles.INGREDIENT));
            String recipeSteps = mCursor.getString(mCursor.getColumnIndex(BakingAppContract.BakingAppContractFiles.RECIPE_STEPS));

            remoteViews.setTextViewText(R.id.widget_recipe_name, recipeName);
            remoteViews.setTextViewText(R.id.widget_ingredient, recipeIngredient);
            remoteViews.setTextViewText(R.id.widget_recipe_steps, recipeSteps);

        }


        return remoteViews;
    }

    private Cursor getCursor(Context context) {

        mCursor = context.getContentResolver().query(BakingAppContract.BakingAppContractFiles.CONTENT_URI,
                null,
                null,
                null,
                BakingAppContract.BakingAppContractFiles._ID);

        return mCursor;
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(context.getPackageName(), R.layout.widget_loading_view);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
