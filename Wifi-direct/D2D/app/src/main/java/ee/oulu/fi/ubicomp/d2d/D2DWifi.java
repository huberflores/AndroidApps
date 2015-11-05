package ee.oulu.fi.ubicomp.d2d;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ee.oulu.fi.ubicomp.network.WiFiDirectBroadcastReceiver;

public class D2DWifi extends ActionBarActivity {

    Context context;

    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver wifiReceiver;

    IntentFilter mIntentFilter;

    WifiP2pManager.PeerListListener mPeerListListener;
    private List peers = new ArrayList();

    TextView container;

    WifiP2pManager.ConnectionInfoListener mPeerConnectionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d2_dwifi);

        container = (TextView) findViewById(R.id.textView2);

        context = this;

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);


        mPeerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peerList) {
                System.out.println("Listening for devices...");
                peers.clear();
                peers.addAll(peerList.getDeviceList());

                if (peers.size() == 0) {
                    System.out.println("No devices found");
                    return;
                }else{
                    Iterator<WifiP2pDevice> i = peers.iterator();
                    while(i.hasNext()){
                        WifiP2pDevice device = i.next();
                        print(device.deviceName);
                    }

                }
            }
        };


        mPeerConnectionListener = new WifiP2pManager.ConnectionInfoListener(){

            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo info) {
                try {
                    InetAddress groupOwnerAddress = InetAddress.getByName(info.groupOwnerAddress.getHostAddress());


                    if (info.groupFormed && info.isGroupOwner){
                        System.out.println("This device acts as client");
                    }else{
                        if (info.groupFormed){
                            System.out.println("Other device acts as client");
                        }
                    }

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

            }

        };



        setActionsWifiDirectReceiver();
    }

    public void setActionsWifiDirectReceiver(){
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        wifiReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this, mPeerListListener, mPeerConnectionListener);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_d2_dwifi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause(){
        super.onPause();
        unregisterReceiver(wifiReceiver);

    }

    protected void onResume(){
        super.onResume();
        registerReceiver(wifiReceiver, mIntentFilter);
    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button1: {
                //Toast.makeText(this, "Button pressed ", Toast.LENGTH_SHORT).show();
                mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        System.out.println("Succeed");
                    }

                    @Override
                    public void onFailure(int reasonCode) {
                        System.out.println("Failed");
                    }
                });


                break;
            }

            case R.id.button2:{
                connectToDevice();

                break;
            }

        }

    }

    public void print(String line){
        container.append(line + "\n");
    }


    public void connectToDevice(){
        WifiP2pDevice device = (WifiP2pDevice) peers.get(0);

        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener(){

            @Override
            public void onSuccess(){
                Toast.makeText(D2DWifi.this, "Connection initiation succeeded...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reason){
                Toast.makeText(D2DWifi.this, "Connection initiation failed...", Toast.LENGTH_SHORT).show();
            }


        });


    }

}
