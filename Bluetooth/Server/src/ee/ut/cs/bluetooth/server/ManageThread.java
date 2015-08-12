package ee.ut.cs.bluetooth.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

public class ManageThread extends Thread {
	private final String TAG = ManageThread.class.getSimpleName();
	
	private BluetoothSocket mmSocket;
    private InputStream mmInStream;
    private OutputStream mmOutStream;
   
    
    public ManageThread(BluetoothSocket socket) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
 
        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }
 
        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }
    
    
    public void run() {
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()
        boolean listen = false;
 
        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
           	
                bytes = mmInStream.read(buffer);
               
                try{
                	String s = new String(buffer);
                	Log.d(TAG, "resultado: " + s);
                
                }catch(Exception e){
                	Log.d(TAG, "Exception");
                }
                
                mmInStream.close();
            
                
            } catch (IOException e) {
                break;
            }
        }
        
        Log.d(TAG, "Finish");
    }
 
    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) { }
    }
 
    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
    
    

	
}
