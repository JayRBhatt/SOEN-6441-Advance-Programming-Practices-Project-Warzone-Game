package utils.maputils;

import model.GameMap;

public class MapViewer {
    public static void readMap() throws ValidationException {
        try {
            p_GameMap.ClearMap();
            File l_FileLoaded = new File("maps/" + p_FileName);
            FileReader l_FileReader = new FileReader(l_FileLoaded);
            Map<String, List<String>> l_FileContent = new HashMap<>();
            String l_CurrentKey = "";
            BufferedReader l_Buffer = new BufferedReader(l_FileReader);
            while (l_Buffer.ready()) {
                String l_read = l_Buffer.readLine();
                if (!l_Read.isEmpty()) {
                    if (l_Read.contains("[") && l_Read.contains("]")) {
                        l_CurrentKey = l_Read.replace("[", "").replace("]", "");
                        l_MapFileContents.put(l_CurrentKey, new ArrayList<>());
                    } else {
                        l_MapFileContents.get(l_CurrentKey).add(l_Read);
                    }
                }
            }

            readContinentsFromFile(p_GameMap, l_MapFileContents.get("Continents"));
            Map<String, List<String>> l_CountryNeighbors = readCountriesFromFile(p_GameMap,
                    l_MapFileContents.get("Territories"));
            addNeighborsFromFile(p_GameMap, l_CountryNeighbors);
        } catch (ValidationException | IOException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    public static void readContinentsFromFile(GameMap p_GameMap, List<String> p_ContinentArray)
            throws ValidationException {
        for (String l_InputString : p_ContinentArray) {
            String[] l_InputArray = l_InputString.split(" ");
            if (l_InputArray.length == 2) {
                p_GameMap.addContinent(l_InputArray[0], l_InputArray[1]);
            }
        }
    }

    public static Map<String, List<String>> readCountriesFromFile(GameMap p_GameMap, List<String> p_CountryArray)
            throws ValidationException {
        Map<String, List<String>> l_NeighborCountries = new HashMap<>();
        for (String l_CountryString : p_CountryArray) {
            List<String> l_CountryStringArray = Arrays.stream(l_CountryString.split(" ")).collect(Collectors.toList());
            if (l_CountryStringArray.size() >= 2) {
                p_GameMap.addCountry(l_CountryStringArray.get(0), l_CountryStringArray.get(1));
                l_NeighborCountries.put(l_CountryStringArray.get(0),
                        l_CountryStringArray.subList(2, l_CountryStringArray.size()));
            }
        }
        return l_NeighborCountries;
    }

    public static void addNeighborsFromFile(GameMap p_GameMap, Map<String, List<String>> p_NeighborList)
            throws ValidationException {
        for (String l_Country : p_NeighborList.keySet()) {
            for (String l_Neighbor : p_NeighborList.get(l_Country)) {
                p_GameMap.addNeighbor(l_Country, l_Neighbor);
            }
        }
    }
}