package com.example.apsandhu.jsonparsingtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class cast extends AppCompatActivity implements CallBackMe, castAllMoviesAdapter.OnItemClickListener {

    ImageView imageView;
    TextView Name;
    TextView dateofbirth;
    TextView placeofbirth;
    TextView popularity;
    TextView genre;
    TextView bio;

    Context context;
    private RecyclerView mRecyclerView;
    castAllMoviesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast);
        mRecyclerView = findViewById(R.id.recyclerView1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        context=this;

        Intent intentFromMovieDetail=getIntent();
        int castId =intentFromMovieDetail.getIntExtra("key",0);
        this.imageView=findViewById(R.id.imageView);
        this.Name=findViewById(R.id.Name);
        this.dateofbirth=findViewById(R.id.dateofbirth);
        this.placeofbirth=findViewById(R.id.placeofbirth);
        this.popularity=findViewById(R.id.popularity);
        this.genre=findViewById(R.id.genre);
        this.bio=findViewById(R.id.bio);
        String url="https://api.themoviedb.org/3/person/"+castId+"?api_key=e9502953a406241d396c224e783c19b6&language=en-US";
        Log.w("myApp", "$$$$$$$$$$$$$$$"+url);
        JsonRetriever.RetrieveFromURL(this,url,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        //  RecyclerView recyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter=new castAllMoviesAdapter(this,castId);

        try {
            TimeUnit.MILLISECONDS.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // test231 adapter = new test231(MainActivity.this, upName,upImg);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(cast.this);


    }

    @Override
    public void CallThis(String jsonText) {
        try {
            JSONObject json = new JSONObject(jsonText);

            //Set Avatar Image From URL
            new DownloadImageTask(imageView).execute("https://image.tmdb.org/t/p/w500"+json.getString("profile_path"));
           Name.setText(json.getString("name"));
            dateofbirth.setText(json.getString("birthday"));
            placeofbirth.setText(json.getString("place_of_birth"));
            popularity.setText(""+json.getDouble("popularity"));
//            genre.setText(json.getInt("gender"));
            bio.setText(json.getString("biography"));




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(cast.this, MovieDetail.class);
        int id = adapter.getIdCast(position);
        detailIntent.putExtra("key", id);
        // test231 clickedItem = kill.get(position);
        startActivity(detailIntent);

    }
}
