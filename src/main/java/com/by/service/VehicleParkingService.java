package com.by.service;

import com.by.json.VehicleParkingJson;

/**
 * Created by yagamai on 15-12-8.
 */
public interface VehicleParkingService {
    VehicleParkingJson findOne(String license);
}
