package com.nju.elm.Service;


import com.nju.elm.Dao.Mappers.MoneyDao;
import com.nju.elm.Dao.Model.Money.resMoney;
import com.nju.elm.Dao.Model.Money.userMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneyService {

    @Autowired
    private MoneyDao moneyDao;

    public boolean newUser(int userID){
        try{
            if(findUserMoney(userID)==null) {
                userMoney money = new userMoney(userID, 0.0);
                moneyDao.addUserMoney(money);
                return true;
            }
            return false;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean newUserWith100Money(int userID){
        try{
            if(findUserMoney(userID)==null) {
                userMoney money = new userMoney(userID, 100.0);
                moneyDao.addUserMoney(money);
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public userMoney findUserMoney(int userID){
        try{
            return moneyDao.getUserMoney(userID);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String payMoney(int userID,double price){
        try{
            userMoney um = findUserMoney(userID);
            if(um == null){
                return "noAccount";
            }
            if(um.getMoney()<price){
                return "lackofmoney";
            }else {
                um.setMoney(um.getMoney() - price);
                moneyDao.updateUserMoney(um);
                return "Success";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    public boolean returnMoney(int userID,double price){
        try{
            userMoney um = findUserMoney(userID);
            um.setMoney(um.getMoney() + price);
            moneyDao.updateUserMoney(um);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



    public boolean deliver(int resID,double money){

        try{
            resMoney rm = moneyDao.getResMoney(resID);
            if(rm == null){
                rm = new resMoney(resID,money);
                moneyDao.addResMoney(rm);
            }else {
                rm.setMoney(rm.getMoney() + money);
                moneyDao.updateResMoney(rm);
            }
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
