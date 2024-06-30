package com.pmb.paymybuddy.constent;

public class UserDBconstents {
    public static final String ADD_USER="INSERT INTO user(username,email,password) VALUES(?,?,?)";
    public static final String UPDATE_USER="UPDATE user SET username=?,email=?,password=? WHERE id=?";
    public static final String DELETE_USER="DELETE FROM user WHERE id=?";
    public static final String DELETE_ALL_USERS="DELETE FROM user";
    public static final String FIND_USER_BY_ID="SELECT * FROM user WHERE id=?";
    public static final String FIND_USER_CINNECTIONS="SELECT * FROM connection WHERE userid1=? OR userid2=?";
    public static final String FIND_USER_BY_USERNAME="SELECT * FROM user WHERE username=?";
    public static final String FIND_USER_BY_EMAIL="SELECT * FROM user WHERE email=?";
    public static final String FIND_ALL_USER="SELECT * FROM user";
    public static final String RESET_AUTO_INCREMENT_USER="ALTER TABLE user AUTO_INCREMENT = 1";

    public static final String ADD_CONNECTION="INSERT INTO connection(userid1,userid2) VALUES(?,?)";
    public static final String DELETE_ALL_CONNECTIONS="DELETE FROM connection";
    public static final String FIND_CONNECTION="SELECT * FROM connection WHERE userid1=? OR userid2=?";
    public static final String RESET_AUTO_INCREMENT_CONNECTION="ALTER TABLE connection AUTO_INCREMENT = 1";
}
