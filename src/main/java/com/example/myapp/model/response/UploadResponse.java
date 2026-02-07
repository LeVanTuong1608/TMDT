package com.example.myapp.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UploadResponse {
    private String url;

    public UploadResponse(String url) {
        this.url = url;
    }
}
