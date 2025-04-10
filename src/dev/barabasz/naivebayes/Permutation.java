package dev.barabasz.naivebayes;

public class Permutation {
    private String data;
    private int yes;
    private int no;

    public Permutation(String data, int yes, int no) {
        setData(data);
        setYes(yes);
        setNo(no);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getYes() {
        return yes;
    }

    public void setYes(int yes) {
        this.yes = yes;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
}
