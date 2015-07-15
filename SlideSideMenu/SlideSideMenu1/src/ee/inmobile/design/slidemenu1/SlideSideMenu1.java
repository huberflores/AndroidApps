package ee.inmobile.design.slidemenu1;



import ee.inmobile.util.commons.MenuEventController;
import ee.inmobile.util.commons.MenuLazyAdapter;
import ee.inmobile.util.commons.OnSwipeTouchListener;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class SlideSideMenu1 extends ActionBarActivity {	
	private RelativeLayout layout;
	private MenuLazyAdapter menuAdapter;
	private boolean floatingScreen = false;
    
	private final Context context = this;
    
	private ListView listMenu;
	private TextView appName;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_side_menu1);  
        this.listMenu = (ListView) findViewById(R.id.listMenu);
        this.layout = (RelativeLayout) findViewById(R.id.layoutToMove);
        this.appName = (TextView) findViewById(R.id.appName);
        
        TextView textMenuBar = (TextView) findViewById(R.id.appName);
        textMenuBar.setTextColor(Color.WHITE);
        
        this.menuAdapter = new MenuLazyAdapter(this, MenuEventController.menuArray.size() == 0 ? MenuEventController.getMenuDefault(this) : MenuEventController.menuArray);
        this.listMenu.setAdapter(menuAdapter);
        
        this.layout.setOnTouchListener(new OnSwipeTouchListener() {
            public void onSwipeRight() {
                if(!floatingScreen){
                	floatingScreen = true;
                	MenuEventController.open(context, layout, appName);
                	MenuEventController.closeKeyboard(context, getCurrentFocus());
                }
            }
            public void onSwipeLeft() {
            	if(floatingScreen){
            		floatingScreen = false;
            		MenuEventController.close(context, layout, appName);
            		MenuEventController.closeKeyboard(context, getCurrentFocus());
            	}
            }
        });
        
        this.listMenu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//Your intent object is null, you need set a intent to this object, 
				//like in 0 position
				Intent intent = null;
				if(position == 0){
					//action
				 	//Here you need create the intent
				 	//LOOK
				 	//intent = new Intent(this, MyNewActivity.class);
					Toast.makeText(getApplicationContext(), "Option 1...",Toast.LENGTH_SHORT).show();
				 	
				} else if(position == 1){
					//action
					//Here you need create the intent
				 	//intent = new Intent(this, MyNewActivity2.class);
					
					Toast.makeText(getApplicationContext(), "Option 2...",Toast.LENGTH_SHORT).show();
				} /*else if(position == 2){
					//if activity is this just close menu before verify if menu is open
					if(floatingScreen){
						floatingScreen = false;
						MenuEventController.close(context, layout, appName);
						MenuEventController.closeKeyboard(context, view);
	            			}
				} else if(position == 3){
					//Here you need create the intent
				 	//intent = new Intent(this, MyNewActivity3.class);
				} else if(position == 4){
					//Here you need create the intent
				 	//intent = new Intent(this, MyNewActivity4.class);
				} else if(position == 5){
					//Here you need create the intent
				 	//intent = new Intent(this, MyNewActivity5.class);
				} else if(position == 6){
					//Here you need create the intent
				 	//intent = new Intent(this, MyNewActivity6.class);
				} else if(position == 7){
					//Here you need create the intent
				 	//intent = new Intent(this, MyNewActivity7.class);
				}*/
				
				//potential exception
				//Check the position if different of current a intent are started else menu just closed
				if(position != 2){
					//startActivity(intent);
					//SlideSideMenu1.this.finish();
					//overridePendingTransition(R.anim.slide_left, R.anim.slide_left);
				}
			}
		});
        
        
    }

 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.slide_side_menu1, menu);
        return true; 
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /*public void openCloseMenu(View view){
    	 Toast.makeText(getApplicationContext(), "Sliding screen...",Toast.LENGTH_SHORT).show();
    }*/
    
    
    public void openCloseMenu(View view){
    	if(!this.floatingScreen){
    		this.floatingScreen = true;
    		MenuEventController.open(this.context, this.layout, this.appName);
    		MenuEventController.closeKeyboard(this.context, view);
    	} else {
    		this.floatingScreen = false;
    		MenuEventController.close(this.context, this.layout, this.appName);
    		MenuEventController.closeKeyboard(this.context, view);
    	}
    }
    
}
