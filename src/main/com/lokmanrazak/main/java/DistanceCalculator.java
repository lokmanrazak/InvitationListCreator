package com.lokmanrazak.main.java;

public class DistanceCalculator {
    private double startLatitude;
    private double startLongitude;
    private double radius;

    public DistanceCalculator(double startLatitude, double startLongitude, double radius) {
        this.startLatitude = Math.toRadians(startLatitude);
        this.startLongitude = Math.toRadians(startLongitude);
        this.radius = radius;
    }

    public double getDistance(double latitude, double longitude) {
        double endLatitudeRad = Math.toRadians(latitude);
        double endLongitudeRad = Math.toRadians(longitude);

        double centralAngle = Math.acos(
                (Math.sin(startLatitude) * Math.sin(endLatitudeRad)) +
                        (Math.cos(startLatitude) * Math.cos(endLatitudeRad) * Math.cos(Math.abs(startLongitude - endLongitudeRad)))
        );

        return radius * centralAngle;
    }
}
