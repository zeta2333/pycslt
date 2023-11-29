package usts.pycro.pycslt.utils;

import usts.pycro.pycslt.model.entity.system.SysUser;
import usts.pycro.pycslt.model.entity.user.UserInfo;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-17 15:42
 */
public class AuthContextUtil {

    // 创建一个ThreadLocal对象存储sysUser
    private static final ThreadLocal<SysUser> SYS_USER_THREAD_LOCAL = new ThreadLocal<>();

    // 创建一个ThreadLocal对象存储userInfo
    private static final ThreadLocal<UserInfo> USER_INFO_THREAD_LOCAL = new ThreadLocal<>();

    public static UserInfo getUserInfo() {
        return USER_INFO_THREAD_LOCAL.get();
    }

    public static void setUserInfo(UserInfo userInfo) {
        USER_INFO_THREAD_LOCAL.set(userInfo);
    }

    public static void removeUserInfo() {
        USER_INFO_THREAD_LOCAL.remove();
    }

    // 定义获取数据的方法
    public static SysUser getSysUser() {
        return SYS_USER_THREAD_LOCAL.get();
    }

    // 定义存储数据的静态方法
    public static void setSysUser(SysUser sysUser) {
        SYS_USER_THREAD_LOCAL.set(sysUser);
    }

    // 删除数据的方法
    public static void removeSysUser() {
        SYS_USER_THREAD_LOCAL.remove();
    }

}