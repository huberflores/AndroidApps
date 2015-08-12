package ee.ut.cs.bluetooth.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class ClientMain extends Activity {
	 
	private final String TAG = ClientMain.class.getSimpleName();
	
	private List<BluetoothDevice> mArrayAdapter = new ArrayList<BluetoothDevice>();
	
	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); 
	private final BluetoothDevice mmDevice = null; 
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);
        
        if (mBluetoothAdapter == null) {
    	    // Device does not support Bluetooth
        	Log.d(TAG, "Bluetooth not supported");
        	return;
    	}
      
        historyDevices();
        

        Log.d(TAG, "client finalized");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.client_main, menu);
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
    
    
    public void onClick(View view) {  
		
		switch (view.getId()) {
			case R.id.button1:
				if (mArrayAdapter.size()>0){
		            new Thread(
		                    new ConnectThread(mArrayAdapter.get(0))
		                ).start();
		        
		            
		            //Multiple requests can be send to a single device
		            //No more than one socket instance can be created to target a single device
		            //if requests are in sequence, one single socket should be open and close until the last request is done (in other words, one socket to manage all the requests)
		            //if requests are not in sequence, then a new socket device should be created to handle each request, one after the other
		            //in this context, once a request is finished, then the socket is close, and just then a new one can be created.
		            
		            /*try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            
		            new Thread(
		                    new ConnectThread(mArrayAdapter.get(0))
		                ).start();
		            */
		         }
				
				Toast.makeText(ClientMain.this, "Connected", Toast.LENGTH_SHORT)
				.show();
				
				break; 
		
					
		}
		
		
	}
    
    
    public void historyDevices(){
    	Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
    	// If there are paired devices
    	if (pairedDevices.size() > 0) {
    	    // Loop through paired devices
    	    for (BluetoothDevice device : pairedDevices) {
    	        // Add the name and address to an array adapter to show in a ListView
    	        mArrayAdapter.add(device);
    	        Log.d(TAG, "device found: " + device.getAddress() +  ", MAC: " + device.getAddress());
    	    }
    	}
    }
}
