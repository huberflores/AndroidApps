package ee.oulu.fi.ubicomp.network;

import android.content.Context;

/**
 * Created by Huber Flores on 11/3/15.
 */
public class D2DWifiDirect {

    private static final String TAG = D2DWifiDirect.class.getSimpleName();

    WifiDirectState wfDiscovery;
    WifiDirectState wfIdle;
    WifiDirectState wfOn;
    WifiDirectState wfOff;

    //current state
    WifiDirectState wfState;

    private Context context;

    public D2DWifiDirect(Context ctx){
        this.context = ctx;


    }

    public void setD2DWifiDirectState(WifiDirectState wfState){
        this.wfState = wfState;
    }

    public WifiDirectState getWifiDirectState(){
        return this.wfState;
    }

    public WifiDirectState getWifiDirectStateDiscovery(){
        return  this.wfDiscovery;
    }

    public WifiDirectState getWifiDirectStateIdle(){
        return this.wfIdle;
    }

    public WifiDirectState getWifiDirectStateOn(){
        return this.wfOn;
    }

    public WifiDirectState getWifiDirectStateOff(){
        return this.wfOff;
    }

    public void D2DDiscovery(){
        wfState.discovery();
    }

    public void D2DIdle(){
        wfState.idle();
    }

    public void D2DOn(){
        wfState.on();
    }

    public void D2DOff(){
        wfState.off();
    }





}
