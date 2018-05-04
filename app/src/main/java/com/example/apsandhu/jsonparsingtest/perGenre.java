package com.example.apsandhu.jsonparsingtest;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class perGenre extends BaseAdapter  {
    List<String> result;
    List<String> img;
   // String[] result;
    Context context;
    private static LayoutInflater inflater=null;
    public perGenre(second mainActivity, List<String> namelist,List<String> img)
    {
        this.result=namelist;
        this.context=mainActivity;
        this.img=img;
        inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return result.size();
        // return result.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView text;
        ImageView avatar;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder =new Holder();
        View rowview;
        rowview=inflater.inflate(R.layout.per_genre_layout,null);
        holder.text=(TextView)rowview.findViewById(R.id.textView1);
        holder.avatar=(ImageView)rowview.findViewById(R.id.avatar);
        new DownloadImageTask(holder.avatar).execute(img.get(position));
       // new DownloadImageTask(holder.avatar).execute("https://timesofindia.indiatimes.com/thumb/msid-5163635,width-400,resizemode-4/5163635.jpg");
        holder.text.setText(result.get(position));
     /*   rowview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"you select "+result.get(position),Toast.LENGTH_LONG).show();

            }
        });*/
        return rowview;
    }
}
