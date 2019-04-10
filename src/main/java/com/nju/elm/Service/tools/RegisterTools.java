package com.nju.elm.Service.tools;

import org.apache.commons.mail.HtmlEmail;

public class RegisterTools {

    public static int getTimeSecond(){
        int p = (int) (System.currentTimeMillis()/1000);
        return p;
    }


    public static String getRandomValidateNum(){
        int p = (int) (System.currentTimeMillis()%1000);
        int q = (int) (Math.random()*1000);
        String num = (1000+p+"").substring(1)+(1000+q+"").substring(1);
        return num;
    }

    public static int calcuTime(int previousTimeSecond){
        int p = (int) (System.currentTimeMillis()/1000);
        return p-previousTimeSecond;
    }


    public static boolean sendVertifyCode(String emailAddress, String VerificationCode){
        String message = "尊敬的用户您好,您本次注册的验证码是:" + VerificationCode +",如非本人操作，请注意注意账号安全。";
        return sendEmailMessage(emailAddress,message);
    }


    public static boolean sendEmailMessage(String emailAddress, String message){
        try {
            HtmlEmail email = new HtmlEmail();//不用更改

            email.setHostName("smtp.163.com");//需要修改，126邮箱为smtp.126.com,163邮箱为163.smtp.com，QQ为smtp.qq.com
            email.setCharset("UTF-8");
            email.setFrom("nju099elm@163.com", "NJU饿了吗？");//此处填邮箱地址和用户名,用户名可以任意填写
            email.setAuthentication("nju099elm@163.com", "nju099elm");//此处填写邮箱地址和客户端授权码
            email.setSubject("NJU饿了吗科技");//此处填写邮件名，邮件名可任意填写

            email.addTo(emailAddress);// 收件地址
            email.setMsg(message);//此处填写邮件内容

            email.send();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }



}

