package br.com.poc.dto;

public class ReturnDTO implements Comparable<ReturnDTO>{

    private String producer;
    private int interval;
    private int previousWin;
    private int followingWin;

    public ReturnDTO(){

    }

    public ReturnDTO(String producer, int interval, int previousWin, int followingWin) {
        this.producer = producer;
        this.interval = interval;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getPreviousWin() {
        return previousWin;
    }

    public void setPreviousWin(int previousWin) {
        this.previousWin = previousWin;
    }

    public int getFollowingWin() {
        return followingWin;
    }

    public void setFollowingWin(int followingWin) {
        this.followingWin = followingWin;
    }

    @Override public int compareTo(ReturnDTO otherWinnersDTO) { //implementação }
        if (this.interval > otherWinnersDTO.interval) {
            return -1;
        } if (this.interval < otherWinnersDTO.getInterval()) {
            return 1;
        }
        return 0;
    }
}
