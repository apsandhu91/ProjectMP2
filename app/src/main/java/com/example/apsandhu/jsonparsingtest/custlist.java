package com.example.apsandhu.jsonparsingtest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.List;

public class custlist extends BaseAdapter {
   List<String> result;
  //  String[] result;
    Context context;
    private static LayoutInflater inflater=null;
    public custlist(Context c)
    {
        this.context=c;
    }
    public custlist(MainActivity mainActivity, List<String> namelist)
    {
        Log.w("myApp", "1111111111111111@@@@@@@@@@@@@@@@!!!!!!!!!!!!!!!!!!!!!!!!!!");
        this.result=namelist;
        this.context=mainActivity;
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
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        Holder holder =new Holder();
        View rowview;
        rowview=inflater.inflate(R.layout.listv,null);
        holder.text=(TextView)rowview.findViewById(R.id.textView1);

        holder.text.setText(result.get(position));
        /*rowview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Toast.makeText(context,"you select "+result[position],Toast.LENGTH_LONG).show();
               Intent myIntent = new Intent(context,perGenre.class);
               // myIntent.putExtra("key", value); //Optional parameters
                context.startActivity(myIntent);

            }
        });*/
        return rowview;
    }
}
