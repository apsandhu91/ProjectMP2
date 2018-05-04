package com.example.apsandhu.jsonparsingtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class second extends AppCompatActivity implements CallBackMe{

    TextView login = null;
    ImageView avatar = null;
    String url;
    private ListView mListView;
    List<String> kill=null;
    List<String> img=null;
    List<Integer> movieid=null;
    Context context;
    String noimg="http://www.bsmc.net.au/wp-content/uploads/No-image-available.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent kamal=getIntent();
        int id =kamal.getIntExtra("key",0);
       // login =findViewById(R.id.login);
      //  avatar =findViewById(R.id.avatar);
        kill= new ArrayList<String>();
        img= new ArrayList<String>();
        movieid=new ArrayList<Integer>();
        urlm("e9502953a406241d396c224e783c19b6",id);
        JsonRetriever.RetrieveFromURL(this, url, this);
    }

    public void urlm(String key,int id)
    {
        url="https://api.themoviedb.org/3/genre/"+id+"/movies?api_key="+key+"&language=en-US&include_adult=false&sort_by=created_at.asc";
    }

    @Override
    public void CallThis(String jsonText) {
        try {
            JSONObject json = new JSONObject(jsonText);
            JSONArray test1=json.getJSONArray("results");
            for (int i=0;i<test1.length();i++)
            {
                JSONObject test2= test1.getJSONObject(i);
                kill.add(test2.getString("original_title"));
                movieid.add(test2.getInt("id"));
                if (test2.has("poster_path")) {
                    if (!test2.get("poster_path").equals(null)) {
                        img.add("https://image.tmdb.org/t/p/w500" + test2.getString("poster_path"));
                    }
                    else
                    {
                        img.add(noimg);
                    }

                }
                else
                {
                    img.add(noimg);
                }
            }
            mListView=(ListView)findViewById(R.id.listv);
            mListView.setAdapter(new perGenre(this,kill,img));
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent myIntent = new Intent(getApplicationContext(),MovieDetail.class);
                    myIntent.putExtra("key", movieid.get(i)); //Optional parameters
                    startActivity(myIntent);
                }
            });
            //Set Avatar Image From URL
            // new DownloadImageTask(avatar).execute(json.getString("avatar_url"));
            //results
            //original_title

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
