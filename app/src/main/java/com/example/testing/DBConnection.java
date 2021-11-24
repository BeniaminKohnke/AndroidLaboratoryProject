package com.example.testing;

public class DBConnection {
    //temporary database adapter
    private static String userNameFromDB = "PAPAJ";
    private static String passwordFromDB = "2137";

    /** Returns message code based on action
     * 0 - if account was not created
     * 1 - if account was created
     * */
    public static byte createAccount(String userName, String password){
        userNameFromDB = userName;
        passwordFromDB = password;

        //Check if account already exists and add if not
        return 0;
    }

    /** Returns message code based on action
     * 0 - if exist
     * 1 - if not exist
     * */
    public static byte checkUserName(String userName){
        return 1;
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
