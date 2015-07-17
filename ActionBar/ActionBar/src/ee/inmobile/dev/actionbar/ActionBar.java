package ee.inmobile.dev.actionbar;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class ActionBar extends /*ActivityBarActivity*/ Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.action_bar, menu);
    	 MenuInflater inflater = getMenuInflater();
    	 inflater.inflate(R.menu.action_bar, menu);

        
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	
    	switch (item.getItemId()) {
        case R.id.action_search:
            openSearch();
            return true;
        case R.id.action_settings:
            openSettings();
            return true;
        default:
            return super.onOptionsItemSelected(item);

    	}

    }
    
    
    
    public void openSearch(){
    	 Toast.makeText(getApplicationContext(), "Open search",Toast.LENGTH_SHORT).show();
    }

    
    public void openSettings(){
    	Toast.makeText(getApplicationContext(), "Open settings",Toast.LENGTH_SHORT).show();
    }
}
