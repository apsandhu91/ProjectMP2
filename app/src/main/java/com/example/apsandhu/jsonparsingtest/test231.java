package com.example.apsandhu.jsonparsingtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class test231 extends RecyclerView.Adapter<test231.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<String> mNames = new ArrayList<>();
    private List<String> img = new ArrayList<>();
   // private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public test231(Context context, List<String> names,List<String> im) {
        mNames = names;
        img=im;
   //     mImageUrls = imageUrls;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onBindViewHolder: called.  2");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listenitem, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.  1");

        holder.name.setText(mNames.get(position));
        new DownloadImageTask(holder.image).execute(img.get(position));


    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView name;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "onBindViewHolder: called.");
            // image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.name);
            image=itemView.findViewById(R.id.avatar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
}
