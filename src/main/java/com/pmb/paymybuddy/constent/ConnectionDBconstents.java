package com.pmb.paymybuddy.constent;

public class ConnectionDBconstents {
    public static final String ADD_CONNECTION="INSERT INTO connection(userid1,userid2) VALUES(?,?)";
    public static final String DELETE_CONNECTION="DELETE FROM connection WHERE id=?";
    public static final String DELETE_ALL_CONNECTIONS="DELETE FROM connection";
    public static final String FIND_CONNECTION="SELECT * FROM connection WHERE userid1=? OR userid2=?";
    public static final String RESET_AUTO_INCREMENT="ALTER TABLE connection AUTO_INCREMENT = 1";
}
