package com.hashtagco.bussinesclient.Common;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.OpenableColumns;

import com.hashtagco.bussinesclient.Model.Admin;
import com.hashtagco.bussinesclient.Model.Requests;
import com.hashtagco.bussinesclient.Model.SubFoods;
import com.hashtagco.bussinesclient.Model.Token;
import com.hashtagco.bussinesclient.Model.User;
import com.hashtagco.bussinesclient.Remote.ApiService;
import com.hashtagco.bussinesclient.Remote.GoogleRetrofitCint;
import com.hashtagco.bussinesclient.Remote.IGoogleServicecs;
import com.hashtagco.bussinesclient.Remote.RetrofitClint;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Random;

public class Common {
    public static User currentUser;
    public static Requests currentRequests;
    public static SubFoods subfooood;
    //-----------------Tables--------------------------------//
    public static final String DRIVER_LOCATION ="DriverLocation" ;
    public static final String FEEDBACK_SERVICE="FeedbackService";
    public static final String SUCESSfFUL_REQUEST_To_CLIENT="SuccessfulRequestToClient";
    public static final String RATING="Rating";
    public static final String FOODS="Foods";

    public static final String USER="User";
    public static final String MENU_IMAGE="MenuImage";



    public static final String DELETE="Delete";
    public static final String USER_KEY="User";
    public static final String PWD_KEY="Password";

    public static final String PHONE_TEXT="userPhone";

    public static final String FOOD_ID="foodId";

    public static final String toopicName="news";

    //This link provides a reference for the HTTP syntax used to pass messages from your app server to client apps via Firebase Cloud Messaging.
    public static final String BASE_URL="https://fcm.googleapis.com";
    public static final String BANNER="Banner";
    //Api your Location
    public static final String Googel_Api_URL="https://maps.googleapis.com";
    public static Admin currentAdmin;
    public static Token currentoken;


    //  استخدام كائن الـ retrofit وربطه بالـ API الخاص بنا
    public static ApiService getFCMClinet()
    {
        return   RetrofitClint.getClint(BASE_URL).create(ApiService.class);
    }

    //  استخدام كائن الـ retrofit وربطه بالـ API الخاص بنا
    public static IGoogleServicecs getGoogleMapsApi()
    {
        return GoogleRetrofitCint.getGoogleApiClint(Googel_Api_URL).create(IGoogleServicecs.class);
    }




    public static String converCodeToStatus(String Status)
    {
        String status;

        switch (Status)
        {

            case "0":
                status = "Placed";
                break;

            case "1":
                status="On my Way";
                break;

            case "2":
                status="shipping";
                break;

            default:
                status="no statue";
                break;

        }

        return status;
    }



    //Method Check Connected to Internet
    public static boolean IsConnectedToInternet(Context context)
    {


        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager !=null)
        {

            NetworkInfo[] networkInfos=connectivityManager.getAllNetworkInfo();


            if (networkInfos!=null)
            {

                for (int i=0;i<networkInfos.length;i++)
                {
                    if (networkInfos[i].getState()==NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }

            }

        }

        return false;
    }

    //This Function will convert Currency to number base on Location
    public static BigDecimal formatCurrency(String amount, Locale locale) throws ParseException
    {
        NumberFormat format=NumberFormat.getCurrencyInstance(locale);

        if (format instanceof DecimalFormat)
            ((DecimalFormat)format).setParseBigDecimal(true);

        return (BigDecimal)format.parse(amount.replace("[^\\d.,]",""));
    }

public static String generateChatRoomId(String a , String b){
if (a.compareTo(b)>0)
    return new StringBuilder(a).append(b).toString();
else if (a.compareTo(b)<0)   return new StringBuilder(b).append(a).toString();
else return new StringBuilder("chat_error").append(new Random().nextInt()).toString();
}

    public static String getFileName(ContentResolver contentResolver, Uri fileUri) {
        String result = null;
        if (fileUri.getScheme().equals("content")){
            Cursor cursor = contentResolver.query(fileUri,null,null,null,null);
            try {
                if (cursor != null &&  cursor.moveToFirst() )
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

            }finally {
               cursor.close();
            }
        }
        if (result == null){
            result = fileUri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1)
                result = result.substring(cut+1);
        }
        return result;
    }
}
