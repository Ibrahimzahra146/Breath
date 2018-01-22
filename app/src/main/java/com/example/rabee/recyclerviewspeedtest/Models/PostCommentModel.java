package com.example.rabee.recyclerviewspeedtest.Models;

import java.util.List;

/**
 * Created by Rabee on 1/20/2018.
 */

public class PostCommentModel {
    PostResponseModel post;
    List<CommentModel> comments;
    ReactListModel reacts;

    public PostResponseModel getPost() {
        return post;
    }
    public void setPost(PostResponseModel post) {
        this.post = post;
    }
    public List<CommentModel> getComments() {
        return comments;
    }
    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }
    public ReactListModel getReacts() {
        return reacts;
    }
    public void setReacts(ReactListModel reacts) {
        this.reacts = reacts;
    }

}