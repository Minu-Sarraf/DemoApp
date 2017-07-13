package com.example.minu.demoapp.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * Created by minu on 7/10/2017.
 */
@IgnoreExtraProperties

public class FeedDataModel {
    public FeedDataModel() {

    }
    private List<FeedsBean> feeds;

    public List<FeedsBean> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<FeedsBean> feeds) {
        this.feeds = feeds;
    }

    public static class FeedsBean {
        /**
         * feedId : 1
         * userId : Harry1
         * userName : Harry Porter
         * status : But you know, happiness can be found even in the darkest of times, if one only remembers to turn on the light
         * userImage : http://myartmagazine.com/file/images/11-2014/harry-porter-colored-pencils-heather.preview.jpg
         * feedImage : http://www.hdwallpapers.in/walls/harry_potter_deathly_hallows_part_ii-wide.jpg
         */

        private int feedId;
        private String userId;
        private String userName;
        private String status;
        private String userImage;
        private String feedImage;

        public int getFeedId() {
            return feedId;
        }

        public void setFeedId(int feedId) {
            this.feedId = feedId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getFeedImage() {
            return feedImage;
        }

        public void setFeedImage(String feedImage) {
            this.feedImage = feedImage;
        }
    }
}
