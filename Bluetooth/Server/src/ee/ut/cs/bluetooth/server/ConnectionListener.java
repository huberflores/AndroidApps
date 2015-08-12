package ee.ut.cs.bluetooth.server;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;


public class ConnectionListener extends Thread {
	protected boolean isStopped = false;
	
	private static final String NAME = "test"; 

	private static final UUID MY_UUID = UUID.fromString("00000000-15bd-3817-75fc-32dc6e939714"); 

	private final String TAG = ConnectionListener.class.getSimpleName();
	
	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final BluetoothServerSocket mmServerSocket;
 
    public ConnectionListener() { 
        // Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
        } catch (IOException e) { }
        mmServerSocket = tmp;
    }
 
    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned
        double startTime = System.currentTimeMillis();
        double finalTime;
        
        while (!isStopped()) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
            	
            	Log.d(TAG,"Server Stopped.");
            	
                break;
            }
            // If a connection was accepted
            if (socket != null) {
                // Do work to manage the connection (in a separate thread) 
                Log.d(TAG, "D2D connected server");
               
                new Thread(
                        new ManageThread(socket) 
                    ).start();
                
                
                finalTime = startTime - System.currentTimeMillis();
                if (finalTime>50000){
                	isStopped = true;
                	Log.d(TAG, "Server stopped");
                	try {
    					mmServerSocket.close();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace(); 
    				}
                    break;
                }
                
            }
        }
    }
 
    /** Will cancel the listening socket, and cause the thread to finish */
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) { }
    }
    
    
    private synchronized boolean isStopped() {
        return this.isStopped;
    }
    
    
}