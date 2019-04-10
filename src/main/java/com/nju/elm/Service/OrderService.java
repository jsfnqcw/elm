package com.nju.elm.Service;

import com.alibaba.fastjson.JSONObject;
import com.nju.elm.Controllers.pojo.discountUnit;
import com.nju.elm.Controllers.pojo.goodsOrderUnit;
import com.nju.elm.Dao.MangoDBService;
import com.nju.elm.Dao.Mappers.OrderDao;
import com.nju.elm.Dao.Mappers.ResDao;
import com.nju.elm.Dao.Model.MangoDB.OrderGoods;
import com.nju.elm.Dao.Model.ResGoods;
import com.nju.elm.Dao.Model.ResInfo;
import com.nju.elm.Dao.Model.UserInfo;
import com.nju.elm.Dao.Model.goodsOrder;
import com.nju.elm.Service.tools.Distance;
import com.nju.elm.Service.tools.TimeTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private ResDao resDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MoneyService moneyService;

    @Autowired
    private UserService userService;
    @Autowired
    private StoreService storeService;

    final public static int Timeset = 600; //设置验证码的生存周期（秒），生存周期内未支付订单有效
    final public static int maxDistance = 8000; //外卖的最远配送距离
    ////////订单状态限制
    final public static String[] stateArrys =
            {"notPaid","paid","timeout","canceled","unsubscribe","arrival"};
    final public static String
            notPaid = stateArrys[0],
            paid = stateArrys[1],
            timeout = stateArrys[2],
            canceled = stateArrys[3],
            unsubscribe = stateArrys[4],
            arrival = stateArrys[5];

    final public static double paidReturn = 0.95;
    final public static double arrivalReturn = 0.6;




    //////////下单相关///////////////////////////////
    ////////////////////////////////////////////////


    public String newOrder(int userID, int resID, String shoppingBag, String addressString, String addressXY){
        try{
            ResInfo info = resDao.getResInfo(resID);
            String resAddressXY = info.getAddressXY();

            ////////判断距离
            double distance = Distance.getDistanceByStringXY(addressXY,resAddressXY);
            if(distance>=maxDistance){
                return "TooLong!";
            }


            double price = 0;
            //////计算总价以及是否超卖(1_原价计算)
            List<goodsOrderUnit> goodslist = JSONObject.parseArray(shoppingBag,goodsOrderUnit.class);
            for(goodsOrderUnit tmp : goodslist){
                ResGoods good = resDao.getResGoodsByID(Integer.parseInt(tmp.getGoodsID()));
                if(tmp.getNum()>=good.getAllowance()){
                    return "商品余量不足！";
                }
                price += good.getPrice()*tmp.getNum();
            }
            ////////(2_打折计算)
            List<discountUnit> discountUnits = JSONObject.parseArray(
                    MangoDBService.getInstance().findDiscount(resID).getDiscount(),discountUnit.class);
            if(price >= discountUnits.get(0).getPrice()) {
                for (int i = discountUnits.size()-1; i >= 0 ; i--) {
                    if (price > discountUnits.get(i).getPrice()) {
                        price -= discountUnits.get(i).getDis();
                        break;
                    }
                    continue;
                }
            }

            String time = TimeTools.getTime();
            goodsOrder o = new goodsOrder(0,userID,resID,time,"",price,
                    addressString,addressXY,"",notPaid);
            orderDao.addNewOrder(o);
            String orderID = o.getOrderID()+"";
            MangoDBService.getInstance().saveOrderGoods(new OrderGoods(orderID,shoppingBag));
            return "Done";

        }catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }

    public String payOrder(int OrderID,int userID){
        goodsOrder o = orderValidate(OrderID,userID);
        if(o == null) {
            return "订单信息错误！";
        }

        /////只有处于notPaid状态的订单才能被支付
        if(o == null || !(o.getState().equals(notPaid)) ){
            return "订单信息错误！";
        }

        ///////判断时间是否过期
        String time = o.getOrderTime();
        if(TimeTools.ifMorethanXsecond(time,Timeset)){
            o.setState(timeout);
            orderDao.updateOrder(o);
            return "该订单已超时，请重新下单并在"+Timeset+"秒内支付。";
        }

        ///////支付
        String result =  moneyService.payMoney(userID,o.getPrice());

        if(result.equals("noAccount")){
            return "请先绑定网银账号噢!";
        }
        if(result.equals("lackofmoney")){
            return "余额不足，交易失败！";
        }
        if(result.equals("error")){
            return "系统错误，交易失败！";
        }

        if(result.equals("Success")){
            o.setPayType("模拟网银");
            o.setState(paid);
            orderDao.updateOrder(o);
            return "交易成功！";
        }


        return "系统错误，交易失败！";
    }

    public String cancelOrder(int OrderID,int userID){
        goodsOrder o = orderValidate(OrderID,userID);
        if(o == null) {
            return "订单信息错误！";
        }

        String state = o.getState();
        if(state.equals(canceled) || state.equals(timeout) || state.equals(unsubscribe)){
            return "订单信息错误！";
        }
        if(state.equals(notPaid)){
            o.setState(canceled);
            orderDao.updateOrder(o);
            return "已取消";
        }

        double price = o.getPrice();
        if(state.equals(paid)){
            o.setState(unsubscribe);
            moneyService.returnMoney(userID,price*paidReturn);
            orderDao.updateOrder(o);
            return "已取消,退回0.95倍的钱。";
        }
        if(state.equals(arrival)){
            o.setState(unsubscribe);
            moneyService.returnMoney(userID,price*arrivalReturn);
            orderDao.updateOrder(o);
            return "已取消,退回0.6倍的钱。";
        }


        return "订单信息错误！";
    }

    public String confirmOrder(int OrderID,int userID){
        goodsOrder o = orderValidate(OrderID,userID);
        if(o == null || !(o.getState().equals(paid))) {
            return "订单信息错误！";
        }
        o.setState(arrival);
        o.setArrivalTime(TimeTools.getTime());
        orderDao.updateOrder(o);

        userService.expUp(userID,5);
        return "已经成功签收歪卖";
    }


    public goodsOrder orderValidate(int OrderID,int userID){
        goodsOrder o = orderDao.getOrderByID(OrderID);
        if(o == null || o.getUserID()!=userID){
            return null;
        }
        return o;
    }



    public List<goodsOrder> getOrdersByUser(int UserId){
        try{
            List<goodsOrder> orders =  orderDao.getOrdersByUser(UserId);
            return orders;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public List<goodsOrder> getOrdersByUserSelected(int UserId,int time,int price,String type){
        try{
            List<goodsOrder> orders =  orderDao.getOrdersByUser(UserId);
            List<goodsOrder> result = new ArrayList<>();
            for(goodsOrder o :orders){
                if(time != 0){
                    int gap = TimeTools.getMonthGap(o.getOrderTime());
                    if(time == 3 && gap<2){
                        continue;
                    }
                    if(time != 3 && gap != time-1){
                        continue;
                    }
                }
                if(price !=0){
                    if(price == 51 && o.getPrice()<=50){
                        continue;
                    }
                    if(price != 51 && o.getPrice()>price){
                        continue;
                    }
                }
                if(!type.equals("0") && !storeService.getInfo(o.getResID()).getType().equals(type)){
                    continue;
                }
                result.add(o);
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public List<goodsOrder> getOrdersByRes(int resId){
        try{
            List<goodsOrder> orders =  orderDao.getOrdersByRes(resId);
            return orders;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public List<goodsOrder> getOrdersByResSelected(int resID,int time,int price,String user){
        try{
            List<goodsOrder> orders =  orderDao.getOrdersByRes(resID);
            List<goodsOrder> result = new ArrayList<>();
            for(goodsOrder o :orders){
                if(time != 0){
                    int gap = TimeTools.getMonthGap(o.getOrderTime());
                    if(time == 3 && gap<2){
                        continue;
                    }
                    if(time != 3 && gap != time-1){
                        continue;
                    }
                }
                if(price !=0){
                    if(price == 51 && o.getPrice()<=50){
                        continue;
                    }
                    if(price != 51 && o.getPrice()>price){
                        continue;
                    }
                }
                if(!user.equals("0") && !userService.getUserInfo(o.getUserID()).getName().equals(user)){
                    continue;
                }
                result.add(o);
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public List<String> getOrderResTypes(int UserId){
        try{
            List<goodsOrder> orders =  orderDao.getOrdersByUser(UserId);
            if(orders == null){return null;}
            List<String> result = new ArrayList<>();

            for(goodsOrder o : orders){
                ResInfo info = storeService.getInfo(o.getResID());
                if(info == null){continue;}

                String tmp = info.getType();
                if(result.indexOf(tmp) != -1){
                    continue;
                }
                result.add(tmp);
            }

            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public List<String> getOrderUser(int resID){
        try{
            List<goodsOrder> orders =  orderDao.getOrdersByRes(resID);
            if(orders == null){return null;}
            List<String> result = new ArrayList<>();

            for(goodsOrder o : orders){
                UserInfo info = userService.getUserInfo(o.getUserID());
                if(info == null){continue;}

                String tmp = info.getName();
                if(result.indexOf(tmp) != -1){
                    continue;
                }
                result.add(tmp);
            }

            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public String getOrderGoods(String orderId){
        try{
            return MangoDBService.getInstance().findOrderGoods(orderId).getGoods();
        }catch (Exception e){
            e.printStackTrace();
            return "操作失败！";
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
