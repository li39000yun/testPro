package com.lyq.mode.strategy.feed;

/**
 * 策略客户端测试类<br/>
 * 策略模式将行为和环境隔离开来，环境角色类负责维持和查询行为的类，<br/>
 * 各种类似的逻辑算法都在各个具体的策略类中进行实现，由于环境和行为隔离开来，<br/>
 * 所以行为的逻辑变更不会影响到环境和客户端<br/>
 * 策略模式涉及到三个角色
 * Created by lyq on 2016/12/2.
 */
public class StrategyDemo {
    public static void main(String[] args) {
        Feeder feeder = new Feeder(new PigFeed());
        // 喂猪
        feeder.feedInterface();
        feeder.setFowlFeed(new RabbitFeed());
        // 喂兔子
        feeder.feedInterface();

    }
}
