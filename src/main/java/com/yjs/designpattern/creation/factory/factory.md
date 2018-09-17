工厂模式
====
简单列一下这个模式的家族：

1、静态工厂模式

这个最常见了，项目中的辅助类，TextUtil.isEmpty等，类+静态方法。
2、简单工厂模式（店里买肉夹馍）

定义：通过专门定义一个类来负责创建其他类的实例，被创建的实例通常都具有共同的父类。
根据类型直接创建肉夹馍：SimpleRoujiaMoFactory.java
 public RoujiaMo creatRoujiaMo(String type) {
     RoujiaMo roujiaMo = null;
     switch (type) {
         case "Suan":
             roujiaMo = new ZSuanRoujiaMo();
             break;
         case "La":
             roujiaMo = new ZLaRoujiaMo();
             break;
         case "Tian":
             roujiaMo = new ZTianRoujiaMo();
             break;
         default:// 默认为酸肉夹馍
             roujiaMo = new ZSuanRoujiaMo();
             break;
     }
     return roujiaMo;
 }
3、工厂方法模式（开分店）

定义：定义一个创建对象的接口，但由子类决定要实例化的类是哪一个。工厂方法模式把类实例化的过程推迟到子类。
对比定义：
1、定义了创建对象的一个接口：public abstract RouJiaMo sellRoujiaMo(String type);
2、由子类决定实例化的类，可以看到我们的馍是子类生成的。
提供创建肉夹馍店抽象方法：RoujiaMoStore.java

public abstract RoujiaMo sellRoujiaMo(String type);
具体实现抽象方法：XianRoujiaMoStore.java

分店依旧使用简单工厂模式：XianSimpleRoujiaMoFactory.java

4、抽象工厂模式（使用官方提供的原料）

定义：提供一个接口，用于创建相关的或依赖对象的家族，而不需要明确指定具体类。
对比定义：
1、提供一个接口：public interface RouJiaMoYLFactroy
2、用于创建相关的或依赖对象的家族 public Meat createMeat();public YuanLiao createYuanliao();我们接口用于创建一系列的原材料。
创建用于提供原料的接口工厂：RoujiaMoYLFactory.java
各自分店实现接口，完成原料提供：XianRoujiaMoYLFoctory.java
准备时，使用官方的原料：RoujiaMo.java
 /**
 * 准备工作
 */
public void prepare(RoujiaMoYLFactory roujiaMoYLFactory) {
    	Meet meet = roujiaMoYLFactory.creatMeet();
    	YuanLiao yuanLiao = roujiaMoYLFactory.creatYuanLiao();
    	Log.e("---RoujiaMo:", "使用官方的原料 ---" + name + ": 揉面-剁肉-完成准备工作 yuanLiao:"+meet+"yuanLiao:"+yuanLiao);
}