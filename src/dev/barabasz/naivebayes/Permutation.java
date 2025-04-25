package dev.barabasz.naivebayes;

/**
 * The {@code Permutation} class represents a data structure that holds a string
 * value along with two floating-point values representing "yes" and "no" probabilities.
 * This class provides getter and setter methods to access and modify its fields.
 * 
 * <p>Example usage:
 * <pre>
 *     Permutation permutation = new Permutation("example", 0.7f, 0.3f);
 *     String data = permutation.getData();
 *     float yesProbability = permutation.getYes();
 *     float noProbability = permutation.getNo();
 * </pre>
 * </p>
 * 
 * <p>Fields:
 * <ul>
 *   <li>{@code data} - A string representing the data associated with the permutation.</li>
 *   <li>{@code yes} - A float representing the "yes" probability.</li>
 *   <li>{@code no} - A float representing the "no" probability.</li>
 * </ul>
 * </p>
 * 
 * <p>Methods:
 * <ul>
 *   <li>{@code getData()} - Returns the string data.</li>
 *   <li>{@code setData(String data)} - Sets the string data.</li>
 *   <li>{@code getYes()} - Returns the "yes" probability.</li>
 *   <li>{@code setYes(float yes)} - Sets the "yes" probability.</li>
 *   <li>{@code getNo()} - Returns the "no" probability.</li>
 *   <li>{@code setNo(float no)} - Sets the "no" probability.</li>
 * </ul>
 * </p>
 * 
 * @author Jakub Barabasz - c23310371
 * @version 1.0.0
 */
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
