package com.nju.elm.Dao.Mappers;


import com.nju.elm.Dao.Model.goodsOrder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "orderMapper")
public interface OrderDao {

    @Insert("insert into goodsOrder values" +
            "(null,#{userID},#{resID}," +
            "#{orderTime},#{arrivalTime},#{price}," +
            "#{address},#{addressXY},#{payType},#{State})")
    @Options(useGeneratedKeys=true, keyProperty="orderID")
    public void addNewOrder(goodsOrder o);


    @Update("update goodsOrder set arrivalTime=#{arrivalTime},payType=#{payType},State=#{State} where orderID=#{orderID}")
    public void updateOrder(goodsOrder o);

    @Select("select * from goodsOrder where orderID=#{orderID}")
    public goodsOrder getOrderByID(int orderID);
    @Select("select * from goodsOrder where userID=#{userID} order by orderID DESC")
    public List<goodsOrder> getOrdersByUser(int userID);
    @Select("select * from goodsOrder where resID=#{resID} order by orderID DESC")
    public List<goodsOrder> getOrdersByRes(int resID);

    @Delete("delete from goodsOrder where orderID=#{orderID}")
    public void deleteOrder(int orderID);





    @Select("select * from goodsOrder where orderID>#{idStart} and State='arrival'")
    public List<goodsOrder> getOrdersByTime(int idStart);






}
