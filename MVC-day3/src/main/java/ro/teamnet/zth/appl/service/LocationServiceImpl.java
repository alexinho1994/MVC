package ro.teamnet.zth.appl.service;

import ro.teamnet.zth.appl.dao.LocationDao;
import ro.teamnet.zth.appl.domain.Location;

import java.util.List;

/**
 * Created by Alexandru.Grameni on 7/24/2017.
 */
public class LocationServiceImpl implements LocationService {
    private LocationDao locationDao = new LocationDao();

    @Override
    public List<Location> findAll() {
        return locationDao.getAllLocations();
    }

    @Override
    public Location findOne(Long locationId) {
        return locationDao.getLocationById(locationId);
    }
}
