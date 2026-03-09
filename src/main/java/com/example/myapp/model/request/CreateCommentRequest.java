package com.example.myapp.model.request;

public class CreateCommentRequest {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CreateCommentRequest(String content) {
        this.content = content;
    }

}
