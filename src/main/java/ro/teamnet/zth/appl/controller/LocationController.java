package ro.teamnet.zth.appl.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;

/**
 * Created by Alexandru.Grameni on 7/20/2017.
 */
@MyController(urlPath="/locations")
public class LocationController {

    @MyRequestMethod(urlPath = "/all", methodType="GET")
    public String getAllLocations()
    {
        return "allLocations";
    }

    @MyRequestMethod(urlPath = "/one", methodType = "GET")
    public String getOneLocation()
    {
        return "oneRandomLocation";
    }

}