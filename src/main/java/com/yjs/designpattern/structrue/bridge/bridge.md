 模式定义
      桥接模式即将抽象部分与它的实现部分分离开来，使他们都可以独立变化。

      桥接模式将继承关系转化成关联关系，它降低了类与类之间的耦合度，减少了系统中类的数量，也减少了代码量。

      将抽象部分与他的实现部分分离这句话不是很好理解，其实这并不是将抽象类与他的派生类分离，而是抽象类和它的派生类用来实现自己的对象。这样还是不能理解的话。我们就先来认清什么是抽象化，什么是实现化，什么是脱耦。

      抽象化：其概念是将复杂物体的一个或几个特性抽出去而只注意其他特性的行动或过程。在面向对象就是将对象共同的性质抽取出去而形成类的过程。

      实现化：针对抽象化给出的具体实现。它和抽象化是一个互逆的过程，实现化是对抽象化事物的进一步具体化。

      脱耦：脱耦就是将抽象化和实现化之间的耦合解脱开，或者说是将它们之间的强关联改换成弱关联，将两个角色之间的继承关系改为关联关系。

      对于那句话：将抽象部分与他的实现部分分离套用《大话设计模式》里面的就是实现系统可能有多个角度分类，每一种角度都可能变化，那么把这种多角度分类给分离出来让他们独立变化，减少他们之间耦合。

      桥接模式中的所谓脱耦，就是指在一个软件系统的抽象化和实现化之间使用关联关系（组合或者聚合关系）而不是继承关系，从而使两者可以相对独立地变化，这就是桥接模式的用意。

二、 模式结构
      下图是桥接模式的UML结构图：

 

55555


      桥接模式主要包含如下几个角色：

      Abstraction：抽象类。 
      RefinedAbstraction：扩充抽象类。 
      Implementor：实现类接口。 
      ConcreteImplementor：具体实现类 。 

三、 模式实现
      模式场景我们就采用哪个画图的。其UML结构图如下：

66666

      首先是形状类：该类为一个抽象类，主要提供画形状的方法：Shape.java

复制代码
public abstract class Shape {
    Color color;

    public void setColor(Color color) {
        this.color = color;
    }
    
    public abstract void draw();
}
复制代码
      然后是三个形状 。圆形：Circle.java

复制代码
public class Circle extends Shape{

    public void draw() {
        color.bepaint("正方形");
    }
}
复制代码
      长方形：Rectangle.java

复制代码
public class Rectangle extends Shape{

    public void draw() {
        color.bepaint("长方形");
    }

}
复制代码
      正方形：Square.java

复制代码
public class Square extends Shape{

    public void draw() {
        color.bepaint("正方形");
    }

}
复制代码
      颜色接口：Color.java

public interface Color {
    public void bepaint(String shape);
}
      白色：White.java

复制代码
public class White implements Color{

    public void bepaint(String shape) {
        System.out.println("白色的" + shape);
    }

}
复制代码
      灰色：Gray.java

复制代码
public class Gray implements Color{

    public void bepaint(String shape) {
        System.out.println("灰色的" + shape);
    }
}
复制代码
      黑色：Black.java

复制代码
public class Black implements Color{

    public void bepaint(String shape) {
        System.out.println("黑色的" + shape);
    }
}
复制代码
      客户端：Client.java

复制代码
public class Client {
    public static void main(String[] args) {
        //白色
        Color white = new White();
        //正方形
        Shape square = new Square();
        //白色的正方形
        square.setColor(white);
        square.draw();
        
        //长方形
        Shape rectange = new Rectangle();
        rectange.setColor(white);
        rectange.draw();
    }
}
复制代码
      运行结果：

      白色的正方形 
      白色的长方形

四、 模式优缺点
优点
      1、分离抽象接口及其实现部分。提高了比继承更好的解决方案。

      2、桥接模式提高了系统的可扩充性，在两个变化维度中任意扩展一个维度，都不需要修改原有系统。

      3、实现细节对客户透明，可以对用户隐藏实现细节。

缺点
      1、桥接模式的引入会增加系统的理解与设计难度，由于聚合关联关系建立在抽象层，要求开发者针对抽象进行设计与编程。 
      2、桥接模式要求正确识别出系统中两个独立变化的维度，因此其使用范围具有一定的局限性。

五、 模式使用场景
       1、如果一个系统需要在构件的抽象化角色和具体化角色之间增加更多的灵活性，避免在两个层次之间建立静态的继承联系，通过桥接模式可以使它们在抽象层建立一个关联关系。

       2、对于那些不希望使用继承或因为多层次继承导致系统类的个数急剧增加的系统，桥接模式尤为适用。

       3、一个类存在两个独立变化的维度，且这两个维度都需要进行扩展。

六、 模式总结
      1、桥接模式实现了抽象化与实现化的脱耦。他们两个互相独立，不会影响到对方。

      2、对于两个独立变化的维度，使用桥接模式再适合不过了。

      3、对于“具体的抽象类”所做的改变，是不会影响到客户。
      
      
      
-------------




1.桥接模式的优点

（1）实现了抽象和实现部分的分离

桥接模式分离了抽象部分和实现部分，从而极大的提供了系统的灵活性，让抽象部分和实现部分独立开来，分别定义接口，这有助于系统进行分层设计，从而产生更好的结构化系统。对于系统的高层部分，只需要知道抽象部分和实现部分的接口就可以了。

（2）更好的可扩展性

由于桥接模式把抽象部分和实现部分分离了，从而分别定义接口，这就使得抽象部分和实现部分可以分别独立扩展，而不会相互影响，大大的提供了系统的可扩展性。

（3）可动态的切换实现

由于桥接模式实现了抽象和实现的分离，所以在实现桥接模式时，就可以实现动态的选择和使用具体的实现。

（4）实现细节对客户端透明，可以对用户隐藏实现细节。

2.桥接模式的缺点

（1）桥接模式的引入增加了系统的理解和设计难度，由于聚合关联关系建立在抽象层，要求开发者针对抽象进行设计和编程。

（2）桥接模式要求正确识别出系统中两个独立变化的维度，因此其使用范围有一定的局限性。

3.桥接模式的使用场景

（1）如果一个系统需要在构件的抽象化角色和具体化角色之间增加更多的灵活性，避免在两个层次之间建立静态的继承联系，通过桥接模式可以使它们在抽象层建立一个关联关系。

（2）抽象化角色和实现化角色可以以继承的方式独立扩展而互不影响，在程序运行时可以动态将一个抽象化子类的对象和一个实现化子类的对象进行组合，即系统需要对抽象化角色和实现化角色进行动态耦合。

（3）一个类存在两个独立变化的维度，且这两个维度都需要进行扩展。

（4）虽然在系统中使用继承是没有问题的，但是由于抽象化角色和具体化角色需要独立变化，设计要求需要独立管理这两者。

（5）对于那些不希望使用继承或因为多层次继承导致系统类的个数急剧增加的系统，桥接模式尤为适用