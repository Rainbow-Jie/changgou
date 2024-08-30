package com.changgou.user.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ Author: 聂振杰
 * @ Date: 2024/08/26/21:45
 * @ Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private Integer id;
    private String loginName;
    private String password;
    private String status;
}
