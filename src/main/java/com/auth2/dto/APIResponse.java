package com.auth2.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class APIResponse<T> {
    private String message;
    private int status;
    private T data;
}
