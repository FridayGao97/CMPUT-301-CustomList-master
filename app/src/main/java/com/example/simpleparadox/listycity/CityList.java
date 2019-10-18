package com.example.simpleparadox.listycity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This is a class that keeps track of a list of city objects
 *
 */
public class CityList {
    private List<City> cities = new ArrayList<>();

    /**
     * This method adds a city ti the list if the city does not exist
     * @param city this a candidate city add
     */
    public void add(City c){
        if(cities.contains(c)){
            throw new IllegalArgumentException();
        }
        else{
            cities.add(c);
        }

    }
    /**
     * This method return a sorted list of cities
     * @return Return the sorted list
     */
    public List<City> getCities(){
        List<City> list = cities;
        Collections.sort(list);
        return list;
    }

    /**
     * This method check whether some city is in the city list
     * @param City c
     * @return Boolean value
     */
    public Boolean hasCity(City c){
        return cities.contains(c);
    }

    /**
     * This method delete a city c from city list
     * @param City c
     */
    public void delete(City c){
        if(!cities.contains(c)){
            throw new IllegalArgumentException();
        }
        cities.remove(c);
    }

    /**
     * This method count the number of cities in the city list
     * @return the total number of cities
     */
    public int countCities(){
        return cities.size();
    }

}
