备忘录模式（Memento Pattern），
===
是行为型模式设计模式之一，该模式用于保存对象当前状态，并且在之后可以再次恢复到此状态。备忘录模式实现的方式需要保证被保存的对象状态不能被对象从外部访问，目的是为了保护被保存的这些对象状态的完整性以及内部实现不向外暴露，本篇博客，我们就来一起学习备忘录模式。


使用场景

备忘录模式使用的场景如下： 
1.需要保存一个对象在某一个时刻的状态或部分状态； 
2.如果用一个接口来让其他对象得到这些状态，将会暴露对象的实现细节并破坏对象的封装性，一个对象不希望外界直接访问其内部状态，通过中间对象可以简洁访问其内部状态。



备忘录模式UML图



Originator：负责创建一个备忘录，可以记录、恢复自身的内部状态，同时 Originator 还可以根据需要决定 Memento 存储自身的哪些内部状态。 
Memento：备忘录角色，用于存储 Originator 的内部状态，并且可以防止 Originator 以外的对象访问 Memento。 
CareTaker：负责存储备忘录，不能对备忘录的内容进行操作和访问，只能够将备忘录传递给其他对象。



备忘录模式通用代码

备忘录模式又可以分为“白箱”备忘录模式和“黑箱”备忘录模式。

“白箱”备忘录模式 
Java中，实现“宽”和“窄”两个接口并不容易，如果暂时忽略两个接口的区别，仅为备忘录角色提供一个宽接口的话，备忘录的内部存储状态就对所有对象公开，这就是“白箱实现”。 
“白箱”实现破坏了封装性，但是通过程序员自律，可以方便地实现备忘录模式。

“白箱”备忘录模式的Java实现如下：



public class Memento {
    private String state;


    public Memento(String state) {

        this.state = state;

    }


    public String getState() {

        return this.state;

    }


    public void setState(String state) {

        this.state = state;

    }

}

public class Originator {

    private String state;

    //工厂方法，返回一个新的备忘录对象
    public Memento createMemento() {

        return new Memento(state);

    }


    //将发起人恢复到备忘录对象所记载的状态
    public void restoreMemento(Memento memento) {

        this.state = memento.getState();

    }


    public String getState() {

        System.out.println("Current state:" + state);

        return this.state;

    }


    public void setState(String state) {

        this.state = state;

    }


}



public class Caretaker {
    private Memento memento;


    public Memento retrieveMemento() {

        return this.memento;

    }


    public void saveMemento(Memento memento) {

        this.memento = memento;

    }


}



public class Client {
    public static void main(String[] args) {

        Originator originator = new Originator();

        Caretaker caretaker = new Caretaker();

        //改变发起人的状态
        originator.setState("on");

        originator.getState();

        //创建备忘录对象，并将发起人对象的状态存储起来
        caretaker.saveMemento(originator.createMemento());

        //再次改变发起人对象的状态
        originator.setState("off");

        originator.getState();

        //恢复发起人对象的状态
        originator.restoreMemento(caretaker.retrieveMemento());

        originator.getState();

    }


}

“黑箱”备忘录模式 
“黑箱”备忘录模式相比“白箱”备忘录模式有如下区别： 
1.将Memento设成Originator类的内部类； 
2.将Memento的方法全部设成私有方法，这样只有它自己和发起人Originator可以调用； 
3.在外部提供一个标识接口MementoIF给Caretaker以及其他对象，标识接口MementoIF没有提供任何方法，因此对外部来说Memento对象的内容都是不可见的。





//标识接口（窄接口）

public interface MementoIF {

}


public class Originator2 {

    private Vector<Object> states;

    private int index;


    public Originator2() {

        states = new Vector<Object>();

        index = 0;

    }

    //工厂方法，返回一个新的备忘录对象

    public MementoIF createMemento() {

        return new Memento(this.states, index);

    }


    //将发起人恢复到备忘录对象所记载的状态

    public void restoreMemento(MementoIF memento) {

        states = ((Memento) memento).getStates();

        index = ((Memento) memento).getIndex();

    }


    //状态的赋值方法

    public void setState(Object state) {

        this.states.addElement(state);

        index++;

    }


    //辅助方法，打印出所有状态

    public void printStates() {

        System.out.println("Total number of states:" + index);

        for (Object o : states) {

            System.out.println(o.toString());

        }


    }
    //内部类

    protected class Memento implements MementoIF {

        private Vector<Object> saveStates;

        private int saveIndex;


        @SuppressWarnings("unchecked")

        //_states一定是Vector<Object类型的变量，复制后也一定是Vector<Object的变量

        private Memento(Vector<Object> _states, int _index) {

            //保存客户端传来的状态对象的拷贝，否则客户端的修改会影响到保存的状态。

            saveStates = (Vector<Object>) _states.clone();

            saveIndex = _index;

        }


        private Vector<Object> getStates() {

            return saveStates;

        }


        private int getIndex() {

            return saveIndex;

        }

    }


}

public class Caretaker2 {
    private Originator2 o;

    private Vector<MementoIF> mementos = new Vector<MementoIF>();

    private int currentIndex;


    public Caretaker2(Originator2 o) {

        this.o = o;

        currentIndex = 0;

    }


    //创建一个新的检查点

