package app.services;

import app.entities.CupcakeBottom;
import app.entities.CupcakeTop;
import app.persistence.OrderMapper;


import java.util.List;

public class OrderService {
   private ConnectionPool connectionPool;
   private OrderMapper orderMapper;


    public OrderService (){
        this.connectionPool=connectionPool;
        this.orderMapper=new OrderMapper(connectionPool);
    }



    public List<CupcakeTop> allCakeTops(){
        return orderMapper.allCakeTops();
    }

    public List<CupcakeBottom> allCakeBottoms(){
        return orderMapper.allCakeBottoms();
    }


}
