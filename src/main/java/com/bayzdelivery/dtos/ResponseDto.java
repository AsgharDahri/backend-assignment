package com.bayzdelivery.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {

    private String status;      // e.g., "Success" or "Error"
    private int statusCode;     // e.g., 200, 201, 400, 500
    private String message;     // e.g., "Person saved successfully"
    private T data;             // The actual payload (Generic)

    // Quick helper for successful responses
    public static <T> ResponseDto<T> success(String message, T data) {
        return ResponseDto.<T>builder()
                .status("Success")
                .statusCode(200)
                .message(message)
                .data(data)
                .build();
    }

    // Quick helper for error responses
    public static <T> ResponseDto<T> error(int code, String message) {
        return ResponseDto.<T>builder()
                .status("Error")
                .statusCode(code)
                .message(message)
                .data(null)
                .build();
    }
}