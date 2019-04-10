package com.nju.elm.Service;

import com.nju.elm.Dao.MangoDBService;
import com.nju.elm.Dao.Mappers.ResDao;
import com.nju.elm.Dao.Mappers.UserDao;
import com.nju.elm.Dao.Model.*;
import com.nju.elm.Dao.Model.MangoDB.Discount;
import com.nju.elm.Dao.Model.MangoDB.RegisterQueue;
import com.nju.elm.Service.tools.RegisterTools;
import com.nju.elm.Service.tools.TimeTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

@Service
public class StoreService {
    @Autowired
    private ResDao resDao;

    final public static int Timeset = 240; //设置验证码的生存周期（秒），生存周期内验证码保持不变

    ///////////session中的餐厅ID为"resID"////////////

    //////////餐厅注册登录、验证相关///////////////////
    ////////////////////////////////////////////////
    public int newRegister(String email, String password) {
        ResLogInfo info = new ResLogInfo(0, email, password);
        resDao.addResLogInfo(info);
        int resID = resDao.getResLogInfoByEmail(email).getResID();
        resDao.addResInfo(new ResInfo(resID, "ELM餐厅$" + resID, "", "", ""));
        return resID;
    }

    public boolean sendVerificationCode(String email) {
        String validateNum = findVerificationCode(email);
        if (validateNum == null) {
            String Code = RegisterTools.getRandomValidateNum();
            RegisterQueue Queue = new RegisterQueue(email, Code);
            MangoDBService.getInstance().saveRegisterQueue(Queue);
            return RegisterTools.sendVertifyCode(email, Code);
        }
        return RegisterTools.sendVertifyCode(email, validateNum);
    }

    public String findVerificationCode(String email) {
        RegisterQueue Queue = MangoDBService.getInstance().findRegisterQueue(email);
        if (Queue != null) {
            int time = RegisterTools.calcuTime(Queue.getCreateTime());
            if (time >= Timeset) {
                MangoDBService.getInstance().deleteRegisterQueue(email);
                return null;
            }
            return Queue.getValidateNumber();
        }
        return null;
    }

    public String Register(String email, String password, String inputVerificationCode) {
        String actualCode = findVerificationCode(email);
        if (actualCode == null || !(actualCode.equals(inputVerificationCode))) {
            return null;
        } else {
            ResLogInfo info = resDao.getResLogInfoByEmail(email);
            if (info == null) {
                return "" + newRegister(email, password);
            }
            return null;
        }
    }

    public boolean login(int resID, String password, HttpServletRequest request) {
        ResLogInfo info = resDao.getResLogInfoById(resID);
        if (info == null || !(info.getPassword().equals(password))) {
            return false;
        } else {
            int ID = info.getResID();
            HttpSession session = request.getSession(true);
            session.setAttribute("user", ID);
            session.setAttribute("resID", ID);
            return true;
        }
    }

    ////////////////////////////////////////////////


    //////////餐厅信息查询、修改相关///////////////////
    ////////////////////////////////////////////////
    public ResInfo getInfo(int resID) {
        try {
            return resDao.getResInfo(resID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean applyForModifyInfo(ResExamine info) {
        try {
            if (resDao.getResExamineByResId(info.getResID()) == null) {
                resDao.addResExamine(info);
            }
            resDao.updateResExm(info);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ResInfo> getStores() {
        try {
            return resDao.getAllRes();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /////////////////货品相关////////////////////////
    ////////////////////////////////////////////////
    public List<ResGoods> getGoodsByRes(int resID) {
        try {
            List<ResGoods> goods = resDao.getResGoodsByResID(resID);
            Iterator<ResGoods> it = goods.iterator();
            while(it.hasNext()){
                ResGoods x = it.next();
                if(!TimeTools.ifNowInTimeSection(x.getStartTime(),x.getEndTime())){
                    it.remove();
                }
            }
            return resDao.getResGoodsByResID(resID);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResGoods getGoodsByID(int goodsID) {
        try {
            return resDao.getResGoodsByID(goodsID);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addGoods(ResGoods goods) {
        try {
            resDao.addResGoods(goods);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delGoods(int goodsID) {
        try {
            resDao.deleteResGoods(goodsID);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public String getDiscount(int resID) {
        try {
            Discount discount = MangoDBService.getInstance().findDiscount(resID);
            if (discount == null) {
                return null;
            }

            return discount.getDiscount();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delDiscount(int resID) {
        try {
            MangoDBService.getInstance().deleteDiscount(resID);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addDiscount(int resID, int price, int dis) {
        try {
            MangoDBService.getInstance().addDiscount(resID, price, dis);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean example() {
        try {

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
