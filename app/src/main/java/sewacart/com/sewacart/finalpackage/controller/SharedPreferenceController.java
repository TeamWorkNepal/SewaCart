package sewacart.com.sewacart.finalpackage.controller;

import android.content.Context;
import android.content.SharedPreferences;

import sewacart.com.sewacart.finalpackage.model.UserModel;

public class SharedPreferenceController {
    static SharedPreferences prefs;


    public static void saveLoginLog(Context context, boolean value) {
        prefs = context.getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLoggedId", value);
        editor.apply();
    }

    public static boolean getLoginLog(Context context) {
        prefs = context.getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        return prefs.getBoolean("isLoggedId", false);
    }

    public static void saveUserDetails(Context context, UserModel.UserDetails data) {

        prefs = context.getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("id", data.getId());
        editor.putString("name",data.getName());
        editor.putString("email", data.getEmail());
        editor.putString("role", data.getRole());
        editor.putString("phone", data.getPhone());
        editor.putString("mobile", data.getMobile());
        editor.putString("state", data.getState());
        editor.putString("city", data.getCity());
        editor.putString("street", data.getStreet());
        editor.putString("extra_address", data.getExtraAddress());
        editor.putString("description", data.getDescription());
        editor.putString("user_photo", data.getUserPhoto());
        editor.putString("zip", data.getZip());

        editor.putBoolean("isLoggedIn", true);

        editor.apply();

    }

    public static void saveUserToken(Context context, String token) {
        prefs = context.getSharedPreferences("userToken", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token", token);
        editor.putBoolean("isSavedToken", true);
        editor.apply();
    }

    public static boolean isTokenSaved(Context context) {
        prefs = context.getSharedPreferences("userToken", Context.MODE_PRIVATE);
        return prefs.getBoolean("isSavedToken", false);
    }

    public static String getToken(Context context) {
        prefs = context.getSharedPreferences("userToken", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        return token;

    }

    public static UserModel.UserDetails getUserDetails(Context context) {
        prefs = context.getSharedPreferences("userdetails", Context.MODE_PRIVATE);

        String id = prefs.getString("id", "");
        String name = prefs.getString("name", "");
        String email = prefs.getString("email", "");
        String role = prefs.getString("role", "");
        String phone = prefs.getString("phone", "");
        String mobile = prefs.getString("mobile", "");
        String state = prefs.getString("state", "");
        String city = prefs.getString("city", "");
        String street = prefs.getString("street", "");
        String extra_address = prefs.getString("extra_address", "");
        String description = prefs.getString("description", "");
        String user_photo = prefs.getString("user_photo", "");
        String zip = prefs.getString("zip", "");



        return new UserModel.UserDetails(id, name, email, role, phone,mobile,state,city,street,extra_address,description,user_photo,zip);
    }
    public  static void clearSharedPrefernce(Context context){
        prefs = context.getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        prefs.edit().clear().apply();


    }
}
