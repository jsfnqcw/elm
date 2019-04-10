package com.nju.elm.Dao.Model.MangoDB;

import com.nju.elm.Service.tools.RegisterTools;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class RegisterQueue implements Serializable {

    String email;
    String validateNumber;
    int createTime;

    public RegisterQueue(String email, String validateNumber) {
        this.email = email;
        this.validateNumber = validateNumber;
        this.createTime = RegisterTools.getTimeSecond();
    }

    public String getValidateNumber() {
        return validateNumber;
    }

    public int getCreateTime() {
        return createTime;
    }
}
