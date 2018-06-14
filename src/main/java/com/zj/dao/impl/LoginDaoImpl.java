package com.zj.dao.impl;

import com.zj.dao.LoginDao;
import com.zj.mybitatsutil.Dao;

/**
 * Created by dajie on 18-4-19.
 */
public class LoginDaoImpl extends Dao implements LoginDao {

    public LoginDaoImpl(){
        super("zj","dev");
    }
}
