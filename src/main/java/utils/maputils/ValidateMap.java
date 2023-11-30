package utils.maputils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import model.Continent;
import model.Country;
import model.GameMap;


/**
 * A class which provides the logic for validating the game map
 * 
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 */

public class ValidateMap 
{
 /**
  * A method which validates the map by checking various different factors
  * @param p_GameMap a GameMap which has to be validated
  * @param p_MinimumCountryCount
  * @return true if all the validation factors are satisfied or else returns false
  */

    public static boolean validateMap(GameMap p_GameMap, int p_MinimumCountryCount) 
    {   if(checkContinentIsEmpty(p_GameMap)){
        
        return false;
    }
    if(checkDuplicateContinents(p_GameMap)){
        return false;
    }
    if(checkDuplicateCountries(p_GameMap)){
        return false;
    }
    if(!checkCountryCount(p_GameMap, p_MinimumCountryCount)){
        return false;
    }
    if(checkDuplicateNeighbours(p_GameMap)){
        return false;
    }
    if(checkIfNeighbourExist(p_GameMap)){
        if(!checkIfContinentIsConnected(p_GameMap)) {
            if(!checkIfMapIsConnected(p_GameMap)) {
                return false;
            }
        }
    }
    return true;
    }

    /**
     * A method which checks whether the continent is empty or not 
     * @param p_GameMap a GameMap which has to be validated
     * @return true if the validation is successful or else it returns false
     */

    public static boolean checkContinentIsEmpty(GameMap p_GameMap) 
    {
        // checks if there are no continents in the game map
        if (p_GameMap.getContinents().isEmpty()) 
        {
            
            return true;
        }
        for (Continent l_continent : p_GameMap.getContinents().values()) 
        {
            
            // checks if there are no countries in the continent
            if (l_continent.getCountries().isEmpty()) 
            {
                System.out.println("This continent " + l_continent.getContinentName() + " has no countries currently");
                return true;
            }
        }
    
        return false;
    }
   
   /**
    * A method which checks if there are duplicate continents present inside the game map
    * @param p_GameMap A gamemap which has to be validated
    * @return true if the validation is successful or else it return false
    */

    public static boolean checkDuplicateContinents(GameMap p_GameMap) 
    {
        Set<String> p_ContinentNames = p_GameMap.getContinents().keySet();
        Set<String> p_UniqueContinentNames = new HashSet<>(p_ContinentNames);
        if (p_UniqueContinentNames.size() != p_ContinentNames.size()) {
            System.out.println("There are duplicate continents present in the current map.");
            return true;
        }
        
        return false;
    }

/**
 * A method which checks if the neighbour is a part of country list
 * @param p_GameMap a game map which has to be validated
 * @return true if validation is successful or else return false
 */
     private static boolean checkIfNeighbourExist(GameMap p_GameMap) {
        Set<String> l_countryNames = p_GameMap.getCountries().keySet();
        Set<String> l_lowercaseCountryNames = l_countryNames.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        for (Continent continent : p_GameMap.getContinents().values()) {
            for (Country country : continent.getCountries()) {
                for (Country l_neighbour : country.getNeighbors()) {
                    String l_neighbourName = l_neighbour.getCountryName().toLowerCase();
                    if (!l_lowercaseCountryNames.contains(l_neighbourName)) {
                        System.out.println("Neighbor is not a part of the countries list - " + l_neighbour + ": neighbor");
                        return false;
                    }
                }
            }
        }
    
        return true;
    }

    /**
     * A method which checks if there are any duplicate countries present in the map
     * @param p_GameMap the game map
     * @return true if the validation is successful or else returns false
     */

    public static boolean checkDuplicateCountries(GameMap p_GameMap) 
    {
        Set<String> p_CountryNames = p_GameMap.getCountries().keySet();
        Set<String> p_UniqueCountryNames = new HashSet<>(p_CountryNames);
        if (p_UniqueCountryNames.size() != p_CountryNames.size()) {
            System.out.println("There are duplicate countries present in the current map.");
            return true;
        }

        return false;
    }

    /**
     * A method which checks if there are any duplicate neighbours 
     * @param p_GameMap the game map
     * @return true if validation is successful or else returns false
     */
    public static boolean checkDuplicateNeighbours(GameMap p_GameMap) {
        for (Continent l_Continent : p_GameMap.getContinents().values()) {
            for (Country l_Country : l_Continent.getCountries()) {
                Set<Country> l_Neighbours = l_Country.getNeighbors();
                if (hasDuplicateNeighbours(l_Neighbours)) {
                    System.out.println("There are duplicate neighbors present in the map.");
                    return true;
                }
            }
        }
    
        return false;
    }

 /**
  * A method which checks if the provided neighbour contains duplicates
  * @param p_Neighbours
  * @return true if validation is successful or else false
  */

    private static boolean hasDuplicateNeighbours(Set<Country> p_Neighbours) {
        Set<Country> p_UniqueNeighbours = new HashSet<>(p_Neighbours);
        return p_UniqueNeighbours.size() != p_Neighbours.size();
    }

/**
 * A method which checks that the country count is greater than minimum number of countries required
 * @param p_GameMap the game map
 * @param p_MinimumCountryCount
 * @return true if the validation is successful or else it returns false
 */

