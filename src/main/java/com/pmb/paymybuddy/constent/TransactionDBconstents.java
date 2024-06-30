package com.pmb.paymybuddy.constent;

public class TransactionDBconstents {
    public static final String ADD_TRANSACTION="INSERT INTO transaction(sender,receiver,amount,description) VALUES(?,?,?,?)";
    public static final String GET_ALL_TRANSACTION="SELECT * FROM transaction";
    public static final String GET_TRANSACTION="SELECT * FROM transaction WHERE id=?";
    public static final String DELETE_TRANSACTION="DELETE FROM transaction WHERE id=?";
    public static final String UPDATE_TRANSACTION="UPDATE transaction SET sender=?,receiver=?,amount=?,description=? WHERE id=?";
    public static final String DELETE_ALL_TRANSACTION="DELETE FROM transaction";
    public static final String RESET_AUTO_INCREMENT="ALTER TABLE transaction AUTO_INCREMENT = 1";
}
