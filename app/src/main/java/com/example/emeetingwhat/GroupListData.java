package com.example.emeetingwhat;

public class GroupListData {
    private String title;
    private String accountOwner;
    private String groupThumbnailPath;

    public String getTitle() {
        return title;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public String getGroupThumbnailPath() {
        return groupThumbnailPath;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public void setGroupThumbnailPath(String groupThumbnailPath) {
        this.groupThumbnailPath = groupThumbnailPath;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
