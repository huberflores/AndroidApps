package ee.ut.cs.bluetooth.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

public class ManageThread extends Thread {
	private final String TAG = ManageThread.class.getSimpleName();
	
	private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream; 
    private Handler mHandler;
 
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
        while (!listen) { 
            try {
                // Read from the InputStream
            	String mensaje = "Este mensaje es enviado usando Bluetooth!"; 
            	buffer = mensaje.getBytes(Charset.forName("UTF-8"));
                		
                mmOutStream.write(buffer);
 
                mmOutStream.flush(); 
             
                Log.d(TAG, "Finish request");
                
                mmOutStream.close();
                listen = true;
           
            } catch (IOException e) {
            	Log.d(TAG, "Finish with exception");
            	
            	cancel();
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
