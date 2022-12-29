package com.binar.chapter7.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersRequest {

    @NonNull
    private Integer id;

    @NonNull
    @Schema(example = "jokowiajah")
    private String username;

    @NonNull
    @Schema(example = "jokowi@gmail.com")
    private String email;

    @NonNull
    @Schema(example = "jokowi123")
    private String password;

}
