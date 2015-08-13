package br.pedrofreitas.myroutertestapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.pedrofreitas.myroutertestapp.R;

public class ResultFragment extends Fragment {
	public final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;   
    ActionBar mActionBar = null;
    
    Info mInfo = null;

    public ResultFragment(Info mInfo) {
    	this.mInfo = mInfo;
    }
    
    @Override
    public void onAttach(Activity activity) {
    	super.onAttach(activity);
    	setHasOptionsMenu(true);
    	
    	mActionBar = ((ActionBarActivity) activity).getSupportActionBar();
    	mActionBar.setDisplayHomeAsUpEnabled(true);
    	mActionBar.setTitle(R.string.details);
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        	case android.R.id.home: 
        		HeadlinesFragment firstFragment = new HeadlinesFragment(mInfo);
        		FragmentTransaction transaction = getFragmentManager().beginTransaction();
        		transaction.replace(R.id.fragment_container, firstFragment);
                transaction.addToBackStack(null);
                transaction.commit();
	            return true;
	
	        default:
	            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {    	
    	setHasOptionsMenu(true);
    	
    	mActionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
    	mActionBar.setDisplayHomeAsUpEnabled(true);
    	mActionBar.setTitle(R.string.details);

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        // Inflate the layout for this fragment
        View mInflate = inflater.inflate(R.layout.result_view, container, false);        
        
        return mInflate;
    }

    @Override
    public void onStart() {
        super.onStart();        
        setHasOptionsMenu(true);
    	
    	mActionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
    	mActionBar.setDisplayHomeAsUpEnabled(true);
    	mActionBar.setTitle(R.string.details);

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateResultView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateResultView(mCurrentPosition);
        }
    }
    
    
    public void updateResultView(int position) {
        TextView article = (TextView) getActivity().findViewById(R.id.result);
                
        article.setText(mInfo.getDetails()[position]);
        mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}
