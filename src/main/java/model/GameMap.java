package model;

public class GameMap
{


// public GameMap(){

//     this.d_continents= new HashMap<>();
//     this.d_countries= new HashMap<>();
//     this.d_players= new HashMap<>();
// }
 
//     private HashMap<String,Continents> d_continents;
//     private HashMap<String,Country> d_countries ;
//     private HashMap<String,Player> d_players;
//     private String d_invalidMessage;


//     public HashMap<String, Continents> getContinents()
//     {
//         return d_continents;
//     }

//     public Continent getContinent(String p_ID)
//     {
//         return d_continents.get(p_ID);
//     }

//     public HashMap<String, Countries> getCountries()
//     {
//         return d_countries;
//     }

//     public Country getCountry(String p_ID)
//     {
//         return d_countries.get(p_ID);
//     }

//     public HashMap<String, Players> getPlayers()
//     {
//         return d_players;
//     }

//     public Player getPlayer(String p_ID)
//     {
//         return d_players.get(p_ID);
//     }

//     public String getInvalidMessage()
//     {
//         return d_InvalidMessage;
//     }

//     public void setInvalidMessage(String p_InvalidMessage)
//     {
//         this.d_InvalidMessage = p_InvalidMessage;
//     }

//     public String getName()
//     {
//         return d_Name;
//     }

//     public String setName(String p_Name)
//     {
//         this.d_Name =  p_Name;
//     }
// ////////////////////////////
//     public void ClearMap()
//     {
//         GameMap.getInstance().getContinents().clear();
//         GameMap.getInstance().getCountries().clear();
//         GameMap.getInstance().getPlayers().clear();
//     }
// ////////////////////////////

//     public void addContinent(String p_ContinentName, String p_TroopsValue)
//     {
//         //
//         Continent l_Continent = new Continent();
//         l_Continent.setContinentName(p_ContinentName);
//         this.getContinents().put(p_ContinentName,l_Continent);
//         System.out.println("Woohooo! You have added a Continnet to your World Map!!");
//     }

//     public void removeContinent(String p_ContinentName)
//     {
//         Set<String> l_CountrySet = this.getContinents().remove(p_ContinentName).getCountries().stream().map(Country::getName).collect()Collectors.toSet();
//         for(String l_Country: l_CountrySet)
//         {
//             this.getCountries().remove(l_Country);
//         }
//         System.out.println("WOW!!"+ p_ContinentName +"is off the Map!!!");
//     }

//     public void addCountry(String p_CountryName, String p_ContinentName)
//     {
//         Country l_Country=new Country();
//         l_Country.setCountryName(p_CountryName);
//         this.getCountries().put(p_CountryName, l_Country);
//         System.out.println(""); 
//     }
}