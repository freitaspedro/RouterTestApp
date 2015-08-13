package br.pedrofreitas.myroutertestapp.fragment;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<String> {
	
	private Context mContext;
    private int id;
    private String[] headlines;
    private String[] details;
    

    public CustomListAdapter(Context context, int textViewResourceId, Info info) {
    	super(context, textViewResourceId, info.getHeadlines());           
        mContext = context;
        id = textViewResourceId;
        headlines = info.getHeadlines();
        details = info.getDetails();
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        View mView = v ;
        if(mView == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, null);
        }

        TextView text = (TextView) mView.findViewById(android.R.id.text1);    
        
        if(headlines[position] != null) {
         	text.setTextColor(Color.WHITE);
           if(details[position] != null && details[position] != "") {
        	   text.setText(Html.fromHtml(headlines[position] + "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;" + "<small><i> Clique aqui para mais detalhes </i></small>"));
        	   text.setBackgroundColor(Color.parseColor("#719717")); 
           } else {
        	   text.setText(headlines[position]);
        	   text.setBackgroundColor(Color.parseColor("#C51212"));         	   
           }     	
        }

        return mView;
    }
}
