package com.astar.java.library.pojo;


public enum Base {
    TWO(2),
    FOUR(4),
    EIGHT(8),
    TEN(10),
    SIXTEEN(16),
    THIRTY_TWO(32),
    SIXTY_FOUR(64);
    int num;


    Base(int num) {
        this.num = num;
    }
}
