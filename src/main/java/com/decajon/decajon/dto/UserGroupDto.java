package com.decajon.decajon.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserGroupDto
{
    private Long userId;
    private Long groupId;
    private String role;
}
