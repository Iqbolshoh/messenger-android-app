package uz.iqbolshoh.messenger;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.Map;

public interface ApiService {

    // 1. Login
    @POST("auth/login.php")
    Call<BaseResponse> login(@Body Map<String, String> credentials);

    // 2. Logout
    @POST("auth/logout.php")
    Call<BaseResponse> logout(@Query("user_id") int userId);

    // 3. Signup
    @POST("auth/signup.php")
    Call<BaseResponse> signup(@Body Map<String, String> userDetails);

    // 4. Check Availability
    @POST("auth/check_availability.php")
    Call<BaseResponse> checkAvailability(@Query("username") String username);

    // 5. Check Login
    @POST("auth/check_login.php")
    Call<BaseResponse> checkLogin(@Query("user_id") int userId);

    // 6. Change User Status (block/unblock)
    @POST("change_user_status.php")
    Call<BaseResponse> changeUserStatus(@Query("user_id") int userId, @Query("status") String status);

    // 7. Check User Status
    @POST("check_user_status.php")
    Call<BaseResponse> checkUserStatus(@Query("user_id") int userId);

    // 8. Clear Messages
    @POST("clear_messages.php")
    Call<BaseResponse> clearMessages(@Query("user_id") int userId);

    // 9. Delete a Specific Message
    @POST("delete_message.php")
    Call<BaseResponse> deleteMessage(@Query("message_id") int messageId);

    // 10. Edit a Message
    @POST("edit_message.php")
    Call<BaseResponse> editMessage(@Query("message_id") int messageId, @Query("new_text") String newText);

    // 11. Fetch Contacts
    @POST("fetch_contacts.php")
    Call<BaseResponse> fetchContacts(@Query("user_id") int userId);

    // 12. Fetch Messages
    @POST("fetch_messages.php")
    Call<BaseResponse> fetchMessages(@Query("user_id") int userId, @Query("chat_id") int chatId);

    // 13. Fetch Profile
    @POST("fetch_profile.php")
    Call<BaseResponse> fetchProfile(@Query("user_id") int userId);

    // 14. Send a Message
    @POST("send_message.php")
    Call<BaseResponse> sendMessage(@Query("sender_id") int senderId, @Query("receiver_id") int receiverId, @Query("message") String message);
}
