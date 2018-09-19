##什么是代理模式？
      
       代理模式的定义：代理模式给某一个对象提供一个代理对象，并由代理对象控制对原对象的引用。通俗的来讲代理模式就是我们生活中常见的中介。

举个例子来说明：假如说我现在想买一辆二手车，虽然我可以自己去找车源，做质量检测等一系列的车辆过户流程，但是这确实太浪费我得时间和精力了。我只是想买一辆车而已为什么我还要额外做这么多事呢？于是我就通过中介公司来买车，他们来给我找车源，帮我办理车辆过户流程，我只是负责选择自己喜欢的车，然后付钱就可以了。用图表示如下：



为什么要用代理模式？
中介隔离作用：在某些情况下，一个客户类不想或者不能直接引用一个委托对象，而代理类对象可以在客户类和委托对象之间起到中介的作用，其特征是代理类和委托类实现相同的接口。
开闭原则，增加功能：代理类除了是客户类和委托类的中介之外，我们还可以通过给代理类增加额外的功能来扩展委托类的功能，这样做我们只需要修改代理类而不需要再修改委托类，符合代码设计的开闭原则。代理类主要负责为委托类预处理消息、过滤消息、把消息转发给委托类，以及事后对返回结果的处理等。代理类本身并不真正实现服务，而是同过调用委托类的相关方法，来提供特定的服务。真正的业务功能还是由委托类来实现，但是可以在业务功能执行的前后加入一些公共的服务。例如我们想给项目加入缓存、日志这些功能，我们就可以使用代理类来完成，而没必要打开已经封装好的委托类。
有哪几种代理模式？
       我们有多种不同的方式来实现代理。如果按照代理创建的时期来进行分类的话， 可以分为两种：静态代理、动态代理。静态代理是由程序员创建或特定工具自动生成源代码，在对其编译。在程序员运行之前，代理类.class文件就已经被创建了。动态代理是在程序运行时通过反射机制动态创建的。

1.静态代理     

 第一步：创建服务类接口

 

复制代码
 1 package main.java.proxy;
 2 
 3 /**
 4  * @Auther: dan gao
 5  * @Description:
 6  * @Date: 22:40 2018/1/9 0009
 7  */
 8 public interface BuyHouse {
 9     void buyHosue();
10 }
复制代码
 

第二步：实现服务接口

复制代码
 1 import main.java.proxy.BuyHouse;
 2 
 3 /**
 4  * @Auther: dan gao
 5  * @Description:
 6  * @Date: 22:42 2018/1/9 0009
 7  */
 8 public class BuyHouseImpl implements BuyHouse {
 9 
10     @Override
11     public void buyHosue() {
12         System.out.println("我要买房");
13     }
14 }
复制代码
第三步：创建代理类

复制代码
 1 package main.java.proxy.impl;
 2 
 3 import main.java.proxy.BuyHouse;
 4 
 5 /**
 6  * @Auther: dan gao
 7  * @Description:
 8  * @Date: 22:43 2018/1/9 0009
 9  */
10 public class BuyHouseProxy implements BuyHouse {
11 
12     private BuyHouse buyHouse;
13 
14     public BuyHouseProxy(final BuyHouse buyHouse) {
15         this.buyHouse = buyHouse;
16     }
17 
18     @Override
19     public void buyHosue() {
20         System.out.println("买房前准备");
21         buyHouse.buyHosue();
22         System.out.println("买房后装修");
23 
24     }
25 }
复制代码
第四步：编写测试类

复制代码
import main.java.proxy.impl.BuyHouseImpl;
import main.java.proxy.impl.BuyHouseProxy;

/**
 * @Auther: dan gao
 * @Description:
 * @Date: 22:43 2018/1/9 0009
 */
public class ProxyTest {
    public static void main(String[] args) {
        BuyHouse buyHouse = new BuyHouseImpl();
        buyHouse.buyHosue();
        BuyHouseProxy buyHouseProxy = new BuyHouseProxy(buyHouse);
        buyHouseProxy.buyHosue();
    }
}
复制代码
静态代理总结：

优点：可以做到在符合开闭原则的情况下对目标对象进行功能扩展。

缺点：我们得为每一个服务都得创建代理类，工作量太大，不易管理。同时接口一旦发生改变，代理类也得相应修改。                                             

2.动态代理
　　在动态代理中我们不再需要再手动的创建代理类，我们只需要编写一个动态处理器就可以了。真正的代理对象由JDK再运行时为我们动态的来创建。

第一步：编写动态处理器

复制代码
 1 package main.java.proxy.impl;
 2 
 3 import java.lang.reflect.InvocationHandler;
 4 import java.lang.reflect.Method;
 5 
 6 /**
 7  * @Auther: dan gao
 8  * @Description:
 9  * @Date: 20:34 2018/1/12 0012
10  */
11 public class DynamicProxyHandler implements InvocationHandler {
12 
13     private Object object;
14 
15     public DynamicProxyHandler(final Object object) {
16         this.object = object;
17     }
18 
19     @Override
20     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
21         System.out.println("买房前准备");
22         Object result = method.invoke(object, args);
23         System.out.println("买房后装修");
24         return result;
25     }
26 }
复制代码
第二步：编写测试类

