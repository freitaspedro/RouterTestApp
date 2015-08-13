package br.pedrofreitas.myroutertestapp;

import java.util.LinkedHashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import br.pedrofreitas.myroutertestapp.fragment.HeadlinesFragment;
import br.pedrofreitas.myroutertestapp.fragment.Info;
import br.pedrofreitas.myroutertestapp.fragment.ResultFragment;
import br.pedrofreitas.myroutertestapp.util.Constants;

public class Result extends ActionBarActivity 
		implements HeadlinesFragment.OnHeadlineSelectedListener {
	
	private Map<String, String> finalResult = new LinkedHashMap<>();
	private Info mInfo = null; 
	
	/** Called when the activity is first created. */
	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_result); 
        
        
        finalResult.put("Reboot", Constants.REBOOT);
        finalResult.put("DNS", null);
        finalResult.put("Acesso Remoto", Constants.ACESSO);
        finalResult.put("X", Constants.X);
        finalResult.put("Y", null);
                
        mInfo = new Info(finalResult);        
        
       
        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            
            
            // Create an instance of ExampleFragment
            HeadlinesFragment firstFragment = new HeadlinesFragment(mInfo);

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }		
	 
   
    @Override
	public void onResultSelected(int position) {
    	// The user selected the headline of an article from the HeadlinesFragment

        // Capture the article fragment from the activity layout
        ResultFragment resultFrag = (ResultFragment)
                getSupportFragmentManager().findFragmentById(R.id.details_fragment);

        
        if (resultFrag != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
        	resultFrag.updateResultView(position);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
        	ResultFragment newFragment = new ResultFragment(mInfo);
            Bundle args = new Bundle();
            args.putInt(ResultFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }
    
}