    public static boolean checkCountryCount(GameMap p_GameMap, int p_MinimumCountryCount) {
        if (p_GameMap.getCountries().size() <= p_MinimumCountryCount) {
            System.out.println("The number of countries"+ p_MinimumCountryCount +  "is less than the minimum countries required");
            return false;
        }
        return true;
    }
/**
 * Class to check the connectivity of the graph
 */
    static class ConnectedGraph {
        private int d_vertices;
        private List<Integer>[] d_edges;

        ConnectedGraph(int p_vertices) {
            this.d_vertices = p_vertices;
            d_edges = new ArrayList[p_vertices];
            for (int i = 0; i < p_vertices; i++) {
                d_edges[i] = new ArrayList<>();
            }
        }
/**
 * A method which create the edge between two vertices
 * @param p_vertex1 the vertex 1
 * @param p_vertex2 the vertex 2
 */
        void addEdge(int p_vertex1, int p_vertex2) {
            d_edges[p_vertex1].add(p_vertex2);
        }

   /**
    * A method to run dfs traversal
    * @param p_node where the traversal starts
    * @param p_visited hold the boolean value based on if it is visited or not
    */     
        private void dfsTraversal(int p_node, boolean[] p_visited) {
            p_visited[p_node] = true;
            for (int l_nextNode : d_edges[p_node]) {
                if (!p_visited[l_nextNode]) {
                    dfsTraversal(l_nextNode, p_visited);
                }
            }
        }
   /**
    * A method to get the transpose of a graph
   
    * @return the graph
    */

        private ConnectedGraph getTranspose() {
            ConnectedGraph l_graph = new ConnectedGraph(d_vertices);
            for (int l_vertex = 0; l_vertex < d_vertices; l_vertex++) {
                for (int nextNode : d_edges[l_vertex]) {
                    l_graph.d_edges[nextNode].add(l_vertex);
                }
            }
            return l_graph;
        }
/**
 * A method to check if the graph is strongly connected or not
 
 *@return true if validation is successful or else returns false
 */
        boolean checkIfStronglyConnected() {
            boolean[] l_visited = new boolean[d_vertices];
            dfsTraversal(0, l_visited);

            for (boolean l_visit : l_visited) {
                if (!l_visit) {
                    return false;
                }
            }

            ConnectedGraph l_transposedGraph = getTranspose();
            Arrays.fill(l_visited, false);
            l_transposedGraph.dfsTraversal(0, l_visited);

            for (boolean l_visit : l_visited) {
                if (!l_visit) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * A method to check whether the continent is connected or not
     
     * @param p_GameMap the game map
     * @return true if the continents are strongly connected or else returns false
     */
    public static boolean checkIfContinentIsConnected(GameMap p_GameMap) {
        Set<String> l_CountryNames = p_GameMap.getCountries().keySet();
        List<String> l_LowercaseCountryNames =new ArrayList<>();
        for(String l_Name : l_CountryNames) {
            l_LowercaseCountryNames.add(l_Name.toLowerCase());
        }

        int l_NumVertices = l_LowercaseCountryNames.size();
        ConnectedGraph l_Graph = new ConnectedGraph(l_NumVertices);

        for (int l_Vertex = 0; l_Vertex < l_NumVertices; l_Vertex++) {  
            for (Map.Entry<String, Continent> l_ContinentEntry : p_GameMap.getContinents().entrySet()) {
                for (Country l_Country : l_ContinentEntry.getValue().getCountries()) {
                    if (l_Country.getCountryName().equalsIgnoreCase(p_GameMap.getCountries().get(l_Country.getCountryName()).getCountryName())) {
                        Set<Country> l_Neighbors = l_Country.getNeighbors();
                        for (Country l_Current : l_Neighbors) {
                            int l_Index = l_LowercaseCountryNames.indexOf(l_Current.getCountryName().toLowerCase());
                            l_Graph.addEdge(l_Vertex, l_Index);
                        }
                    }
                }
            }
        }

        return checkMapConnectivity(l_Graph);
    }
/**
 * A method which checks if the whole map is strongly connected or not
 * @param p_GameMap the game map
 * @return true if the whole map is strongly connected or else returns false
 */
    public static boolean checkIfMapIsConnected(GameMap p_GameMap) {
        Set<String> l_CountryNames = p_GameMap.getCountries().keySet();
        List<String> l_LowercaseCountryNames = l_CountryNames.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        int l_NumVertices = l_LowercaseCountryNames.size();
        ConnectedGraph l_Graph = new ConnectedGraph(l_NumVertices);

        for (int l_Vertex = 0; l_Vertex < l_NumVertices; l_Vertex++) {
            for (Map.Entry<String, Country> l_CountryEntry : p_GameMap.getCountries().entrySet()) {
                Set<Country> l_Neighbours = l_CountryEntry.getValue().getNeighbors();
                for (Country l_Current : l_Neighbours) {
                    int l_Index = l_LowercaseCountryNames.indexOf(l_Current.getCountryName().toLowerCase());
                    l_Graph.addEdge(l_Vertex, l_Index);
                }
            }
        }

        return checkMapConnectivity(l_Graph);
    }
/**
 * A method to check the map connectivity based on graph connectivity
 * @param p_Graph
 * @return returns true if the graph is connected or else it returns false
 */
    private static boolean checkMapConnectivity(ConnectedGraph p_Graph) {
        return p_Graph.checkIfStronglyConnected();
    }

}


