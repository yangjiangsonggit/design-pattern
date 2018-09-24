
中介者模式
======

1.优点

（1）适当地使用中介者模式可以避免同事类之间的过度耦合，使得各同事类之间可以相对独立地使用。

（2）使用中介者模式可以将对象的行为和协作进行抽象，能够比较灵活的处理对象间的相互作用。

（3）使用中介者模式可以将对象间多对多的关联转变为一对多的关联，使对象间的关系易于理解和维护。

2.缺点

中介者模式是一种比较常用的模式，也是一种比较容易被滥用的模式。对于大多数的情况，同事类之间的关系不会复杂到混乱不堪的网状结构，因此，大多数情况下，将对象间的依赖关系封装的同事类内部就可以的，没有必要非引入中介者模式。滥用中介者模式，只会让事情变的更复杂。所以，我们决定使用中介者模式之前要多方考虑、权衡利弊。

---------------------

我们平时写代码的过程，一个类必然会与其他类产生依赖关系，如果这种依赖关系如网状般错综复杂，那么必然会影响我们的代码逻辑以及执行效率，适当地使用中介者模式可以对这种依赖关系进行解耦使逻辑结构清晰，本篇博客，我们就一起学习中介者模式。

定义及使用场景
定义：中介者模式包装了一系列对象相互作用的方式，使得这些对象不必相互明显作用。从而使它们可以松散耦合。当某些对象之间的作用发生改变时，不会立即影响其他的一些对象之间的作用。保证这些作用可以彼此独立的变化。

使用场景： 
当对象之间的交互操作很多且每个对象的行为操作都依赖彼此时，为防止在修改一个对象的行为时，同时涉及很多其他对象的行为，可使用中介者模式。

UML类图


（1）Mediator：抽象中介者角色，定义了同事对象到中介者对象的接口，一般以抽象类的方式实现。

（2）ConcreteMediator：具体中介者角色，继承于抽象中介者，实现了父类定义的方法，它从具体的同事对象接受消息，向具体同事对象发出命令。

（3）Colleague：抽象同事类角色，定义了中介者对象的接口，它只知道中介者而不知道其他的同事对象。

（4）ConcreteColleague1、ConcreteColleague2：具体同事类角色，继承于抽象同事类，每个具体同事类都知道本身在小范围的行为，而不知道在大范围内的目的。

中介者模式通用代码
抽象同事类： 
Colleague.class

public abstract class Colleague {

    protected Mediator mediator;

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public abstract void operation();
}

具体同事类 
ConcreteColleagueA.class && ConcreteColleagueB.class

public class ConcreteColleagueA extends Colleague{

    public void notifyColleagueB() {
        mediator.notifyColleagueB();
    }

    @Override
    public void operation() {
        System.out.print("this is ConcreteColleagueA's operation\n");
    }
}

public class ConcreteColleagueB extends Colleague{

    public void notifyColleagueA() {
        mediator.notifyColleagueA();
    }

    @Override
    public void operation() {
        System.out.print("this is ConcreteColleagueB's operation\n");
    }
}

抽象中介者角色： 
Mediator.class

public abstract class Mediator {

    protected Colleague colleagueA;
    protected Colleague colleagueB;

    public Mediator(Colleague colleagueA, Colleague colleagueB) {
        this.colleagueA = colleagueA;
        this.colleagueB = colleagueB;
    }

    public abstract void notifyColleagueA();
    public abstract void notifyColleagueB();
}

ConcreteMediator.class

public class ConcreteMediator extends Mediator{

    public ConcreteMediator(Colleague colleagueA, Colleague colleagueB) {
        super(colleagueA, colleagueB);
    }

    @Override
    public void notifyColleagueA() {
        if (colleagueA != null) {
            colleagueA.operation();
        }
    }

    @Override
    public void notifyColleagueB() {
        if (colleagueB != null) {
            colleagueB.operation();
        }
    }
}


测试代码：

public class Client {
    public static void main(String[] args) {
        Colleague colleagueA = new ConcreteColleagueA();
        Colleague colleagueB = new ConcreteColleagueB();

        Mediator mediator = new ConcreteMediator(colleagueA, colleagueB);
        colleagueA.setMediator(mediator);
        colleagueB.setMediator(mediator);

        ((ConcreteColleagueA)colleagueA).notifyColleagueB();
        ((ConcreteColleagueB)colleagueB).notifyColleagueA();
    }
}

两个 Colleague 类成功通过 Mediator 进行了相互作用。上面是中介者模式的标准写法，在平时项目中，硬是将各业务的类抽象出一个 Colleague 父类是不太合理的，因为子类之间的业务逻辑的不同，导致他们很难抽象出一些公用方法，所以这时候使用中介者模式，可以省去 Colleague 这个角色，让 Mediator 直接依赖于几个同事子类；同时也可以不定义Mediator接口，把具体的中介者对象实现成为单例，这样同事对象不再持有中介者，而是在需要的时候直接获取中介者对象并调用；中介者也不再持有同事对象，而是在具体处理方法里面去创建，或获取，或从数据传入需要的同事对象。

