package org.lifei.rpc.consumer.impl;

import org.lifei.rpc.consumer.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public String findById() {
        return "user{id=1, username=xiongge}";
    }
}
