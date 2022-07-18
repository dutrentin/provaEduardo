package br.com.poc.enuns;

public enum ColumnEnum {

    YEAR(0),
    TITLE(1),
    STUDIOS(2),
    PRODUCERS(3),
    WINNER(4);

    private int column;


    ColumnEnum(int column){
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
