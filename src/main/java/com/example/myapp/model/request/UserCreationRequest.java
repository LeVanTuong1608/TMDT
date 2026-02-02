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
public class UserCreationRequest {

    // @NotBlank(message = "Họ tên trống")
    String fullName;

    // @NotBlank(message = "Email trống")
    // @Email(message = "Email không đúng định dạng")
    String email;

    // @NotBlank(message = "Mật khẩu trống")
    // @Size(min = 6, max = 20, message = "Mật khẩu phải chứa từ 6-20 ký tự")
    String password;

    // @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "Số điện thoại
    // không hợp lệ!")
    String phone;

    String address;

    // getter & setter
}