中介者模式例子
同事：

//抽象同事类  
abstract class AbstractColleague {  
    protected AbstractMediator mediator;  

    /**既然有中介者，那么每个具体同事必然要与中介者有联系，  
     * 否则就没必要存在于 这个系统当中，这里的构造函数相当  
     * 于向该系统中注册一个中介者，以取得联系  
     */ 
    public AbstractColleague(AbstractMediator mediator) {  
        this.mediator = mediator;  
    }  

    // 在抽象同事类中添加用于与中介者取得联系（即注册）的方法  
    public void setMediator(AbstractMediator mediator) {  
        this.mediator = mediator;  
    }  
}  

//具体同事A  
class ColleagueA extends AbstractColleague {  

    //每个具体同事都通过父类构造函数与中介者取得联系  
    public ColleagueA(AbstractMediator mediator) {  
        super(mediator);  
    }  

    //每个具体同事必然有自己分内的事，没必要与外界相关联  
    public void self() {  
        System.out.println("同事A --> 做好自己分内的事情 ...");  
    }  

    //每个具体同事总有需要与外界交互的操作，通过中介者来处理这些逻辑并安排工作  
    public void out() {  
        System.out.println("同事A --> 请求同事B做好分内工作 ...");  
        super.mediator.execute("ColleagueB", "self");  
    }  
}  

//具体同事B  
class ColleagueB extends AbstractColleague {  

    public ColleagueB(AbstractMediator mediator) {  
        super(mediator);  
    }  

    public void self() {  
        System.out.println("同事B --> 做好自己分内的事情 ...");  
    }  

    public void out() {  
        System.out.println("同事B --> 请求同事A做好分内工作  ...");  
        super.mediator.execute("ColleagueA", "self");  
    }  
} 

中介者：

//抽象中介者  
abstract class AbstractMediator {  

    //中介者肯定需要保持有若干同事的联系方式  
    protected Hashtable<String, AbstractColleague> colleagues = new Hashtable<String, AbstractColleague>();  

    //中介者可以动态地与某个同事建立联系  
    public void addColleague(String name, AbstractColleague c) {  
        this.colleagues.put(name, c);  
    }     

    //中介者也可以动态地撤销与某个同事的联系  
    public void deleteColleague(String name) {  
        this.colleagues.remove(name);  
    }  

    //中介者必须具备在同事之间处理逻辑、分配任务、促进交流的操作  
    public abstract void execute(String name, String method);   
}  

//具体中介者  
class Mediator extends AbstractMediator{  

    //中介者最重要的功能，来回奔波与各个同事之间  
    public void execute(String name, String method) {  

        if("self".equals(method)){  //各自做好分内事  
            if("ColleagueA".equals(name)) {  
                ColleagueA colleague = (ColleagueA)super.colleagues.get("ColleagueA");  
                colleague.self();  
            }else {  
                ColleagueB colleague = (ColleagueB)super.colleagues.get("ColleagueB");  
                colleague.self();  
            }  
        }else { //与其他同事合作  
            if("ColleagueA".equals(name)) {  
                ColleagueA colleague = (ColleagueA)super.colleagues.get("ColleagueA");  
                colleague.out();  
            }else {  
                ColleagueB colleague = (ColleagueB)super.colleagues.get("ColleagueB");  
                colleague.out();  
            }  
        }  
    }  
} 

测试类：

//测试类  
public class Client {  
    public static void main(String[] args) {  

        //创建一个中介者  
        AbstractMediator mediator = new Mediator();  

        //创建两个同事  
        ColleagueA colleagueA = new ColleagueA(mediator);  
        ColleagueB colleagueB = new ColleagueB(mediator);  

        //中介者分别与每个同事建立联系  
        mediator.addColleague("ColleagueA", colleagueA);  
        mediator.addColleague("ColleagueB", colleagueB);  

        //同事们开始工作  
        colleagueA.self();  
        colleagueA.out();  
        System.out.println("======================合作愉快，任务完成！\n");  

        colleagueB.self();  
        colleagueB.out();  
        System.out.println("======================合作愉快，任务完成！");  
    }  
} 

具体中介者类Mediator中的execute()方法中现在就有一堆冗长的判断代码了。虽然可以把它分解并增加到Mediator类中的其它private方法中，但是具体的业务逻辑是少不了的。

所以，在解耦同事类之间的联系的同时，中介者自身也不免任务过重，因为几乎所有的业务逻辑都交代到中介者身上了，这就是中介者模式的不足之处。

对上面的例子还可以进行如下优化： 
修改后的同事

//抽象同事类  
abstract class AbstractColleague {  
    protected AbstractMediator mediator;      

    //舍去在构造函数中建立起与中介者的联系  
//  public AbstractColleague(AbstractMediator mediator) {  
//      this.mediator = mediator;  
//  }  

    // 在抽象同事类中添加用于与中介者取得联系（即注册）的方法  
    public void setMediator(AbstractMediator mediator) {  
        this.mediator = mediator;  
    }  
}  

