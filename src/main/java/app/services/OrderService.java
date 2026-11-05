package app.services;


import app.entities.CupcakeBottom;
import app.entities.CupcakeTop;
import app.entities.Order;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;

import java.util.List;

public class OrderService {
   private ConnectionPool connectionPool;
   private OrderMapper orderMapper;


    public OrderService (ConnectionPool connectionPool){
        this.connectionPool=connectionPool;
        this.orderMapper=new OrderMapper(connectionPool);
    }



    public List<CupcakeTop> allCakeTops(){
        return orderMapper.allCakeTops();
    }

    public List<CupcakeBottom> allCakeBottoms(){return orderMapper.allCakeBottoms();}

    public CupcakeBottom getCupcakeBottomByFlavor(String flavor){
        return null;
    }

    public CupcakeTop getCupcakeTopByFlavor(String flavor){
        return null;
    }

    public void saveCart(Order order) {


    }
}
