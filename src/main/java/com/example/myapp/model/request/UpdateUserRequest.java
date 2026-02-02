package com.example.myapp.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {

    // @Pattern(regexp = "(09|03|07|08|05)+([0-9]{8})\\b", message = "Điện thoại
    // không hợp lệ")
    String phone;

    // String

    // @NotBlank(message = "Họ tên trống")
    // @JsonProperty("full_name")
    String fullName;

    String address;

    String imageUrl;
    String oldPassword;
    String newPassword;
    // getter & setter
}
