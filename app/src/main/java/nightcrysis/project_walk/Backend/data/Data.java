/**
 * A class for representing the types of data we are using
 */

package nightcrysis.project_walk.Backend.data;

public enum Data {
    LITERACY_RATE_MALE("Literacy rate, males (15-24)"),
    LITERACY_RATE_FEMALE("Literacy rate, female (15-25)"),
    PERSISTENCE_TO_GRADE_5_MALE("Persistence to grade 5 for males"),
    PERSISTENCE_TO_GRADE_5_FEMALE("Persistence to grade 5 for females"),
    SCHOOL_ENROLMENT_PRIMARY("School enrolment for primary school"),
    SCHOOL_ENROLMENT_SECONDARY("School enrolment for secondary school"),
    SCHOOL_ENROLMENT_TERTIARY("School enrolment for tertiary school"),
    SHARE_OF_WOMEN_IN_WAGE_EMPLOYMENT_IN_NONAGRICULTURAL_SECTOR("Women in wage employment in non agricultural sector"),
    SEATS_HELD_BY_WOMEN_IN_NATIONAL_PARLIAMENT("Seats held by women in national parliament"),
    TOTAL_POPULATION("Total population");

    private String name;

    Data(String name){
        this.name = name;
    }

    /**
     *
     * @return A string representation of the description of the topic of data
     */
    public String toString(){
        return name;
    }

    /**
     *
     * @param name The toString fo the enum
     * @return The enum if there is one that is equal, otherwise null
     */
    public static Data getEnumFromString(String name){
        for(Data d: Data.values()){
            if(name.equals(d.name)){
                return d;
            }
        }
        return null;
    }
}