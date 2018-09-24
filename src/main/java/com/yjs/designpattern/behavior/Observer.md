
观察者模式
=======

定义了对象之间的一对多的依赖，这样一来，当一个对象改变时，它的所有的依赖者都会收到通知并自动更新。

先不提定义,我们先思考一个实例,当博客更新文章时,有哪些地方需要同步更新?

1. 个人文章统计总数+1
2. 个人积分数+n
3. 关注者消息推送+1
4. 分类推送添加...

如果这些更新需要以插件形式加载或移除,就可以利用观察者模式.也就是,后期再增加相关功能模块,不需要太多工作就可以加载同步;缺点也在这里,因为各个插件模块分散,后面的功能方法可能重叠之前的功能,导致数据错误,所以在使用时尽量多做一步注释或者整理好文档.
【观察者模式适用场景】
当一个抽象模型有两个方面，其中一个方面依赖于另一个方面。
当对一个对象的改变需要同时改变其它对象，而不知道具体有多少个对象待改变。
当一个对象必须通知其它对象，而它又不能假定其它对象是谁。换句话说，你不希望这些对象是紧密耦合的。

【观察者模式中主要角色--2个接口,2个类】
1.抽象主题（Subject）角色(接口)：主题角色将所有对观察者对象的引用保存在一个集合中，每个主题可以有任意多个观察者。 抽象主题提供了增加和删除观察者对象的接口。
2.抽象观察者（Observer）角色(接口)：为所有的具体观察者定义一个接口，在观察的主题发生改变时更新自己。
3.具体主题（ConcreteSubject）角色(1个)：存储相关状态到具体观察者对象，当具体主题的内部状态改变时，给所有登记过的观察者发出通知。具体主题角色通常用一个具体子类实现。
4.具体观察者（ConcretedObserver）角色(多个)：存储一个具体主题对象，存储相关状态，实现抽象观察者角色所要求的更新接口，以使得其自身状态和主题的状态保持一致。


【观察者模式中主要角色关系】
抽象主题（Subject）(接口)------>被具体主题（ConcreteSubject）角色(1个)实现
                                                                                               ↑
                                                                                                 | (观察者对象载入主题方法,并在主题
                                                                                                 |   方法中调用观察者实现的接口方法)
抽象观察者（Observer）(接口)---->被具体观察者（ConcretedObserver）角色(N个)实现


---------------------

1、初步认识
观察者模式的定义：
　　在对象之间定义了一对多的依赖，这样一来，当一个对象改变状态，依赖它的对象会收到通知并自动更新。

大白话：
　　其实就是发布订阅模式，发布者发布信息，订阅者获取信息，订阅了就能收到信息，没订阅就收不到信息。

2、这个模式的结构图

3、可以看到，该模式包含四个角色
抽象被观察者角色：也就是一个抽象主题，它把所有对观察者对象的引用保存在一个集合中，每个主题都可以有任意数量的观察者。抽象主题提供一个接口，可以增加和删除观察者角色。一般用一个抽象类和接口来实现。
抽象观察者角色：为所有的具体观察者定义一个接口，在得到主题通知时更新自己。
具体被观察者角色：也就是一个具体的主题，在集体主题的内部状态改变时，所有登记过的观察者发出通知。
具体观察者角色：实现抽象观察者角色所需要的更新接口，一边使本身的状态与制图的状态相协调。
4、使用场景例子
　　有一个微信公众号服务，不定时发布一些消息，关注公众号就可以收到推送消息，取消关注就收不到推送消息。

5、观察者模式具体实现
1、定义一个抽象被观察者接口

复制代码
package com.jstao.observer;

/***
 * 抽象被观察者接口
 * 声明了添加、删除、通知观察者方法
 * @author jstao
 *
 */
public interface Observerable {
    
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObserver();
    
}
复制代码
 

 2、定义一个抽象观察者接口

复制代码
package com.jstao.observer;

/***
 * 抽象观察者
 * 定义了一个update()方法，当被观察者调用notifyObservers()方法时，观察者的update()方法会被回调。
 * @author jstao
 *
 */
public interface Observer {
    public void update(String message);
}
复制代码
3、定义被观察者，实现了Observerable接口，对Observerable接口的三个方法进行了具体实现，同时有一个List集合，用以保存注册的观察者，等需要通知观察者时，遍历该集合即可。

复制代码
package com.jstao.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者，也就是微信公众号服务
 * 实现了Observerable接口，对Observerable接口的三个方法进行了具体实现
 * @author jstao
 *
 */
public class WechatServer implements Observerable {
    
    //注意到这个List集合的泛型参数为Observer接口，设计原则：面向接口编程而不是面向实现编程
    private List<Observer> list;
    private String message;
    
    public WechatServer() {
        list = new ArrayList<Observer>();
    }
    
    @Override
    public void registerObserver(Observer o) {
        
        list.add(o);
    }
    
    @Override
    public void removeObserver(Observer o) {
        if(!list.isEmpty())
            list.remove(o);
    }

    //遍历
    @Override
    public void notifyObserver() {
        for(int i = 0; i < list.size(); i++) {
            Observer oserver = list.get(i);
            oserver.update(message);
        }
    }
    
    public void setInfomation(String s) {
        this.message = s;
        System.out.println("微信服务更新消息： " + s);
        //消息更新，通知所有观察者
        notifyObserver();
    }

}
复制代码
4、定义具体观察者，微信公众号的具体观察者为用户User

复制代码
package com.jstao.observer;

/**
 * 观察者
 * 实现了update方法
 * @author jstao
 *
 */
public class User implements Observer {

    private String name;
    private String message;
    
    public User(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String message) {
        this.message = message;
        read();
    }
    
    public void read() {
        System.out.println(name + " 收到推送消息： " + message);
    }
    
}
复制代码
5、编写一个测试类

首先注册了三个用户，ZhangSan、LiSi、WangWu。公众号发布了一条消息"PHP是世界上最好用的语言！"，三个用户都收到了消息。

用户ZhangSan看到消息后颇为震惊，果断取消订阅，这时公众号又推送了一条消息，此时用户ZhangSan已经收不到消息，其他用户

还是正常能收到推送消息。

复制代码
package com.jstao.observer;

public class Test {
    
    public static void main(String[] args) {
        WechatServer server = new WechatServer();
        
        Observer userZhang = new User("ZhangSan");
        Observer userLi = new User("LiSi");
        Observer userWang = new User("WangWu");
        
        server.registerObserver(userZhang);
        server.registerObserver(userLi);
        server.registerObserver(userWang);
        server.setInfomation("PHP是世界上最好用的语言！");
        
        System.out.println("----------------------------------------------");
        server.removeObserver(userZhang);
        server.setInfomation("JAVA是世界上最好用的语言！");
        
    }
}
复制代码
