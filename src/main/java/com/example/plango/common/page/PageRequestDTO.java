package com.example.plango.common.page;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDTO {
    @Builder.Default
    private int size = 10;
    private String cursor;
    private String keyword;
}
