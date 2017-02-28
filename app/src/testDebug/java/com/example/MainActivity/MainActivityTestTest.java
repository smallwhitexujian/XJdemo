package com.example.MainActivity;

import org.testng.annotations.Test;

/**
 * 　　┏┓　　　　┏┓
 * 　┏┛┻━━━━┛┻┓
 * 　┃　　　　　　　　┃
 * 　┃　　　━　　　　┃
 * 　┃　┳┛　┗┳　　┃
 * 　┃　　　　　　　　┃
 * 　┃　　　┻　　　　┃
 * 　┃　　　　　　　　┃
 * 　┗━━┓　　　┏━┛
 * 　　　　┃　　　┃　　　神兽保佑
 * 　　　　┃　　　┃　　　代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * <p>
 * <p>
 * 作者: Created by: xujian on Date: 16/7/20.
 * 邮箱: xj626361950@163.com
 * com.example.MainActivity
 */
public class MainActivityTestTest {
    @Test
    public void onCreate() throws Exception {
        fun(3, "A", "B", "C");
    }

    @org.junit.Test
    public void fun() throws Exception {
        fun(3, "A", "B", "C");
    }

    void fun(int n, String a, String b, String c) {
        System.out.println(n + "," + a + "," + b + "," + c);   //(3,a,b,c)
        if (n == 1) {
            System.out.println(a + "->" + c);                   
        } else {    
            fun(n - 1, a, c, b);                               //(2,a,c,b)
            System.out.println(a + "->" + c);                  // a - > b
            fun(n - 1, b, a, c);                               //(2,)
        }
    }
}