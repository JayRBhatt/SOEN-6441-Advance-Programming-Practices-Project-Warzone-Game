package model;


import java.util.HashSet;
import java.util.Set;

public class Continent 
{
    String d_ContinentID;
    String d_ContinentName;
    int d_ContinentValue;
    Set<Country> d_Countries;


    public String getContinentId() 
    {
        return d_ContinentID;
    }

    public void setContinentId(String p_ContinentID)
    {
        this.d_ContinentID = p_ContinentID;
    }

    public String getContinentName() 
    {
        return d_ContinentName;
    }

    public void setContinentName(String p_ContinentName) {

        System.out.println("methid");
        this.d_ContinentName = p_ContinentName;
        System.out.println("para" + p_ContinentName + "::::  this vali value ::->" + this.d_ContinentName);
    }

    public int getContinentValue()
    {
        return d_ContinentValue;
    }

    public void setContinentValue(int p_ContinentValue)
    {
        this.d_ContinentValue= p_ContinentValue;
    }

    public Set<Country> getCountries() 
    {
        if (d_Countries == null) {
            d_Countries = new HashSet<>();
        }
        return d_Countries;
    }
}
