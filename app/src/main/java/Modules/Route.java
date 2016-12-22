package Modules;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Cosio on 17/10/2016.
 */
public class Route {
    public String startAddress;
    public LatLng startLocation;
    public String endAddress;
    public LatLng endLocation;
    public Distance distance;
    public Duration duration;

    public List<LatLng> points;
}


