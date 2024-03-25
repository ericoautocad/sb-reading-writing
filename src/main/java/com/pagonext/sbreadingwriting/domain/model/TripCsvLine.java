
package com.pagonext.sbreadingwriting.domain.model;

public class TripCsvLine {
    private Integer bikeId;
    private Integer age;
    private String gender;
    private String durationTime;
    private String startStationName;
    private String endStationName;

    public TripCsvLine() {
            super();
    }

    public TripCsvLine(
                    Integer bikeId,
                    Integer age,
                    String gender,
                    String durationTime,
                    String startStationName,
                    String endStationName) {
            this.bikeId = bikeId;
            this.age = age;
            this.gender = gender;
            this.durationTime = durationTime;
            this.startStationName = startStationName;
            this.endStationName = endStationName;
    }

    public Integer getBikeId() {
            return bikeId;
    }

    public void setBikeId(Integer bikeId) {
            this.bikeId = bikeId;
    }

    public Integer getAge() {
            return age;
    }

    public void setAge(Integer age) {
            this.age = age;
    }

    public String getGender() {
            return gender;
    }

    public void setGender(String gender) {
            this.gender = gender;
    }

    public String getDurationTime() {
            return durationTime;
    }

    public void setDurationTime(String durationTime) {
            this.durationTime = durationTime;
    }

    public String getStartStationName() {
            return startStationName;
    }

    public void setStartStationName(String startStationName) {
            this.startStationName = startStationName;
    }

    public String getEndStationName() {
            return endStationName;
    }

    public void setEndStationName(String endStationName) {
            this.endStationName = endStationName;
    }

    
}
