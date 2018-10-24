package sewacart.com.sewacart.finalpackage.rest.services;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
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

    @FormUrlEncoded
    @POST("wp-admin/admin-ajax.php")
    Call<UserModel> updateProfile(@Field("action") String action, @Field("user_id") String user_id , @Field("display_name") String display_name, @Field("email_address") String email_address , @Field("phone") String phone , @Field("mobile") String mobile , @Field("state") String state , @Field("city") String city, @Field("zip") String zip,  @Field("street") String street,@Field("extra_address") String extra_address, @Field("description") String description ,@Field("imageonly") String imageonly );

    @POST("wp-admin/admin-ajax.php")
    Call<UserModel> updateImage(@Body RequestBody file);
}
