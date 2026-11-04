package app.services;

import app.entities.Cake;
import app.entities.Topping;
import app.persistence.CakeMapper;
import app.persistence.OrderMapper;
import app.persistence.ToppingMapper;


import java.util.List;

public class OrderService {


    private ToppingMapper toppingMapper;
    private CakeMapper cakeMapper;


    public OrderService (){
        toppingMapper=new ToppingMapper();
        cakeMapper = new CakeMapper();
    }



    public List<Topping> allToppings(){
        return toppingMapper.AllToppings();
    }

    public List<Cake>allCakes(){
        return cakeMapper.AllCakes();
    }


}
