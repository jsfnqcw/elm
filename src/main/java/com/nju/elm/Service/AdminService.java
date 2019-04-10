package com.nju.elm.Service;

import com.nju.elm.Dao.Mappers.MoneyDao;
import com.nju.elm.Dao.Mappers.OrderDao;
import com.nju.elm.Dao.Mappers.ResDao;
import com.nju.elm.Dao.Mappers.UserDao;
import com.nju.elm.Dao.Model.Money.adminCal;
import com.nju.elm.Dao.Model.ResExamine;
import com.nju.elm.Dao.Model.ResInfo;
import com.nju.elm.Dao.Model.goodsOrder;
import com.nju.elm.Service.tools.TimeTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private ResDao resDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MoneyDao moneyDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private MoneyService moneyService;


    public boolean login(String username, String password, HttpServletRequest request){
        if(!username.equals("admin")){
            return false;
        }
        if(!password.equals("admin")){
            return false;
        }

        request.getSession().setAttribute("user","admin");
        return true;
    }





    ///////餐厅审批
    public List<ResExamine> getResExm(){
        try{
            return resDao.getResExamine();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean confirm(int resID){
        try{
            ResExamine i = resDao.getResExamineByResId(resID);
            ResInfo info = new ResInfo(i.getResID(),i.getName(),i.getAddress(),i.getAddressXY(),i.getType());
            resDao.updateResInfo(info);
            resDao.deleteResExamine(resID);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deny(int resID){
        try{
            resDao.deleteResExamine(resID);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public String numOfUsers(){
        return userDao.getUsers().size()+"";
    }

    public String numOfRes(){
        return resDao.getAllRes().size()+"";
    }

    public List<adminCal> finance(){
        try{
            return moneyDao.getAllAdminCal();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    ////////收益结算
    public boolean changePer(double per){
        try{
            adminCal adminCal1 = moneyDao.getNewestAdminCal();
            int idStart = adminCal1==null?1:adminCal1.getCalOrderId();
            List<goodsOrder> gl = orderDao.getOrdersByTime(idStart);
            if(gl == null){ return false;}


            int idEnd = idStart;double price = 0;

            for(goodsOrder go : gl){
                idEnd = go.getOrderID();
                moneyService.deliver(go.getResID(),go.getPrice()*per);
                price += go.getPrice();
            }

            adminCal adC = new adminCal(0, TimeTools.getTime(),idEnd,price,gl.size());
            moneyDao.addAdminCal(adC);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
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
