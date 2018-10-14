package sewacart.com.sewacart.finalpackage.rest.services;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import sewacart.com.sewacart.finalpackage.model.UserModel;

public interface UserInterface {
    @FormUrlEncoded
    @POST("wp-admin/admin-ajax.php")
    Call<UserModel> signInUser(@Field("action") String action, @Field("username") String username , @Field("user_password") String user_password );

    @FormUrlEncoded
    @POST("wp-admin/admin-ajax.php")
    Call<UserModel> registerUser(@Field("action") String action, @Field("username") String username  , @Field("email_address") String email_address, @Field("confirm_pass") String confirm_pass );

    @GET("wp-json/api/userprofile")
    Call<UserModel> getUserDetailsById(@QueryMap Map<String, String> params);
}
