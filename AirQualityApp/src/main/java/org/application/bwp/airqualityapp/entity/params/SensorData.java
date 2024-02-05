package org.application.bwp.airqualityapp.entity.params;

public class SensorData {
    private int id;
    private int stationId;
    private Param param;

    public SensorData(int id, int stationId, Param param) {
        this.id = id;
        this.stationId = stationId;
        this.param = param;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "id=" + id +
                ", stationId=" + stationId +
                ", param=" + param +
                '}';
    }
}
