package com.nju.elm.Dao.Mappers;

import com.nju.elm.Dao.Model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "resMapper")
public interface ResDao {
    @Insert("insert into ResInfo values(#{resID},#{name},#{address},#{addressXY},#{type})")
    public void addResInfo(ResInfo info);

    @Update("update ResInfo set name=#{name},address=#{address},addressXY=#{addressXY},type=#{type} where resID=#{resID}")
    public void updateResInfo(ResInfo info);

    @Select("select * from ResInfo where resID=#{resID}")
    public ResInfo getResInfo(int resID);
    @Select("select * from ResInfo")
    public List<ResInfo> getAllRes();

    @Delete("delete from ResInfo where resID=#{resID}")
    public void deleteResInfo(int resID);



    @Insert("insert into ResLogInfo values(null,#{email},#{password})")
    public void addResLogInfo(ResLogInfo info);

    @Select("select * from ResLogInfo where email=#{email}")
    public ResLogInfo getResLogInfoByEmail(String email);

    @Select("select * from ResLogInfo where resID=#{resID}")
    public ResLogInfo getResLogInfoById(int resID);



    @Insert("insert into ResExamine values(#{resID},#{name},#{address},#{addressXY},#{type})")
    public void addResExamine(ResExamine info);

    @Select("select * from ResExamine")
    public List<ResExamine> getResExamine();
    @Select("select * from ResExamine where resID=#{resID}")
    public ResExamine getResExamineByResId(int resID);

    @Update("update ResExamine set name=#{name},address=#{address},addressXY=#{addressXY},type=#{type} where resID=#{resID}")
    public void updateResExm(ResExamine info);

    @Delete("delete from ResExamine where resID=#{resID}")
    public void deleteResExamine(int resID);



    @Insert("insert into ResGoods values(null,#{name},#{introduction},#{price},#{allowance},#{startTime},#{endTime},#{resID})")
    public void addResGoods(ResGoods goods);

    @Select("select * from ResGoods where resID=#{resID}")
    public List<ResGoods> getResGoodsByResID(int resID);
    @Select("select * from ResGoods where id=#{id}")
    public ResGoods getResGoodsByID(int id);
    @Select("select * from ResGoods")
    public List<ResGoods> getALLResGoods();


    @Delete("delete from ResGoods where ID=#{ID}")
    public void deleteResGoods(int id);
}
