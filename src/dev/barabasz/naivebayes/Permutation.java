package dev.barabasz.naivebayes;

public class Permutation {
    private String data;
    private float yes;
    private float no;

    public Permutation(String data, float yes, float no) {
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

    public float getYes() {
        return yes;
    }

    public void setYes(float yes) {
        this.yes = yes;
    }

    public float getNo() {
        return no;
    }

    public void setNo(float no) {
        this.no = no;
    }
}
