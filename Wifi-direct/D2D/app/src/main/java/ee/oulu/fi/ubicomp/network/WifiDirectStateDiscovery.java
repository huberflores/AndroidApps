package ee.oulu.fi.ubicomp.network;



/**
 * Created by huber on 11/3/15.
 */
public class WifiDirectStateDiscovery implements WifiDirectState {

    private static final String TAG = WifiDirectStateDiscovery.class.getSimpleName();


    D2DWifiDirect wifiDirect;


    public WifiDirectStateDiscovery(D2DWifiDirect wifiDirect){
        this.wifiDirect = wifiDirect;

    }


    @Override
    public void discovery() {
        wifiDirect.setD2DWifiDirectState(wifiDirect.getWifiDirectStateDiscovery());
        wifiDirect.D2DDiscovery();

    }

    @Override
    public void idle() {

    }

    @Override
    public void on() {

    }

    @Override
    public void off() {

    }
}
