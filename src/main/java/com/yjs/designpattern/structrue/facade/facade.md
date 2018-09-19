
外观模式(门面模式)

定义：提供一个统一的接口，用来访问子系统中的一群接口，外观定义了一个高层的接口，让子系统更容易使用。其实就是为了方便客户的使用，把一群操作，
封装成一个方法。

面试时面试官问了我一个问题，事务有哪些基本特性？我不假思索地回答：原子性，一致性，隔离性和持久性，并对这四个特性的概念做了描述：

原子性：是指每个事务都是一个不可分割的最小单元，事务要么成功提交，要么失败回滚，不存在半成功半失败的情况。

一致性：跟原子性密切相关，是指事务的执行结果应该使数据库从一种一致性状态到另一种一致性状态。

隔离性：事务与事务之前是隔离开的，一个事务的执行不能被其他事务干扰。

持久性：事务一旦成功提交，它执行的结果应该能够持久化到数据库中，接下来的其他操作及故障不应该对它的执行结果有任何影响。

面试官再问：能举出生活中所看到的事务的例子吗？

我：这个...嗯...这个...

看来不仔细领会概念的内容而死记硬背是过不了关的，其实生活之中事务的例子很多，拿复杂一点的例子来说，银行转账就是一个典型的事务实例，当你转账的时候，钱从你账户划出到对方账户收到钱应该是在一个事务里面，要么转账成功要么转账失败，不存在说你的钱从你的账户转出后对对方账号没收到钱，ATM却告诉你转账成功了。这个转账的过程就体现了事务的四个特征。如果从简单的例子来说，我们每天开车，从打开车门到车子开始走这也是一个完整的事务，少了任何一个步骤例如忘记启动发动机或没有挂挡都无法使这个事务成功提交。

以上扯远了，但是今天我们要说的设计模式的例子确实跟开车有关。

试想这样一种场景，我们每天都要开车上下班，为了使车子走起来我们要做些什么操作？假设我们开的是手动挡的车，我们需要按这个步骤来操作：

1. 用遥控钥匙解锁车门

2. 打开车门

3. 关上车门

4. 启动发动机

5. 挂挡

6. 松手刹

7. 松离合器

8. 车子开始走

如果用java代码来描述这一系列的动作该怎样设计呢？很显然，我们需要几个类，而每个类都有自己的一些行为，我先用UML类图来描述一下这些类以及它们的行为。



为了更深入地探讨这种设计模式能够解决的问题，我把设计好的每一个类的代码贴出来，虽然有点繁琐，但是代码也会很直观。

汽车钥匙：

复制代码
public class CarKeys {
    public void unlock() {
        System.out.println("Key unlock car.");
    }
    
    public void lock() {
        System.out.println("Key lock car.");
    }
}
复制代码
车门：

复制代码
public class CarDoors {
    public void open() {
        System.out.println("Open the car door.");
    }
    
    public void close() {
        System.out.println("Close the car door.");
    }
}
复制代码
发动机：

复制代码
public class Engine {
    public void start() {
        System.out.println("Car Engine started.");
    }
    
    public void stop() {
        System.out.println("Car Engine stopped.");
    }
}
复制代码
变速箱：

public class Gear {
    public void setGear(int gear) {
        System.out.println(String.format("Change to gear %s", gear));
    }
}
手刹：

复制代码
public class ParkingBrake {
    public void release() {
        System.out.println("Release parking brake.");
    }
    
    public void hold() {
        System.out.println("Hold parking brake.");
    }
}
复制代码
离合器：

复制代码
public class Clutch {
    public void release() {
        System.out.println("Release clutch slowly.");
    }
    
    public void push() {
        System.out.println("Push clutch quickly");
    }
}
复制代码
汽车：

复制代码
public class Car {
    public void go() {
        System.out.println("Car go.");
    }
    
    public void stop() {
        System.out.println("Car Stop");
    }
}
复制代码
测试类：

复制代码
import org.junit.Before;
import org.junit.Test;

public class MyTest2 {
    private CarKeys carKeys;
    private CarDoors carDoors;
    private Engine engine;
    private Gear gear;
    private ParkingBrake parkingBrake;
    private Clutch clutch;
    private Car car;
    private DriveCarFacade facade;
    
    @Before
    public void setup() {
        carKeys = new CarKeys();
        carDoors = new CarDoors();
        engine = new Engine();
        gear = new Gear();
        parkingBrake = new ParkingBrake();
        clutch = new Clutch();
        car = new Car();
        facade = new DriveCarFacade();
        facade.setCarKeys(carKeys);
        facade.setCarDoors(carDoors);
        facade.setEngine(engine);
        facade.setGear(gear);
        facade.setParkingBrake(parkingBrake);
        facade.setClutch(clutch);
        facade.setCar(car);
    }
    
    @Test
    public void typicalWay4Go() {
        carKeys.unlock();
        carDoors.open();
        engine.start();
        gear.setGear(1);
        parkingBrake.release();
        clutch.release();
        car.go();
    }    
}
复制代码
测试结果：



从以上UML类图及代码可以看到，我创建了7个类，每个类都有自己的行为，为了使车子走起来，我们有一个客户端类来驱动这7个类及它们的行为，但是从这种设计里面我们看到一些弊端：

