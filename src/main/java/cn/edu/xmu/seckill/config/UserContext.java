package cn.edu.xmu.seckill.config;

import cn.edu.xmu.seckill.pojo.User;

public class UserContext {
    private static ThreadLocal<User> userHolder = new ThreadLocal<User>();

    public static void setUser(User user) {
        userHolder.set(user);
    }

    public static User getUser() {
        return userHolder.get();
    }
}
