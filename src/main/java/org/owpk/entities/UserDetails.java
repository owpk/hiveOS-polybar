package org.owpk.entities;

import lombok.Data;

import java.util.List;

@Data
public class UserDetails {
    private User user;
    private Integer farm_id;
    private List<String> walletNames;
}
