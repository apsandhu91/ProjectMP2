package com.example.apsandhu.jsonparsingtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CallBackMe,test231.OnItemClickListener {

    TextView login = null;
    ImageView avatar = null;
    String url1;
    private ListView mListView;
    List<String> kill = null;
    List<Integer> kill2 = null;
    List<String> upName = null;
    List<String> upImg = null;
    List<Integer> upId = null;
    String noimg="http://www.bsmc.net.au/wp-content/uploads/No-image-available.jpg";
    Context context;



    private RecyclerView mRecyclerView;
    private test231 mExampleAdapter;
   // private ArrayList<test231> mExampleList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kill = new ArrayList<String>();
        kill2 = new ArrayList<Integer>();
        upName = new ArrayList<String>();
        upImg = new ArrayList<String>();
        upId = new ArrayList<Integer>();
        context=this;

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRequestQueue = Volley.newRequestQueue(this);      //  mExampleList = new ArrayList<>();

        urlm2("e9502953a406241d396c224e783c19b6");
        parseJSON2(url1);
        urlm("e9502953a406241d396c224e783c19b6");

            JsonRetriever.RetrieveFromURL(this, url1, this);


        //  JsonRetriever.RetrieveFromURL(this, url, this);
    }

    public void urlm(String key) {
        url1 = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + key + "&language=en-US";
    }
    public void urlm2(String key) {
        url1 = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + key + "&language=en-US&page=1";
    }



    private void parseJSON2(String url) {
        // String url = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=kitten&image_type=photo&pretty=true";
        Log.w("myApp", url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                           // JSONObject json = new JSONObject(jsonText);
                             JSONArray test1 = response.getJSONArray("results");
                            for (int i = 0; i < test1.length(); i++) {
                                JSONObject test2 = test1.getJSONObject(i);
                                upName.add(test2.getString("title"));
                                upId.add(test2.getInt("id"));
                                if (test2.has("poster_path")) {
                                    if (!test2.get("poster_path").equals(null)) {
                                        upImg.add("https://image.tmdb.org/t/p/w500" + test2.getString("poster_path"));
                                    }
                                    else
                                    {
                                        upImg.add(noimg);
                                    }

                                }
                                else
                                {
                                    upImg.add(noimg);
                                }

                            }
                            LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                          //  RecyclerView recyclerView = findViewById(R.id.recyclerView);
                            mRecyclerView.setLayoutManager(layoutManager);
                            test231 adapter = new test231(MainActivity.this, upName,upImg);
                            mRecyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(MainActivity.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void CallThis(String jsonText) {
        try {
            JSONObject json = new JSONObject(jsonText);
            final JSONArray test1=json.getJSONArray("genres");
            for (int i=0;i<test1.length();i++)
            {
                JSONObject test2= test1.getJSONObject(i);
                kill.add(test2.getString("name"));

            }
            mListView=(ListView)findViewById(R.id.listv);
            mListView.setAdapter(new custlist(this,kill));
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent myIntent = new Intent(getApplicationContext(),second.class);
                    int id=0;
                    for (int j=0;j<test1.length();j++)
                    {
                        try {
                            JSONObject test2= test1.getJSONObject(i);
                            if (test2.get("name").equals(kill.get(i)))
                            {
                                id=test2.getInt("id");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    myIntent.putExtra("key", id); //Optional parameters
                    startActivity(myIntent);
                }
            });
            //Set Avatar Image From URL
            // new DownloadImageTask(avatar).execute(json.getString("avatar_url"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(getApplicationContext(), MovieDetail.class);
       int id = upId.get(position);
       detailIntent.putExtra("key", id);
        // test231 clickedItem = kill.get(position);
        startActivity(detailIntent);

    }
}
