package com.example.apsandhu.jsonparsingtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class castAllMoviesAdapter  extends RecyclerView.Adapter<castAllMoviesAdapter.ViewHolder> implements CallBackMe {


    private List<String> mNames = new ArrayList<>();
    private List<String> img = new ArrayList<>();

    public int getIdCast(int position) {
        return idCast.get(position);
    }


    private List<Integer> idCast = new ArrayList<>();
    String url="";
    int id;
    // private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(castAllMoviesAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }


    public  castAllMoviesAdapter(Context context, int id) {

        this.id=id;
        mContext = context;
        url="https://api.themoviedb.org/3/person/"+id+"/movie_credits?api_key=e9502953a406241d396c224e783c19b6&language=en-US";
        // img.add("https://image.tmdb.org/t/p/w500/ttzLpjvcLkvXyyTBpjmZw11tjlr.jpg");
        // img.add("https://image.tmdb.org/t/p/w500/lvpKTDdWUv61sty299uC8DQzVTX.jpg");
        //   img.add("https://image.tmdb.org/t/p/w500/d9NkfCwczP0TjgrjpF94jF67SK8.jpg");
        //img.add("");

        JsonRetriever.RetrieveFromURL(context, url,this);

        Log.w("myApp", "1111111111111111@@@@@@@@@@@@@@@@!!!!!!!!!!!!!!!!!!!!!!!!!!"+"       chalda23");

    }





    @Override
    public void CallThis(String jsonText) {
        try {
            JSONObject json = new JSONObject(jsonText);
            final JSONArray test1=json.getJSONArray("cast");
            for (int i=0;i<test1.length();i++)
            {
                JSONObject test2 = test1.getJSONObject(i);
                img.add("https://image.tmdb.org/t/p/w500"+test2.getString("poster_path"));
                mNames.add(test2.getString("original_title"));
              //  Log.w("myApp", "1111111111111111@@@@@@@@@@@@@@@@!!!!!!!!!!!!!!!!!!!!!!!!!!"+"       chalda23"+ test2.getString("original_title"));

                idCast.add(test2.getInt("id"));

            }

            //Set Avatar Image From URL
            // new DownloadImageTask(avatar).execute(json.getString("avatar_url"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView image;



        public ViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textView1);
            image=itemView.findViewById(R.id.avatar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }





    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_movies_adapter, parent, false);

        return new castAllMoviesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "++++++++++++"+img.get(position));
        holder.name.setText(mNames.get(position));
        new DownloadImageTask(holder.image).execute(img.get(position));
    }

    @Override
    public int getItemCount() {
        return img.size();
    }
}