1. 车子的启动顺序由客户决定，但是万一客户弄错了呢？例如还没有按遥控钥匙就去开车门或是发动机还没有启动就挂挡。或者更糟糕的情况，客户根本就没有调用松离合器的方法。

2. 现在7个组件跟客户都是聚合关系，如果这7个组件类里面有关于行为方法的改动(例如参数列表的变化)就势必要更改客户端的调用代码。

从以上两个缺点来看，我觉得这种设计是紧耦合并且是脆弱的。试想有一天，客户希望他的汽车能够自动驾驶，他不想要每天重复这么多繁琐的动作，他想要的就是按一下汽车钥匙的go按钮然后汽车把剩下的所有步骤都完成，他只要下车去办公室就好了。

那么如何解决这种问题？

为了解决这种问题，我们需要引入外观模式，也有的称之为门面模式。门面模式的定义是这样的：外观模式提供了一个统一的接口，用来访问子系统中的一群接口。外观定义了一个高层接口，让子系统更容易使用。

外观模式也诠释了面向对象设计原则中的最少知识原则，最少知识意指一个类应该只跟它需要打交道的类沟通，这样可以有效的保证松散耦合。

那么我们开始来实现外观模式吧！还是先上UML类图来个鸟瞰全局：



外观类：

复制代码
public class DriveCarFacade {
    private CarKeys carKeys;
    private CarDoors carDoors;
    private Engine engine;
    private Gear gear;
    private ParkingBrake parkingBrake;
    private Clutch clutch;
    private Car car;
    
    public void setCarKeys(CarKeys carKeys) {
        this.carKeys = carKeys;
    }
    public void setCarDoors(CarDoors carDoors) {
        this.carDoors = carDoors;
    }
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    public void setGear(Gear gear) {
        this.gear = gear;
    }
    public void setParkingBrake(ParkingBrake parkingBrake) {
        this.parkingBrake = parkingBrake;
    }
    public void setClutch(Clutch clutch) {
        this.clutch = clutch;
    }
    public void setCar(Car car) {
        this.car = car;
    }

    public void go() {
        this.carKeys.unlock();
        this.carDoors.open();
        this.engine.start();
        this.gear.setGear(1);
        this.parkingBrake.release();
        this.clutch.release();
        this.car.go();
    }
    
    public void stop() {
        this.clutch.push();
        this.gear.setGear(0);
        this.parkingBrake.hold();
        this.engine.stop();
        this.car.stop();
        this.carDoors.close();
        this.carKeys.lock();
    }
}
复制代码
完整测试类：

复制代码
import org.junit.Before;
import org.junit.Test;

public class MyTest {
    
    private CarKeys carKeys;
    private CarDoors carDoors;
    private Engine engine;
    private Gear gear;
    private ParkingBrake parkingBrake;
    private Clutch clutch;
    private Car car;
    private DriveCarFacade facade;
    
    @Before
    public void setup() {
        carKeys = new CarKeys();
        carDoors = new CarDoors();
        engine = new Engine();
        gear = new Gear();
        parkingBrake = new ParkingBrake();
        clutch = new Clutch();
        car = new Car();
        facade = new DriveCarFacade();
        facade.setCarKeys(carKeys);
        facade.setCarDoors(carDoors);
        facade.setEngine(engine);
        facade.setGear(gear);
        facade.setParkingBrake(parkingBrake);
        facade.setClutch(clutch);
        facade.setCar(car);
    }
    
    @Test
    public void typicalWay4Go() {
        carKeys.unlock();
        carDoors.open();
        engine.start();
        gear.setGear(1);
        parkingBrake.release();
        clutch.release();
        car.go();
    }
    
    @Test
    public void typicalWay4Stop() {
        clutch.push();
        gear.setGear(0);
        parkingBrake.hold();
        engine.stop();
        car.stop();
        carDoors.close();
        carKeys.lock();
        
    }
    
    @Test
    public void facadeWay4Go() {
        facade.go();
    }
    
    @Test
    public void facedeWay4Stop() {
        facade.stop();
    }

}
复制代码
从类图你能够看到，7个组件类现在跟外观类是聚合关系，客户类现在不需要跟7个组件类打交道，它只需要跟掮客外观类打交道就好了，这种设计的好处是显而易见的：

1. 客户端跟子系统松散耦合，子系统的改变不会造成客户端调用者的改变。

2. 外观类可以根据需要更改算法（操作的顺序），客户端对这些改变是不知情的。

3. 增加子系统或者替换子系统，对客户没有影响。

4. 实践了最小知识原则。

各位看官看到这里，有没有觉得其实我们可以使用另一种设计模式来达到同样的效果？是的，其实我们可以使用命令模式来做到同样的效果，我们可以把每一个操作定义为子命令，然后用一个宏来集合一群子命令，宏里面是可以定义操作顺序的，最后客户端只需要调用这个宏就可以了，一样可以达到客户端跟组件类松耦合的目的，只不过架构方式不一样而已，关于具体实现，我会另开一篇来详细阐述，在这里我就抛砖引玉，各位有何看法，欢迎拍砖:)