复制代码
 1 package main.java.proxy.test;
 2 
 3 import main.java.proxy.BuyHouse;
 4 import main.java.proxy.impl.BuyHouseImpl;
 5 import main.java.proxy.impl.DynamicProxyHandler;
 6 
 7 import java.lang.reflect.Proxy;
 8 
 9 /**
10  * @Auther: dan gao
11  * @Description:
12  * @Date: 20:38 2018/1/12 0012
13  */
14 public class DynamicProxyTest {
15     public static void main(String[] args) {
16         BuyHouse buyHouse = new BuyHouseImpl();
17         BuyHouse proxyBuyHouse = (BuyHouse) Proxy.newProxyInstance(BuyHouse.class.getClassLoader(), new
18                 Class[]{BuyHouse.class}, new DynamicProxyHandler(buyHouse));
19         proxyBuyHouse.buyHosue();
20     }
21 }
复制代码
 注意Proxy.newProxyInstance()方法接受三个参数：

ClassLoader loader:指定当前目标对象使用的类加载器,获取加载器的方法是固定的
Class<?>[] interfaces:指定目标对象实现的接口的类型,使用泛型方式确认类型
InvocationHandler:指定动态处理器，执行目标对象的方法时,会触发事件处理器的方法
动态代理总结：虽然相对于静态代理，动态代理大大减少了我们的开发任务，同时减少了对业务接口的依赖，降低了耦合度。但是还是有一点点小小的遗憾之处，那就是它始终无法摆脱仅支持interface代理的桎梏，因为它的设计注定了这个遗憾。回想一下那些动态生成的代理类的继承关系图，它们已经注定有一个共同的父类叫Proxy。Java的继承机制注定了这些动态代理类们无法实现对class的动态代理，原因是多继承在Java中本质上就行不通。有很多条理由，人们可以否定对 class代理的必要性，但是同样有一些理由，相信支持class动态代理会更美好。接口和类的划分，本就不是很明显，只是到了Java中才变得如此的细化。如果只从方法的声明及是否被定义来考量，有一种两者的混合体，它的名字叫抽象类。实现对抽象类的动态代理，相信也有其内在的价值。此外，还有一些历史遗留的类，它们将因为没有实现任何接口而从此与动态代理永世无缘。如此种种，不得不说是一个小小的遗憾。但是，不完美并不等于不伟大，伟大是一种本质，Java动态代理就是佐例。

3.CGLIB代理
       JDK实现动态代理需要实现类通过接口定义业务方法，对于没有接口的类，如何实现动态代理呢，这就需要CGLib了。CGLib采用了非常底层的字节码技术，其原理是通过字节码技术为一个类创建子类，并在子类中采用方法拦截的技术拦截所有父类方法的调用，顺势织入横切逻辑。但因为采用的是继承，所以不能对final修饰的类进行代理。JDK动态代理与CGLib动态代理均是实现Spring AOP的基础。

第一步：创建CGLIB代理类

复制代码
 1 package dan.proxy.impl;
 2 
 3 import net.sf.cglib.proxy.Enhancer;
 4 import net.sf.cglib.proxy.MethodInterceptor;
 5 import net.sf.cglib.proxy.MethodProxy;
 6 
 7 import java.lang.reflect.Method;
 8 
 9 /**
10  * @Auther: dan gao
11  * @Description:
12  * @Date: 20:38 2018/1/16 0016
13  */
14 public class CglibProxy implements MethodInterceptor {
15     private Object target;
16     public Object getInstance(final Object target) {
17         this.target = target;
18         Enhancer enhancer = new Enhancer();
19         enhancer.setSuperclass(this.target.getClass());
20         enhancer.setCallback(this);
21         return enhancer.create();
22     }
23 
24     public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
25         System.out.println("买房前准备");
26         Object result = methodProxy.invoke(object, args);
27         System.out.println("买房后装修");
28         return result;
29     }
30 }
复制代码
 

第二步：创建测试类

复制代码
 1 package dan.proxy.test;
 2 
 3 import dan.proxy.BuyHouse;
 4 import dan.proxy.impl.BuyHouseImpl;
 5 import dan.proxy.impl.CglibProxy;
 6 
 7 /**
 8  * @Auther: dan gao
 9  * @Description:
10  * @Date: 20:52 2018/1/16 0016
11  */
12 public class CglibProxyTest {
13     public static void main(String[] args){
14         BuyHouse buyHouse = new BuyHouseImpl();
15         CglibProxy cglibProxy = new CglibProxy();
16         BuyHouseImpl buyHouseCglibProxy = (BuyHouseImpl) cglibProxy.getInstance(buyHouse);
17         buyHouseCglibProxy.buyHosue();
18     }
19 }
复制代码
 

CGLIB代理总结： CGLIB创建的动态代理对象比JDK创建的动态代理对象的性能更高，但是CGLIB创建代理对象时所花费的时间却比JDK多得多。所以对于单例的对象，因为无需频繁创建对象，用CGLIB合适，反之使用JDK方式要更为合适一些。同时由于CGLib由于是采用动态创建子类的方法，对于final修饰的方法无法进行代理。