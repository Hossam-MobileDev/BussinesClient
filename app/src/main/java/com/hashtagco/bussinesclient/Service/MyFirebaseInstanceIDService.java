package com.hashtagco.bussinesclient.Service;


//import com.google.firebase.iid.FirebaseInstanceId;


public class MyFirebaseInstanceIDService{ /*extends FirebaseInstanceIdService
{
    @Override
    public void onTokenRefresh()
    {
        super.onTokenRefresh();

        String token= FirebaseInstanceId.getInstance().getToken();

        if (Common.currentUser !=null)
            updateTokenServer(token);





    }
    private void updateTokenServer(String token)
    {

        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference referenceToken=db.getReference("Tokens");


        Token token1=new Token(token,true); //parser isServerToken True because Customers

        referenceToken.child(Common.currentUser.getPhone()).setValue(token1);

    }


    private void updateTokenFirebase(String tokenRefershed)
    {

        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference Tokens=db.getReference("Tokens");


        Token token=new Token(tokenRefershed,false); //parser isServerToken false because Clint

         Tokens.child(Common.currentUser.getPhone()).setValue(token);

    }
*/




}
