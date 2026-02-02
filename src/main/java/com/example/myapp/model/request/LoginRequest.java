package com.example.myapp.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class LoginRequest {

    // @NotBlank(message = "Email trống")
    // @Email(message = "Email không đúng định dạng")
    String email;
    // @NotBlank(message = "Mật khẩu trống")
    // @Size(min = 6, max = 20, message = "Mật khẩu phải chứa từ 6-20 ký tự")
    String password;

    // getter & setter
}
