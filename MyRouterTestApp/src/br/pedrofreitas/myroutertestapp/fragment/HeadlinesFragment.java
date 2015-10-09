package br.pedrofreitas.myroutertestapp.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import br.pedrofreitas.myroutertestapp.R;

public class HeadlinesFragment extends ListFragment {	
	OnHeadlineSelectedListener mCallback;
	ActionBar mActionBar = null;

	private Info mInfo = null;
	
	public HeadlinesFragment(Info mInfo) {
		this.mInfo = mInfo;
	}	
	
    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnHeadlineSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onResultSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
    	super.onAttach(activity);
    	
    	// This makes sure that the container activity has implemented
    	// the callback interface. If not, it throws an exception.
    	try {
    		mCallback = (OnHeadlineSelectedListener) activity;      
    		mActionBar = ((ActionBarActivity) activity).getSupportActionBar();
    		mActionBar.setDisplayHomeAsUpEnabled(true);
    		mActionBar.setTitle(R.string.title_activity_result);
    		
    	} catch (ClassCastException e) {
    		throw new ClassCastException(activity.toString()
    				+ " must implement OnHeadlineSelectedListener");
    	}
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mActionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle(R.string.title_activity_result);
       
        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;  
        
        // Create an array adapter for the list view, using the Info headlines array
        setListAdapter(new CustomListAdapter(getActivity(), layout, mInfo));        
        
    }

    @Override
    public void onStart() {
        super.onStart();
        
        mActionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle(R.string.title_activity_result);

        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        if (getFragmentManager().findFragmentById(R.id.details_fragment) != null) {
            getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            
        }
    }    

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(mInfo.getDetails()[position] != null && mInfo.getDetails()[position] != "") {
        	TextView text = (TextView) v.findViewById(android.R.id.text1);    
        	text.setBackgroundColor(Color.parseColor("#F80C0C")); 
 
        	final int pos = position;        	
        	new Handler().postDelayed(new Runnable() {
        		@Override
    			public void run() {
        			// Notify the parent activity of selected item
        			mCallback.onResultSelected(pos);       
        			
        			// Set the item as checked to be highlighted when in two-pane layout
        			getListView().setItemChecked(pos, true);    			
        		}
        	}, 170);
        } else {
        	TextView text = (TextView) v.findViewById(android.R.id.text1);    
        	text.setBackgroundColor(Color.parseColor("#B0D050"));
        	
        	final TextView t = text;
        	new Handler().postDelayed(new Runnable() {
        		@Override
    			public void run() {		
        			t.setBackgroundColor(Color.parseColor("#719717"));       
        		}
        	}, 170);    			
        }
    	
    }    

}
