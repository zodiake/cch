package com.by.controller;

import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.VehicleParkingJson;
import com.by.service.VehicleParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;

/**
 * Created by yagamai on 15-12-8.
 */
@Controller
@RequestMapping("/api/vehicle")
public class VehicleParkingController {
    @Autowired
    private VehicleParkingService service;

    @RequestMapping
    public Status parkingQuery(@PathParam("license") String license) {
        VehicleParkingJson json = service.findOne(license);
        return new Success<>(json);
    }
}
