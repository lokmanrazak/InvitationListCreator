package java;

public class DistanceCalculator {
    private static final double MEAN_EARTH_RADIUS = 6371.009;

    private double startLongitude;
    private double startLatitude;

    public DistanceCalculator(double startLongitude, double startLatitude) {
        this.startLongitude = Math.toRadians(startLongitude);
        this.startLatitude = Math.toRadians(startLatitude);
    }

    public double getDistance(double longitude, double latitude) {
        double endLongitude = Math.toRadians(longitude);
        double endLatitude = Math.toRadians(latitude);

        double centralAngle = getCentralAngle(endLongitude, endLatitude);

        return MEAN_EARTH_RADIUS * centralAngle;
    }

    public double getCentralAngle(double endLongitude, double endLatitude) {
        return Math.acos(
                (Math.sin(startLatitude) * Math.sin(endLatitude)) +
                        (Math.cos(startLatitude) * Math.cos(endLatitude) * Math.cos(Math.abs(startLongitude - endLongitude)))
        );
    }
}
