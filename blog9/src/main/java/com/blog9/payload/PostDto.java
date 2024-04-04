package com.blog9.payload;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class PostDto {
    private long id;
    @NotEmpty
    @Min(value = 2, message = "post should have min 2")
    private String title;
    @NotEmpty
    @Min(value = 2, message = "post should have min 2")
    private String description;
    @NotEmpty
    @Min(value = 2, message = "post should have min 2")
    private String content;
}
