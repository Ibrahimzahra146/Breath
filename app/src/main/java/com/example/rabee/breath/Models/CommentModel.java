package com.example.rabee.breath.Models;

import java.util.Date;
import java.util.List;

/**
 * Created by Rabee on 1/20/2018.
 */

public class CommentModel {
    int id;
    String text;
    private Date timestamp;
    List<ReplyModel> replies;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<ReplyModel> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyModel> replies) {
        this.replies = replies;
    }
}
