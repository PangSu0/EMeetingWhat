package com.example.emeetingwhat;

import com.kakao.friends.response.model.FriendInfo;

import java.util.ArrayList;
import java.util.List;

public class MyFriendsInfo {
    private long userId;
    private String nickName;
    private String thumbnailImagePath;
    private String profileImagePath;
    public List<MyFriendsInfo> getFriendInfoList() {
        return friendInfoList;
    }
    private final List<MyFriendsInfo> friendInfoList = new ArrayList<>();
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getThumbnailImagePath() {
        return thumbnailImagePath;
    }

    public void setThumbnailImagePath(String thumbnailImagePath) {
        this.thumbnailImagePath = thumbnailImagePath;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
}
