package com.decajon.decajon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserGroupDto
{
    private Long userId;
    private Long groupId;
    private String role;
}
