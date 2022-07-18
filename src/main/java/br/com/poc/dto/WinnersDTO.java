package br.com.poc.dto;

public class WinnersDTO implements Comparable<WinnersDTO>{
    private String producer;
    private int year;

    public WinnersDTO(){

    }

    public WinnersDTO(String producer, int year) {
        this.producer = producer;
        this.year = year;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override public int compareTo(WinnersDTO otherWinnersDTO) { //implementação }
        if (this.year > otherWinnersDTO.year) {
            return -1;
        } if (this.year < otherWinnersDTO.getYear()) {
            return 1;
        }
        return 0;
    }
}
