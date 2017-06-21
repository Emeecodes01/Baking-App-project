package com.emma.bakingapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.emma.bakingapp.Adapters.CustomListAdapter;
import com.emma.bakingapp.Models.IngeredientsResponse;
import com.emma.bakingapp.Models.RecipeModels;
import com.emma.bakingapp.Utils.ToastMessageUtil;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {
    private List<IngeredientsResponse> responseList;
    private RecyclerView mRecyclerView;
    private Button lesson_btn;
    private String title;
    private ImageView recipeImage;
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.recipe_toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recipe_detail_rv);
        lesson_btn = (Button) findViewById(R.id.try_button);
        recipeImage = (ImageView) findViewById(R.id.recipe_image);

        //gets the parcelable arraylist form the intent
        try {

            i = getIntent();

        }catch (NullPointerException e){
             e.printStackTrace();
            ToastMessageUtil.getToastMessage(getApplicationContext(), e.toString());
        }

        final RecipeModels RecipeModels = i.getParcelableExtra("recipesModels");
        title = RecipeModels.getName();
        responseList = RecipeModels.getIngredientsResponse();
        recipeImage.setImageResource(i.getExtras().getInt("imageRes"));

        //sets the toolbar dynamically
        setToolBar(toolbar, title);
        setUpListView(responseList);

        //respond to on click event
        lesson_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeDetailActivity.this, VideoTutorialActivity.class);
                intent.putExtra("recipeModel", RecipeModels);
                startActivity(intent);
            }
        });
    }

    private void setUpListView(List<IngeredientsResponse> responseList) {

        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.single_ingre_item, responseList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);

    }

    private void setToolBar(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        toolbar.setTitle(title);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

}