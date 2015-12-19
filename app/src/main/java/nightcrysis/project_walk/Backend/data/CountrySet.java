/**
 * A class for storing all the countries information in one Data type
 */
package nightcrysis.project_walk.Backend.data;

import java.io.Serializable;
import java.util.ArrayList;


public class CountrySet implements Serializable{

    private static final long serialVersionUID = 7526465875622776147L;

    ArrayList<Country> countries;

    /**
     *
     * @param countries The ArrayList of all the countries you wish the CountrySet to contain
     */
    public CountrySet(ArrayList<Country> countries){
        this.countries = countries;
    }

    /**
     *
     * @param list The ArrayList of countries you want the CountrySet to contain
     */
    public void setList(ArrayList<Country> list){
        this.countries = list;
    }

    /**
     *
     * @return Returns the ArrayList the CountrySet object contains that stores all the Country
     *  objects
     */
    public ArrayList<Country> getList(){
        return this.countries;
    }

    /**
     *
     * @param name The name the Country object you want contains
     * @return The country object with the specified name if there is one, otherwise null
     */
    public Country getCountryByName(String name){
        for(Country c: countries){
            if(c.getName().equals(name)){
                return c;
            }
        }
        return null;
    }
}
