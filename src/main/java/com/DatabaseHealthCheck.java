package com;

import com.codahale.metrics.health.HealthCheck;

/**
 * User: maxz
 * Date: 25.09.2016
 */
public class DatabaseHealthCheck extends HealthCheck {

//    private final Database database;
//
//    public DatabaseHealthCheck(Database database) {
//        this.database = database;
//    }
//
    @Override
    protected Result check() throws Exception {
//        if (database.isConnected()) {
            return Result.healthy();
//        } else {
//            return Result.unhealthy("Cannot connect to " + database.getUrl());
//        }
    }
}