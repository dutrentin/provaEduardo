package br.com.poc.dto;
import br.com.poc.dto.ReturnDTO;
import java.util.List;

public class ReturnBaseDTO implements Comparable<ReturnBaseDTO>{
    private String desc;
    private ReturnDTO object;

    public ReturnBaseDTO(){

    }

    public ReturnBaseDTO(String desc, ReturnDTO object) {
        this.desc = desc;
        this.object = object;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ReturnDTO getObject() {
        return object;
    }

    public void setObject(ReturnDTO object) {
        this.object = object;
    }

    @Override public int compareTo(ReturnBaseDTO otherWinnersDTO) { //implementação }
        if (this.object.getInterval() > otherWinnersDTO.object.getInterval()) {
            return -1;
        } if (this.object.getInterval() < otherWinnersDTO.object.getInterval()) {
            return 1;
        }
        return 0;
    }
}
