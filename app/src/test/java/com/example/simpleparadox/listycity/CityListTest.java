package com.example.simpleparadox.listycity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CityListTest {

    private CityList mockCityList() {
        CityList cityList = new CityList();
        cityList.add(mockCity());
        return cityList;
    }
    private City mockCity() {
        return new City("Edmonton", "Alberta");
    }

    @Test
    void testAdd() {
        CityList cityList = mockCityList();

        assertEquals(1, cityList.getCities().size());

        City city = new City("Regina", "Saskatchewan");
        cityList.add(city);

        assertEquals(2, cityList.getCities().size());
        assertTrue(cityList.getCities().contains(city));
    }

    @Test
    void testAddException() {
        CityList cityList = mockCityList();

        City city = new City("Yellowknife", "Northwest Territories");
        cityList.add(city);

        assertThrows(IllegalArgumentException.class,()->{
            cityList.add(city);
        });
    }

    @Test
    void testGetCities() {
        CityList cityList = mockCityList();

        assertEquals(0, mockCity().compareTo(cityList.getCities().get(0)));

        City city = new City("Charlottetown", "Prince Edward Island");
        cityList.add(city);

        assertEquals(0, city.compareTo(cityList.getCities().get(0)));
        assertEquals(0, mockCity().compareTo(cityList.getCities().get(1)));
    }

    @Test
    void testHasCity(){
        CityList cityList = mockCityList();
        assertEquals(1, cityList.getCities().size());

        City city = new City("Charlottetown", "Prince Edward Island");
        cityList.add(city);

        City city2 = new City("NonExist", "Null");

        assertTrue(cityList.hasCity(city));
        assertFalse(cityList.hasCity(city2));

    }

    @Test
    void testDelete() {
        CityList cityList = mockCityList();

        assertEquals(1, cityList.getCities().size());

        City city = new City("Regina", "Saskatchewan");
        cityList.add(city);
        assertEquals(2, cityList.getCities().size());
        assertTrue(cityList.getCities().contains(city));

        cityList.delete(city);
        assertEquals(1, cityList.getCities().size());
        assertFalse(cityList.getCities().contains(city));
    }

    @Test
    void testDeleteException() {
        CityList cityList = mockCityList();

        City city = new City("Yellowknife", "Northwest Territories");
        cityList.add(city);
        City city2 = new City("Shanghai", "Shanghai");

        assertThrows(IllegalArgumentException.class,()->{
            cityList.delete(city2);
        });
    }

    @Test
    void testCountCities() {
        CityList cityList = mockCityList();
        assertEquals(1, cityList.countCities());

        City city = new City("Charlottetown", "Prince Edward Island");
        cityList.add(city);

        assertEquals(2, cityList.countCities());
        City city2 = new City("Shanghai", "Shanghai");
        cityList.add(city2);

        assertEquals(3, cityList.countCities());

        //cityList.delete(city);
        //assertEquals(2, cityList.countCities());
    }


}