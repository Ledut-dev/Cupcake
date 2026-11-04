package app.persistence;

import app.entities.Cake;
import app.entities.Topping;

import java.util.ArrayList;
import java.util.List;

public class CakeMapper {



    public List<Cake> allCakes(){

        List<Cake> allCake= new ArrayList<>();

        //SQL der henter alle kager
        //while rs.next
        //String query = """ ... """


        allCake.add(new Cake());

        return allCake;
    }


}
