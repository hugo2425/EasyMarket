package Modules;

import java.util.List;

/**
 * Created by Cosio on 17/10/2016.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
