package com.nju.elm.Dao.Mappers;

import com.nju.elm.Dao.Model.UserAddress;
import com.nju.elm.Dao.Model.UserInfo;
import com.nju.elm.Dao.Model.UserLogInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "userMapper")
public interface UserDao {

    @Insert("insert into UserInfo(id,name,phoneNumber,level,exp) values(#{ID},#{name},#{phoneNumber},#{level},#{exp})")
    public void addU(UserInfo info);

    @Update("update UserInfo set name=#{name},phoneNumber=#{phoneNumber},level=#{level},exp=#{exp} where id=#{ID}")
    public void updateU(UserInfo info);

    @Delete("delete from UserInfo where id=#{ID}")
    public void deleteU(int id);

    @Select("select * from UserInfo where id=#{ID}")
    public UserInfo getUserInfo(int id);
    @Select("select * from UserInfo")
    public List<UserInfo> getUsers();


    @Insert("insert into UserLogInfo(id,email,cancelState) values(null,#{email},#{cancelState})")
    public void addUL(UserLogInfo info);

    @Update("update UserLogInfo set cancelState='cancel' where email=#{email}")
    public void cancelUL(String email);

    @Select("select * from UserLogInfo where email=#{email}")
    public UserLogInfo getUserLogInfo(String email);


    @Insert("insert into UserAddress(addressString,addressX,addressY,id) values(#{addressString},#{addressX},#{addressY},#{ID})")
    public void addUA(UserAddress address);

    @Delete("delete from UserAddress where addressString=#{addressString} and id=#{ID}")
    public void deleteUA(UserAddress address);

    @Select("select * from UserAddress where id=#{ID}")
    public List<UserAddress> getUserAddress(int id);

    @Select("select * from UserAddress where addressString=#{addressString} and id=#{ID}")
    public UserAddress findUserAddress(UserAddress address);

}
