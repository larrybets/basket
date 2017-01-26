package model;

/**
 * Created by DAM on 26/1/17.
 */
public class Estadisticas {
    public int maxBaskets;
    public int maxAssists;
    public int maxRebounds;
    public int minBaskets;
    public int minAssists;
    public int minRebounds;

    public int avgBaskets;
    public int avgAssists;
    public int avgRebounds;


    public Estadisticas() {
    }

    @Override
    public String toString() {
        return "Estadisticas{" +
                "maxBaskets=" + maxBaskets +
                ", maxAssists=" + maxAssists +
                ", maxRebounds=" + maxRebounds +
                ", minBaskets=" + minBaskets +
                ", minAssists=" + minAssists +
                ", minRebounds=" + minRebounds +
                ", avgBaskets=" + avgBaskets +
                ", avgAssists=" + avgAssists +
                ", avgRebounds=" + avgRebounds +
                '}';
    }
}
