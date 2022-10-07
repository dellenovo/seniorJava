package org.lifei.rpc;

import org.lifei.rpc.consumer.SkuService;
import org.lifei.rpc.consumer.UserService;
import org.lifei.rpc.consumerstub.HeroRPCProxy;

public class TestHeroRPC {
    public static void main(String[] args) {
        SkuService skuService = (SkuService) HeroRPCProxy.create(SkuService.class);
        System.out.println(skuService.findByName("uid"));

        UserService userService = (UserService) HeroRPCProxy.create(UserService.class);
        System.out.println(userService.findById());
    }
}