//具体同事A  
class ColleagueA extends AbstractColleague {  

    //舍去在构造函数中建立起与中介者的联系  
//  public ColleagueA(AbstractMediator mediator) {  
//      super(mediator);  
//  }  

    //每个具体同事必然有自己分内的事，没必要与外界相关联  
    public void self() {  
        System.out.println("同事A --> 做好自己分内的事情 ...");  
    }  

    //每个具体同事总有需要与外界交互的操作，通过中介者来处理这些逻辑并安排工作  
    public void out() {  
        System.out.println("同事A --> 请求同事B做好分内工作 ...");  
        super.mediator.execute("ColleagueB", "self");  
    }  
}  

//具体同事B  
class ColleagueB extends AbstractColleague {  
    //舍去在构造函数中建立起与中介者的联系  
//  public ColleagueB(AbstractMediator mediator) {  
//      super(mediator);  
//  }  

    public void self() {  
        System.out.println("同事B --> 做好自己分内的事情 ...");  
    }  

    public void out() {  
        System.out.println("同事B --> 请求同事A做好分内工作  ...");  
        super.mediator.execute("ColleagueA", "self");  
    }  
} 

修改后的中介者：

//抽象中介者  
abstract class AbstractMediator {  

    //中介者肯定需要保持有若干同事的联系方式  
    protected Hashtable<String, AbstractColleague> colleagues = new Hashtable<String, AbstractColleague>();  

    //中介者可以动态地与某个同事建立联系  
    public void addColleague(String name, AbstractColleague c) {  

        // 在中介者这里帮助具体同事建立起于中介者的联系  
        c.setMediator(this);  
        this.colleagues.put(name, c);  
    }     

    //中介者也可以动态地撤销与某个同事的联系  
    public void deleteColleague(String name) {  
        this.colleagues.remove(name);  
    }  

    //中介者必须具备在同事之间处理逻辑、分配任务、促进交流的操作  
    public abstract void execute(String name, String method);   
} 

测试类


public class Client {  
    public static void main(String[] args) {  

        //创建一个中介者  
        AbstractMediator mediator = new Mediator();  

        //不用构造函数为具体同事注册中介者来取得联系了  
//      ColleagueA colleagueA = new ColleagueA(mediator);  
//      ColleagueB colleagueB = new ColleagueB(mediator);  

        ColleagueA colleagueA = new ColleagueA();  
        ColleagueB colleagueB = new ColleagueB();  

        //中介者分别与每个同事建立联系  
        mediator.addColleague("ColleagueA", colleagueA);  
        mediator.addColleague("ColleagueB", colleagueB);  

        //同事们开始工作  
        colleagueA.self();  
        colleagueA.out();  
        System.out.println("======================合作愉快，任务完成！\n");  

        colleagueB.self();  
        colleagueB.out();  
        System.out.println("======================合作愉快，任务完成！");  
    }  
} 

Android源码中的模式实现
Android Mediator样例：—KeyguardViewMediator 
关键代码： 
同事角色： 
KeyguardService：

./base/packages/SystemUI/src/com/android/systemui/keyguard/KeyguardService.java
1
public class KeyguardService extends Service {
    //中介者角色
    private KeyguardViewMediator mKeyguardViewMediator;

    @Override
    public void onCreate() {
        ((SystemUIApplication) getApplication()).startServicesIfNeeded();
        //中介者角色初始化
        mKeyguardViewMediator =
                ((SystemUIApplication) getApplication()).getComponent(KeyguardViewMediator.class);
    }

    private final IKeyguardService.Stub mBinder = new IKeyguardService.Stub() {

        @Override // Binder interface
        public void addStateMonitorCallback(IKeyguardStateCallback callback) {
            checkPermission();
            //调用中介者角色
            mKeyguardViewMediator.addStateMonitorCallback(callback);
        }

        @Override // Binder interface
        public void verifyUnlock(IKeyguardExitCallback callback) {
            checkPermission();
            //调用中介者角色
            mKeyguardViewMediator.verifyUnlock(callback);
        }
        ......
        @Override
        public void onActivityDrawn() {
            checkPermission();
            //调用中介者角色
            mKeyguardViewMediator.onActivityDrawn();
        }
    };

}

同事角色： 
FingerprintUnlockController：

./base/packages/SystemUI/src/com/android/systemui/statusbar/phone/FingerprintUnlockController.java
1
2
public class FingerprintUnlockController extends KeyguardUpdateMonitorCallback {
  //中介者角色
  private KeyguardViewMediator mKeyguardViewMediator;
    @Override
    public void onFingerprintAuthenticated(int userId) {
        ......       
        switch (mMode) {
            ...... 
            case MODE_WAKE_AND_UNLOCK:
                ...... 
                //调用角色者模式
                mKeyguardViewMediator.onWakeAndUnlocking();
                ...... 
    }
}

中介者：KeyguardViewMediator 
KeyguardViewMediator类充当了一个中介者角色，以对各个角色的通信进行协调。

还有诸如MVP模式中的presenter,还有Binder也都采用了中介者模式

---------------------

 