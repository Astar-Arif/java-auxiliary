package com.astar.common.library.pojo;


public class Base {
    int base_n;

    public Base(int n){
        this.base_n = n;
    }

    public static Base Base2(){
        return new Base(2);
    }

    public static Base Base8(){
        return new Base(8);
    }

    public static Base Base10(){
        return new Base(10);
    }

    public static Base Base16(){
        return new Base(16);
    }

    public static Base Base32(){
        return new Base(32);
    }

    public static Base Base64(){
        return new Base(64);
    }
}
