package app.services;


import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;

import java.util.ArrayList;
import java.util.List;

import static app.entities.OrderStatus.PLACED;

public class OrderService {
   private ConnectionPool connectionPool;
   private OrderMapper orderMapper;


    public OrderService (ConnectionPool connectionPool){
        this.connectionPool=connectionPool;
        this.orderMapper=new OrderMapper(connectionPool);
    }



    public List<CupcakeTop> allCakeTops(){
       List <CupcakeTop> allCakeTops=null;
        try{
            allCakeTops=orderMapper.allCakeTops();
        }catch (DatabaseException d){
           d.getMessage();
       }return allCakeTops;
    }

    public List<CupcakeBottom> allCakeBottoms(){
        List<CupcakeBottom> allCakeBottoms=null;
            try {
                allCakeBottoms = orderMapper.allCakeBottoms();
            } catch (DatabaseException d){
                d.getMessage();
            }return allCakeBottoms;
        }

    public CupcakeBottom getCupcakeBottomByFlavor(String flavor){
        return null;
    }

    public CupcakeTop getCupcakeTopByFlavor(String flavor){
        return null;
    }

    public void saveOrder(User user) {
        int orderId=0;
            try {
                orderId = orderMapper.saveOrder(user);
            } catch (DatabaseException d){
                d.getMessage();
            }
        saveOrderline(user, orderId);
    }

    public void saveOrderline(User user, int id){


        for (Orderline o:user.getCart().getOrderlines()) {
            try{
            orderMapper.saveOrderline(o, id); //gem på bruger
            }catch (DatabaseException d){
            d.getMessage();
            }
        }
    }



}
