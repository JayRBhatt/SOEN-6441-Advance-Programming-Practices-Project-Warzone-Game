package model;


import java.util.HashSet;
import java.util.Set;

public class Continent 
{
    String d_ContinentID;
    String d_ContinentName;
    int d_ContinentValue;
    Set<Country> d_Countries;

    public Continent(String p_ContinentID, String p_ContinentName, int p_ContinentValue) 
    {
		this.d_ContinentID=p_ContinentID;
		this.d_ContinentName=p_ContinentName;
		this.d_ContinentValue=p_ContinentValue;
        this.d_Countries = new HashSet<>();
	}

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

    public void setContinentName(String p_ContinentName)
    {
        this.d_ContinentName= p_ContinentName;
    }

    public int getContinentValue()
    {
        return d_ContinentValue;
    }

    public void setContinentValue(int p_ContinentValue)
    {
        this.d_ContinentValue= p_ContinentValue;
    }

    public Set<Country> getCountries() {
        if (d_Countries == null) {
            d_Countries = new HashSet<>();
        }
        return d_Countries;
    }
}
