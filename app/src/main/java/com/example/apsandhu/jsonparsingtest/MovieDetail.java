package com.example.apsandhu.jsonparsingtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class MovieDetail extends AppCompatActivity implements CallBackMe, CastRecyclerAdapter.OnItemClickListener {

    ImageView poster;
    TextView title;
    TextView language;
    TextView runtime;
    TextView date;
    TextView genre;
    TextView overview;
    TextView director;
    TextView actors;
    TextView cast;
    Context context;
    private RecyclerView mRecyclerView;
    CastRecyclerAdapter adapter;
  //  private RequestQueue mRequestQueue;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        context=this;
          mRecyclerView = findViewById(R.id.recyclerView1);
          mRecyclerView.setHasFixedSize(true);
          mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

         // mRequestQueue = Volley.newRequestQueue(this);      //  mExampleList = new ArrayList<>();
       //   mRequestQueue = Volley.newRequestQueue(this);
          Intent kamal=getIntent();
          int id =kamal.getIntExtra("key",0);
         this.poster=findViewById(R.id.poster);
          this.title=findViewById(R.id.title);
          this.language=findViewById(R.id.language);
          this.runtime=findViewById(R.id.runtime);
          this.date=findViewById(R.id.date);
          this.genre=findViewById(R.id.genre);
          this.overview=findViewById(R.id.overview);
          this.director=findViewById(R.id.director);
          this.actors=findViewById(R.id.actors);
          this.cast=findViewById(R.id.cast);
          String url="https://api.themoviedb.org/3/movie/"+id+"?api_key=e9502953a406241d396c224e783c19b6&language=en-US";
          Log.w("myApp", "1111111111111111@@@@@@@@@@@@@@@@!!!!!!!!!!!!!!!!!!!!!!!!!!"+url);

              JsonRetriever.RetrieveFromURL(this,url,this);

          LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
          //  RecyclerView recyclerView = findViewById(R.id.recyclerView);
          mRecyclerView.setLayoutManager(layoutManager);
           adapter=new CastRecyclerAdapter(this,id);

          try {
              TimeUnit.MILLISECONDS.sleep(2500);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          // test231 adapter = new test231(MainActivity.this, upName,upImg);
          mRecyclerView.setAdapter(adapter);
         adapter.setOnItemClickListener(MovieDetail.this);


      }

    @Override
    public void CallThis(String jsonText) {
        try {
            JSONObject json = new JSONObject(jsonText);

            //Set Avatar Image From URL
             new DownloadImageTask(poster).execute("https://image.tmdb.org/t/p/w500"+json.getString("backdrop_path"));
             title.setText(json.getString("title"));
             runtime.setText(json.getString("runtime"));
             date.setText(json.getString("release_date"));
             overview.setText(json.getString("overview"));
            director.setText(json.getString("release_date"));
            actors.setText(json.getString("release_date"));
            cast.setText(json.getString("release_date"));
             language.setText(json.getString("original_language"));



        } catch (JSONException e) {
            e.printStackTrace();
        }
       // mRequestQueue.add(request);
    }


    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(MovieDetail.this, cast.class);
        int id = adapter.getIdCast(position);
       detailIntent.putExtra("key", id);
        // test231 clickedItem = kill.get(position);
        startActivity(detailIntent);
    }
}
