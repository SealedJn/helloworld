package com.DesignPattern.FlyWeightPattern;

/**
 * 共享对象的状态分为内部状态与外部状态，内部状态在各个对象间共享，而外部状态由客户端传入，这一点一定要牢记
 *
 * 最近王二狗的儿子爱上了玩五子棋，就让二狗给他做个五子棋的的手机游戏。你说这要求狗子能拒绝吗？说能的都是单身狗 o(￣︶￣)o。
 *
 * 二狗开始设计五子棋的架构，在分析到棋子那一块的时候，他想到了享元模式。他是这么分析的：
 * 这里需要大量的棋子对象，它们除了颜色有黑白之分，摆放的位置不同其他都一样，非常适合使用享元模式。
 */
public interface Chess {
    //绘制棋子
    void draw(int x,int y);
}

