/**
 * A class for associating data with a year
 */
package nightcrysis.project_walk.Backend.data;

import java.io.Serializable;

public class DataPoint implements Serializable {

    private static final long serialVersionUID = 7526466587411176147L;

    final private int year;
    final private double data;

    /**
     *
     * @param year The year the data is from
     * @param data The data itself
     */
    public DataPoint(int year, double data){
        this.year = year;
        this.data = data;
    }

    /**
     *
     * @return Returns the data
     */
    public double getData() {
        return data;
    }

    /**
     *
     * @return Returns the year of this data
     */
    public int getYear() {
        return year;
    }
}
