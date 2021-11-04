package com.example.testing;

public class DBConnection {
    //temporary database adapter
    private static String userNameFromDB = "";
    private static String passwordFromDB = "";

    /** Returns message code based on action*/
    public static byte addUser(String userName, String password){
        userNameFromDB = userName;
        passwordFromDB = password;

        //Check if account already exists and add if not
        return 0;
    }

    /** Returns message code based on action
     * 0 - does not exist
     * 1 - is equal
     * 2 - is not equal*/
    public static byte checkLoggingData(String userName, String password){
        if(!userNameFromDB.isEmpty() && !passwordFromDB.isEmpty()){
            return (byte)(userName.equals(userNameFromDB) && password.equals(passwordFromDB) ? 1 : 2);
        }

        //Check if input is equal to database data
        return 0;
    }
}