    public void createMemento() {

        mementos.addElement(o.createMemento());

        currentIndex++;

    }


    //将发起人恢复到某个检查点

    public void reStoreMemento(int index) {

        o.restoreMemento(mementos.elementAt(index));

    }


    //删除某个检查点

    public void removeMemento(int index) {

        mementos.removeElementAt(index);

    }

}




public class Client2 {
    public static void main(String[] args) {

        Originator2 o = new Originator2();

        Caretaker2 c = new Caretaker2(o);


        //改变发起人的状态

        o.setState("state0");

        //创建一个检查点

        c.createMemento();

        o.setState("state1");

        c.createMemento();

        o.setState("state2");

        c.createMemento();

        o.setState("state3");

        c.createMemento();

        o.setState("state4");

        c.createMemento();

        //打印出所有状态

        o.printStates();


        //恢复到第3个检查点

        System.out.println("Restoring to 3");

        c.reStoreMemento(3);

        o.printStates();

        //恢复到第0个检查点

        System.out.println("Restoring to 0");

        c.reStoreMemento(0);

        o.printStates();

        //恢复到第4个检查点

        System.out.println("Restoring to 4");

        c.reStoreMemento(4);

        o.printStates();

    }


}


跟例子1相比，负责人角色除了负责保存状态之外，还负责发起人状态的恢复，功能增强了。

总结一下： 
1.“黑箱”备忘录的实现中，将Memento类做成Originator的内部类，并将其方法全部设置成private，其实这样一般来说就已经足够了，不需要再使用窄接口MementoIF。因为这样做的话外部拿到Memento类的实例，由于其方法都是private的，所以该方法只有Originator类可以调用，其它类是调用不了的，也就无法修改其中的内容。

2.那么窄接口什么时候使用呢，我觉得应该是这样，如果Memento类因为某些原因不能做成内部类，那么就应该定义两个接口，一个WideMemento，一个NarrowMemento（一般没有定义任何方法），前者供Originator类使用，后者供其它类使用。这样的缺点就是，外部只要将得到的实例强制转化为WideMemento类型，同样可以访问到Memento类的内容。

3.如果要继承Originator类并且不改变Memento类的代码，那么Memento类的方法应该设置成默认属性（package access），而不是private。



Android源码中的备忘录模式

1.onSaveInstanceState和onRestoreInstanceState 
当Activity不是正常方式退出，且Activity在随后的时间内被系统杀死之前会调用这两个方法让开发人员可以有机会存储Activity相关信息，且在下次返回Activity时恢复这些数据。通过这两个函数。开发人员能够在某些特殊场景下储存与界面相关的信息，提升用户体验。

(1) onCreate(Bundle savedInstanceState) 方法 
Activity 创建时回调 : 该方法会自动传入一个 Bundle 对象, 该 Bundle 对象就是上次被系统销毁时在 onSaveInstanceState 或者 onRestoreInstanceState 中保存的数据; 
– 注意 : 只有是系统自动回收的时候才会保存 Bundle 对象数据; 
– Bundle 对象来源 : onCreate() 方法中的 Bundle 对象参数, 是在 onSaveInstance() 或者 onRestoreInstanceState() 方法中保存的 Bundle 对象; 
.

(2) onSaveInstanceState(Bundle outState) 方法 
outState 参数作用 :  
– 数据保存 : Activity 声明周期结束的时候, 需要保存 Activity 状态的时候, 会将要保存的数据使用键值对的形式 保存在 Bundle 对象中; 
– 恢复数据 : 在 Activity 的 onCreate()方法 创建 Activity 的时候会传入一个 Bundle 对象, 这个 Bundle 对象就是这个 outState 参数;

调用时机 : Activity 容易被销毁的时候调用, 注意是容易被销毁, 也可能没有销毁就调用了; 
– 按下Home键 : Activity 进入了后台, 此时会调用该方法; 
– 按下电源键 : 屏幕关闭, Activity 进入后台; 
– 启动其它 Activity : Activity 被压入了任务栈的栈底; 
– 横竖屏切换 : 会销毁当前 Activity 并重新创建;

onSaveInstanceState方法调用注意事项 :  
– 用户主动销毁不会调用 : 当用户点击回退键 或者 调用了 finish() 方法, 不会调用该方法; 
– 调用时机不固定 : 该方法一定是在 onStop() 方法之前调用, 但是不确定是在 onPause() 方法之前 还是 之后调用; 
– 布局中组件状态存储 : 每个组件都 实现了 onSaveInstance() 方法, 在调用函数的时候, 会自动保存组件的状态, 注意, 只有有 id 的组件才会保存; 
– 关于默认的 super.onSaveInstanceState(outState) : 该默认的方法是实现 组件状态保存的;

(3) onRestoreInstanceState(Bundle savedInstanceState) 方法 
方法回调时机 : 在 Activity 被系统销毁之后 恢复 Activity 时被调用, 只有销毁了之后重建的时候才调用, 如果内存充足, 系统没有销毁这个 Activity, 就不需要调用; 
– Bundle 对象传递 : 该方法保存的 Bundle 对象在 Activity 恢复的时候也会通过参数传递到 onCreate() 方法中; 
– 位于生命周期位置吧 : 该方法在 onResume() 方法之前保存信息;

---------------------

 