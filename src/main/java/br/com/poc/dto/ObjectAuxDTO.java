package br.com.poc.dto;

public class ObjectAuxDTO {
    private String desc;
    private String producer;
    private int interval;
    private int yearMin;
    private int yearMax;

    public ObjectAuxDTO(){

    }

    public ObjectAuxDTO(String desc,String producer, int interval, int yearMin, int yearMax) {
        this.desc = desc;
        this.interval = interval;
        this.yearMin = yearMin;
        this.yearMax = yearMax;
        this.producer = producer;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getYearMin() {
        return yearMin;
    }

    public void setYearMin(int yearMin) {
        this.yearMin = yearMin;
    }

    public int getYearMax() {
        return yearMax;
    }

    public void setYearMax(int yearMax) {
        this.yearMax = yearMax;
    }
}
