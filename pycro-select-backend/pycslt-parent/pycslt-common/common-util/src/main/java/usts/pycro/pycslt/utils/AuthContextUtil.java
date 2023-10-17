package usts.pycro.pycslt.utils;

import usts.pycro.pycslt.model.entity.system.SysUser;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-17 15:42
 */
public class AuthContextUtil {

    // 创建一个ThreadLocal对象
    private static final ThreadLocal<SysUser> THREAD_LOCAL = new ThreadLocal<>();

    // 定义存储数据的静态方法
    public static void set(SysUser sysUser) {
        THREAD_LOCAL.set(sysUser);
    }

    // 定义获取数据的方法
    public static SysUser get() {
        return THREAD_LOCAL.get();
    }

    // 删除数据的方法
    public static void remove() {
        THREAD_LOCAL.remove();
    }

}