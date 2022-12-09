/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peswa.leavemanager.util;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

public class AppUtil {


    public static String generateActivationCode(){
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(999999);
        String formatted = String.format("%06d", num);
        return formatted;
    }

    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789QWERTYUIOPASDFGHJKLZXCVBNM";

    public static String generateRandomString(int length) {
        StringBuffer buffer = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            buffer.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(buffer);
    }

    public static long getDateDifferenceInMins(Date createdDate){
        Date currentDate = new Date();
        long difference_In_Time = currentDate.getTime() - createdDate.getTime();
        long difference_In_Hours = (difference_In_Time/(1000 * 60 * 60)) % 24;
        long difference_In_Minutes = (difference_In_Time/(1000 * 60))% 60;

        long totalMins = (60 * difference_In_Hours) + difference_In_Minutes;
        return totalMins;
    }

}
