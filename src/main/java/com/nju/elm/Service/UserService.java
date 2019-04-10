package com.nju.elm.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.nju.elm.Dao.Mappers.OrderDao;
import com.nju.elm.Dao.Mappers.UserDao;
import com.nju.elm.Dao.MangoDBService;
import com.nju.elm.Dao.Model.*;
import com.nju.elm.Dao.Model.MangoDB.RegisterQueue;
import com.nju.elm.Service.tools.Distance;
import com.nju.elm.Service.tools.RegisterTools;
import com.nju.elm.Service.tools.TimeTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    final public static int Timeset = 240; //设置验证码的生存周期（秒），生存周期内验证码保持不变
    final public static int[] expList = {0,10,20,50,100,200,500,9999999};


    //////////账号注册登录、验证相关///////////////////
    ////////////////////////////////////////////////
    public void newRegister(String email){
        int id = 0;
        UserLogInfo info = new UserLogInfo(id,email,"Using");
        userDao.addUL(info);
        int ID = userDao.getUserLogInfo(email).getID();
        userDao.addU(new UserInfo(ID,"ELM用户$"+ID,"0",1,0));
    }


    public boolean sendVerificationCode(String email){
        String validateNum = findVerificationCode(email);
        if(validateNum == null){
        String Code = RegisterTools.getRandomValidateNum();
        RegisterQueue Queue = new RegisterQueue(email,Code);
        MangoDBService.getInstance().saveRegisterQueue(Queue);
        return RegisterTools.sendVertifyCode(email,Code);
    }
        return RegisterTools.sendVertifyCode(email,validateNum);
}

    public String findVerificationCode(String email){
        RegisterQueue Queue = MangoDBService.getInstance().findRegisterQueue(email);
        if(Queue != null){
            int time = RegisterTools.calcuTime(Queue.getCreateTime());
            if(time>=Timeset){
                MangoDBService.getInstance().deleteRegisterQueue(email);
                return null;
            }
            return Queue.getValidateNumber();
        }
        return null;
    }

    public boolean Register(String email,String inputVerificationCode){
        String actualCode = findVerificationCode(email);
        if(actualCode == null || !(actualCode.equals(inputVerificationCode))){
            return false;
        }
        else{
            UserLogInfo info = userDao.getUserLogInfo(email);
            if(info == null){
                newRegister(email);
            }
            return true;
        }
    }

    public boolean login(String email, String inputVerificationCode, HttpServletRequest request){
        String actualCode = findVerificationCode(email);
        if(actualCode == null || !(actualCode.equals(inputVerificationCode))){
            return false;
        }
        else{
            UserLogInfo info = userDao.getUserLogInfo(email);
            if(info == null){
                newRegister(email);
            }else{
                if (info.getCancelState().equals("cancel")){
                    return false;
                }
            }
            int ID = userDao.getUserLogInfo(email).getID();
            HttpSession session = request.getSession(true);
            session.setAttribute("user",email);
            session.setAttribute("id",ID);

            return true;
        }

    }

    public boolean cancel(String email,HttpServletRequest request){
        HttpSession session = request.getSession(true);
        String actualEmail = (String)(session.getAttribute("user"));

        if (actualEmail.equals(email)){
            userDao.cancelUL(email);
            return true;
        }
        return false;
    }
    ////////////////////////////////////////////////


    //////////账号信息查询、修改相关///////////////////
    ////////////////////////////////////////////////
    public UserInfo getUserInfo(int id){
        try{
            return userDao.getUserInfo(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean newUserInfo(int id){
        try{
            UserInfo newInfo = new UserInfo(id,"ELM用户$"+id,"0",1,0);
            userDao.addU(newInfo);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean modifyInfo(String name,String phoneNumber,HttpServletRequest request){
        try {
            HttpSession session = request.getSession();
            int id = (int) (session.getAttribute("id"));
            UserInfo info = userDao.getUserInfo(id);

            if(info == null){
                return newUserInfo(id);
            }

            info.setName(name);info.setPhoneNumber(phoneNumber);

            userDao.updateU(info);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public String expUp(int id,int Num){
        UserInfo info = userDao.getUserInfo(id);
        int exp = info.getExp()+Num;
        info.setExp(exp);
        if(exp >= expList[info.getLevel()]){
            info.setLevel(info.getLevel()+1);
            userDao.updateU(info);
            return "lvlUp";
        }
        userDao.updateU(info);
        return "expUp";

    }


    public boolean addAddress(String addressString,String XY,int id){
        String[] t = XY.split(",");
        String X = t[0];String Y = t[1];

        try{
            UserAddress address = new UserAddress(addressString,X,Y,id);
            if(userDao.findUserAddress(address)==null){
                userDao.addUA(address);
                return true;
            }
            return false;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public boolean delAddress(String addressString,int id){
        try{
            UserAddress address = new UserAddress(addressString,"","",id);
            if(userDao.findUserAddress(address)==null){
                return false;
            }else{
                userDao.deleteUA(address);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public String getAddress(int id){
        try{
            List<UserAddress> jymdList = new ArrayList<>(userDao.getUserAddress(id));
            return JSON.toJSONString(jymdList);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }




    public boolean example(){
        try{

            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}

