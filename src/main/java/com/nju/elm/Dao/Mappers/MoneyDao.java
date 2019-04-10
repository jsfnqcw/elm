package com.nju.elm.Dao.Mappers;


import com.nju.elm.Dao.Model.Money.adminCal;
import com.nju.elm.Dao.Model.Money.resMoney;
import com.nju.elm.Dao.Model.Money.userMoney;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "moneyMapper")

public interface MoneyDao {
    @Insert("insert into userMoney values(#{userID},#{money})")
    public void addUserMoney(userMoney info);

    @Update("update userMoney set money=#{money} where userID=#{userID}")
    public void updateUserMoney(userMoney info);

    @Select("select * from userMoney where userID=#{userID}")
    public userMoney getUserMoney(int userID);

    @Delete("delete from userMoney where userID=#{userID}")
    public void deleteUserMoney(int userID);



    @Insert("insert into resMoney values(#{resID},#{money})")
    public void addResMoney(resMoney info);

    @Update("update resMoney set money=#{money} where resID=#{resID}")
    public void updateResMoney(resMoney info);

    @Select("select * from resMoney where resID=#{resID}")
    public resMoney getResMoney(int resID);

    @Delete("delete from resMoney where resID=#{resID}")
    public void deleteResMoney(int resID);



    @Insert("insert into adminCal values(null,#{calTime},#{calOrderId},#{price},#{num})")
    public void addAdminCal(adminCal info);

    @Select("select * from adminCal where adID=(select max(adID) from adminCal)")
    public adminCal getNewestAdminCal();
    @Select("select * from adminCal order by adID DESC")
    public List<adminCal> getAllAdminCal();

    @Delete("delete from adminCal where adID=#{adID}")
    public void deleteAdminCal(int adID);
}
