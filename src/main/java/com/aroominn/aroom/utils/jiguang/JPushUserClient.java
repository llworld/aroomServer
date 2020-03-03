package com.aroominn.aroom.utils.jiguang;

import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jiguang.common.utils.Preconditions;
import cn.jmessage.api.common.BaseClient;
import cn.jmessage.api.common.JMessageConfig;
import cn.jmessage.api.common.model.NoDisturbPayload;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.common.model.RegisterPayload;
import cn.jmessage.api.common.model.UserPayload;
import cn.jmessage.api.common.model.friend.FriendNote;
import cn.jmessage.api.common.model.friend.FriendNotePayload;
import cn.jmessage.api.common.model.group.GroupShieldPayload;
import cn.jmessage.api.user.*;
import cn.jmessage.api.utils.StringUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JPushUserClient extends BaseClient {

    private String userPath;
    private String adminPath;

    public JPushUserClient(String appkey, String masterSecret) {
        this(appkey, masterSecret, null, JMessageConfig.getInstance());
    }

    public JPushUserClient(String appkey, String masterSecret, HttpProxy proxy, JMessageConfig config) {
        super(appkey, masterSecret, proxy, config);
        userPath = (String) config.get(JMessageConfig.USER_PATH);
        adminPath = (String) config.get(JMessageConfig.ADMIN_PATH);
    }

    public ResponseWrapper registerUsers(RegisterPayload payload)
            throws APIConnectionException, APIRequestException {

        Preconditions.checkArgument(null != payload, "payload should not be null");

        return _httpClient.sendPost(_baseUrl + userPath, payload.toString());
    }

    public ResponseWrapper registerAdmins(RegisterInfo payload)
            throws APIConnectionException, APIRequestException {

        Preconditions.checkArgument(null != payload, "payload should not be null");

        return _httpClient.sendPost(_baseUrl + adminPath, payload.toString());

    }

    public UserInfoResult getUserInfo(String username)
            throws APIConnectionException, APIRequestException {

        StringUtils.checkUsername(username);
        ResponseWrapper response = _httpClient.sendGet(_baseUrl + userPath + "/" + username);
        return UserInfoResult.fromResponse(response, UserInfoResult.class);
    }

    public ResponseWrapper updateUserInfo(String username, UserPayload payload)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        Preconditions.checkArgument(null != payload, "payload should not be null");

        return _httpClient.sendPut(_baseUrl + userPath + "/" + username, payload.toString());
    }

    public UserStateResult getUserState(String username)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        ResponseWrapper response = _httpClient.sendGet(_baseUrl + userPath + "/" + username + "/userstat");
        return UserStateResult.fromResponse(response, UserStateResult.class);
    }

    public UserStateListResult[] getUsersState(String... users) throws APIConnectionException, APIRequestException {
        JsonArray jsonArray = new JsonArray();
        for (String username : users) {
            StringUtils.checkUsername(username);
            jsonArray.add(new JsonPrimitive(username));
        }
        ResponseWrapper response = _httpClient.sendPost(_baseUrl + userPath + "/userstat", jsonArray.toString());
        return _gson.fromJson(response.responseContent, UserStateListResult[].class);
    }

    public ResponseWrapper updatePassword(String username, String password)
            throws APIConnectionException, APIRequestException {

        StringUtils.checkUsername(username);
        StringUtils.checkPassword(password);

        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("new_password", password);

        return _httpClient.sendPut(_baseUrl + userPath + "/" + username + "/password",
                jsonObj.toString());

    }

    public ResponseWrapper deleteUser(String username)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        return _httpClient.sendDelete(_baseUrl + userPath + "/" + username);
    }

    public UserInfoResult[] getBlackList(String username)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        ResponseWrapper response = _httpClient.sendGet(_baseUrl + userPath + "/" + username + "/blacklist");
        return _gson.fromJson(response.responseContent, UserInfoResult[].class);
    }

    /**
     * Add Users to black list
     *
     * @param username The owner of the black list
     * @param users    The users that will add to black list
     * @return add users to black list
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public ResponseWrapper addBlackList(String username, String... users)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        Preconditions.checkArgument(null != users && users.length > 0, "black list should not be empty");

        JsonArray array = new JsonArray();
        for (String user : users) {
            array.add(new JsonPrimitive(user));
        }
        return _httpClient.sendPut(_baseUrl + userPath + "/" + username + "/blacklist", array.toString());
    }

    public ResponseWrapper removeBlackList(String username, String... users)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        Preconditions.checkArgument(null != users && users.length > 0, "black list should not be empty");
        JsonArray array = new JsonArray();
        for (String user : users) {
            array.add(new JsonPrimitive(user));
        }

        return _httpClient.sendDelete(_baseUrl + userPath + "/" + username + "/blacklist", array.toString());
    }

    /**
     * Get user list
     *
     * @param start The start index of the list
     * @param count The number that how many you want to get from list
     * @return User info list
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public UserListResult getUserList(int start, int count)
            throws APIConnectionException, APIRequestException {

        if (start < 0 || count <= 0 || count > 500) {
            throw new IllegalArgumentException("negative index or count must more than 0 and less than 501");
        }
        ResponseWrapper response = _httpClient.sendGet(_baseUrl + userPath + "?start=" + start + "&count=" + count);
        return UserListResult.fromResponse(response, UserListResult.class);

    }

    /**
     * Get admins by appkey
     *
     * @param start The start index of the list
     * @param count The number that how many you want to get from list
     * @return admin user info list
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public UserListResult getAdminListByAppkey(int start, int count)
            throws APIConnectionException, APIRequestException {
        if (start < 0 || count <= 0 || count > 500) {
            throw new IllegalArgumentException("negative index or count must more than 0 and less than 501");
        }
        ResponseWrapper response = _httpClient.sendGet(_baseUrl + adminPath + "?start=" + start + "&count=" + count);
        return UserListResult.fromResponse(response, UserListResult.class);

    }

    /**
     * Set don't disturb service while receiving messages.
     * You can Add or remove single conversation or group conversation
     *
     * @param username Necessary
     * @param payload  NoDisturbPayload
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public ResponseWrapper setNoDisturb(String username, NoDisturbPayload payload)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        Preconditions.checkArgument(null != payload, "payload should not be null");

        return _httpClient.sendPost(_baseUrl + userPath + "/" + username + "/nodisturb", payload.toString());
    }

    /**
     * Get all groups of a user
     *
     * @param username Necessary
     * @return Group info list
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public UserGroupsResult getGroupList(String username)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        ResponseWrapper response = _httpClient.sendGet(_baseUrl + userPath + "/" + username + "/groups");

        return UserGroupsResult.fromResponse(response);
    }

    /**
     * Add friends to username
     *
     * @param username Necessary
     * @param users    username to be add
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public ResponseWrapper addFriends(String username, String... users)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        Preconditions.checkArgument(null != users && users.length > 0, "friend list should not be empty");

        JsonArray array = new JsonArray();
        for (String user : users) {
            array.add(new JsonPrimitive(user));
        }

        return _httpClient.sendPost(_baseUrl + userPath + "/" + username + "/friends", array.toString());
    }

    /**
     * Delete friends
     *
     * @param username Friends you want to delete to
     * @param users    username to be delete
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public ResponseWrapper deleteFriends(String username, String... users)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        Preconditions.checkArgument(null != users && users.length > 0, "friend list should not be empty");

        JsonArray array = new JsonArray();
        for (String user : users) {
            array.add(new JsonPrimitive(user));
        }
        return _httpClient.sendDelete(_baseUrl + userPath + "/" + username + "/friends", array.toString());
    }

    /**
     * Update friends' note information. The size is limit to 500.
     *
     * @param username Necessary
     * @param array    FriendNote array
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public ResponseWrapper updateFriendsNote(String username, FriendNote[] array)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        FriendNotePayload payload = new FriendNotePayload.Builder()
                .setFriendNotes(array)
                .build();
        Preconditions.checkArgument(null != payload, "FriendNotePayload should not be null");
        return _httpClient.sendPut(_baseUrl + userPath + "/" + username + "/friends", payload.toString());
    }

    /**
     * Get friends'info from user
     *
     * @param username Necessary
     * @return UserList
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public UserInfoResult[] getFriendsInfo(String username)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        ResponseWrapper response = _httpClient.sendGet(_baseUrl + userPath + "/" + username + "/friends");
        return _gson.fromJson(response.responseContent, UserInfoResult[].class);
    }

    /**
     * Set user's group message blocking
     *
     * @param payload  GroupShieldPayload
     * @param username Necessary
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public ResponseWrapper setGroupShield(GroupShieldPayload payload, String username)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(null != payload, "GroupShieldPayload should not be null");
        StringUtils.checkUsername(username);
        return _httpClient.sendPost(_baseUrl + userPath + "/" + username + "/groupsShield", payload.toString());
    }

    /**
     * Forbid or activate user
     *
     * @param username username
     * @param disable  true means forbid, false means activate
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public ResponseWrapper forbidUser(String username, boolean disable)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        return _httpClient.sendPut(_baseUrl + userPath + "/" + username + "/forbidden?disable=" + disable, null);
    }


}
