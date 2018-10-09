##simple    c#

    ：解释器模式，平常用的比较的少，所以在写这个模式之前在博客园搜索了一番，看完之后那叫一个头大。篇幅很长，我鼓足了劲看了半天的描述跟解释，可能是本人的水平有限，或者是耐心太差，看到一半就有点扛不住了。我感觉对于一个菜鸟或者是没接触过设计模式的人来说，在看设计模式的时候更希望作者能简短的用几行代码来描述设计模式，这样起码看完大体有个概念。
    
        
    
         概述：
    
     
    
           Interpreter模式也叫解释器模式，是由GoF提出的23种设计模式中的一种。Interpreter是行为模式之一，它是一种特殊的设计模式，它建立一个解释器，对于特定的计算机程序设计语言，用来解释预先定义的文法。
    
     
    
         结构图：
     
    
     代码举例（我已经最简单化了，一看即懂）
    
    
     /// <summary>
        /// 解释器上下文环境类。用来存储解释器的上下文环境，比如需要解释的文法等。
        /// </summary>
        class Context
        {
            private int sum;
            public int Sum
            {
                get { return sum; }
                set { sum=value;}
            }
         
        }
        /// <summary>
        /// 解释器抽象类。
        /// </summary>
       abstract class AbstractExpreesion
        {
            public abstract void Interpret(Context context);
           
         
        }
        /// <summary>
        ///   解释器具体实现类。自加
        /// </summary>
        class PlusExpression : AbstractExpreesion
        {
            public override void Interpret(Context context)
            {
                int sum = context.Sum;
                sum++;
                context.Sum = sum;
     
            }
        }
        /// <summary>
        ///   解释器具体实现类。 自减
        /// </summary>
        class MinusExpression : AbstractExpreesion
        {
            public override void Interpret(Context context)
            {
                int sum = context.Sum;
                sum--;
                context.Sum = sum;
     
            }
        }
    客户端调用
    
        class Program
        {
            static void Main(string[] args)
            {
                Context context = new Context();
                context.Sum = 10;
                List<AbstractExpreesion> list = new List<AbstractExpreesion>();
                //运行加法三次
                list.Add(new PlusExpression());
                list.Add(new PlusExpression());
                list.Add(new PlusExpression());
                //运行减法两次
                list.Add(new MinusExpression());
                list.Add(new MinusExpression());
                for (int i = 0; i < list.Count(); i++)
                {
                    AbstractExpreesion expression = list[i];
                    expression.Interpret(context);
                }
                Console.WriteLine(context.Sum);
                Console.ReadLine();
                //得出结果为11
            }
        }
      看完之后，是不是觉得很简单，无非是设定几个公式，然后统一进行执行。最终得到结果！设计模式代表的是一种思想，至于怎么千变万化就看大家的了
    
     
    
          适用场景：
    
     
    
            1.当有一个语言需要解释执行，并且你可将该语言中的句子表示为一个抽象语法树，可以使用解释器模式。而当存在以下情况时该模式效果最好
    
            2.该文法的类层次结构变得庞大而无法管理。此时语法分析程序生成器这样的工具是最好的选择。他们无需构建抽象语法树即可解释表达式，这样可以节省空间而且还可能节省时间。
    
            3.效率不是一个关键问题，最高效的解释器通常不是通过直接解释语法分析树实现的，而是首先将他们装换成另一种形式，例如，正则表达式通常被装换成状态机，即使在这种情况下，转换器仍可用解释器模式实现，该模式仍是有用的
            
            
##java
        
     在阎宏博士的《JAVA与模式》一书中开头是这样描述解释器（Interpreter）模式的：
     
     　　解释器模式是类的行为模式。给定一个语言之后，解释器模式可以定义出其文法的一种表示，并同时提供一个解释器。客户端可以使用这个解释器来解释这个语言中的句子。
     
     解释器模式的结构
     　　下面就以一个示意性的系统为例，讨论解释器模式的结构。系统的结构图如下所示：
     
     
     
     　　模式所涉及的角色如下所示：
     
     　　（1）抽象表达式(Expression)角色：声明一个所有的具体表达式角色都需要实现的抽象接口。这个接口主要是一个interpret()方法，称做解释操作。
     
     　　（2）终结符表达式(Terminal Expression)角色：实现了抽象表达式角色所要求的接口，主要是一个interpret()方法；文法中的每一个终结符都有一个具体终结表达式与之相对应。比如有一个简单的公式R=R1+R2，在里面R1和R2就是终结符，对应的解析R1和R2的解释器就是终结符表达式。
     
     　　（3）非终结符表达式(Nonterminal Expression)角色：文法中的每一条规则都需要一个具体的非终结符表达式，非终结符表达式一般是文法中的运算符或者其他关键字，比如公式R=R1+R2中，“+"就是非终结符，解析“+”的解释器就是一个非终结符表达式。
     
     　　（4）环境(Context)角色：这个角色的任务一般是用来存放文法中各个终结符所对应的具体值，比如R=R1+R2，我们给R1赋值100，给R2赋值200。这些信息需要存放到环境角色中，很多情况下我们使用Map来充当环境角色就足够了。
     
      
     
     　　为了说明解释器模式的实现办法，这里给出一个最简单的文法和对应的解释器模式的实现，这就是模拟Java语言中对布尔表达式进行操作和求值。
     
     　　在这个语言中终结符是布尔变量，也就是常量true和false。非终结符表达式包含运算符and，or和not等布尔表达式。这个简单的文法如下：
     
     　　　　Expression  ::= Constant | Variable | Or | And | Not
     
     　　　　And 　　　　::= Expression 'AND' Expression
     
     　　　　Or　　　　　::= Expression 'OR' Expression
     
     　　　　Not　　　　 ::= 'NOT' Expression
     
     　　　　Variable　　::= 任何标识符
     
     　　　　Constant    ::= 'true' | 'false'
     
     　　解释器模式的结构图如下所示：
     
     　　
     
     　　源代码
     　　抽象表达式角色
     
     复制代码
     public abstract class Expression {
         /**
          * 以环境为准，本方法解释给定的任何一个表达式
          */
         public abstract boolean interpret(Context ctx);
         /**
          * 检验两个表达式在结构上是否相同
          */
         public abstract boolean equals(Object obj);
         /**
          * 返回表达式的hash code
          */
         public abstract int hashCode();
         /**
          * 将表达式转换成字符串
          */
         public abstract String toString();
     }
     复制代码
     　　一个Constant对象代表一个布尔常量
     
     复制代码
     public class Constant extends Expression{
         
         private boolean value;
     
         public Constant(boolean value){
             this.value = value;
         }
         
         @Override
         public boolean equals(Object obj) {
             
             if(obj != null && obj instanceof Constant){
                 return this.value == ((Constant)obj).value;
             }
             return false;
         }
     
         @Override
         public int hashCode() {
             return this.toString().hashCode();
         }
     
         @Override
         public boolean interpret(Context ctx) {
             
             return value;
         }
     
         @Override
         public String toString() {
             return new Boolean(value).toString();
         }
         
     }
     复制代码
     　　一个Variable对象代表一个有名变量
     
     复制代码
     public class Variable extends Expression {
     
         private String name;
     
         public Variable(String name){
             this.name = name;
         }
         @Override
         public boolean equals(Object obj) {
             
             if(obj != null && obj instanceof Variable)
             {
                 return this.name.equals(
                         ((Variable)obj).name);
             }
             return false;
         }
     
         @Override
         public int hashCode() {
             return this.toString().hashCode();
         }
     
         @Override
         public String toString() {
             return name;
         }
     
         @Override
         public boolean interpret(Context ctx) {
             return ctx.lookup(this);
         }
     
     }
     复制代码
     　　代表逻辑“与”操作的And类，表示由两个布尔表达式通过逻辑“与”操作给出一个新的布尔表达式的操作
     
     复制代码
     public class And extends Expression {
     
         private Expression left,right;
         
         public And(Expression left , Expression right){
             this.left = left;
             this.right = right;
         }
         @Override
         public boolean equals(Object obj) {
             if(obj != null && obj instanceof And)
             {
                 return left.equals(((And)obj).left) &&
                     right.equals(((And)obj).right);
             }
             return false;
         }
     
         @Override
         public int hashCode() {
             return this.toString().hashCode();
         }
     
         @Override
         public boolean interpret(Context ctx) {
             
             return left.interpret(ctx) && right.interpret(ctx);
         }
     
         @Override
         public String toString() {
             return "(" + left.toString() + " AND " + right.toString() + ")";
         }
     
     }
     复制代码
     　　代表逻辑“或”操作的Or类，代表由两个布尔表达式通过逻辑“或”操作给出一个新的布尔表达式的操作
     
     复制代码
     public class Or extends Expression {
         private Expression left,right;
     
         public Or(Expression left , Expression right){
             this.left = left;
             this.right = right;
         }
         @Override
         public boolean equals(Object obj) {
             if(obj != null && obj instanceof Or)
             {
                 return this.left.equals(((Or)obj).left) && this.right.equals(((Or)obj).right);
             }
             return false;
         }
     
         @Override
         public int hashCode() {
             return this.toString().hashCode();
         }
     
         @Override
         public boolean interpret(Context ctx) {
             return left.interpret(ctx) || right.interpret(ctx);
         }
     
         @Override
         public String toString() {
             return "(" + left.toString() + " OR " + right.toString() + ")";
         }
     
     }
     复制代码
     　　代表逻辑“非”操作的Not类，代表由一个布尔表达式通过逻辑“非”操作给出一个新的布尔表达式的操作
     
     复制代码
     public class Not extends Expression {
     
         private Expression exp;
         
         public Not(Expression exp){
             this.exp = exp;
         }
         @Override
         public boolean equals(Object obj) {
             if(obj != null && obj instanceof Not)
             {
                 return exp.equals(
                         ((Not)obj).exp);
             }
             return false;
         }
     
         @Override
         public int hashCode() {
             return this.toString().hashCode();
         }
     
         @Override
         public boolean interpret(Context ctx) {
             return !exp.interpret(ctx);
         }
     
         @Override
         public String toString() {
             return "(Not " + exp.toString() + ")";
         }
     
     }
     复制代码
     　　环境(Context)类定义出从变量到布尔值的一个映射
     
     复制代码
     public class Context {
     
         private Map<Variable,Boolean> map = new HashMap<Variable,Boolean>();
         
         public void assign(Variable var , boolean value){
             map.put(var, new Boolean(value));
         }
         
         public boolean lookup(Variable var) throws IllegalArgumentException{
             Boolean value = map.get(var);
             if(value == null){
                 throw new IllegalArgumentException();
             }
             return value.booleanValue();
         }
     }
     复制代码
     　　客户端类
     
     复制代码
     public class Client {
     
         public static void main(String[] args) {
             Context ctx = new Context();
             Variable x = new Variable("x");
             Variable y = new Variable("y");
             Constant c = new Constant(true);
             ctx.assign(x, false);
             ctx.assign(y, true);
             
             Expression exp = new Or(new And(c,x) , new And(y,new Not(x)));
             System.out.println("x=" + x.interpret(ctx));
             System.out.println("y=" + y.interpret(ctx));
             System.out.println(exp.toString() + "=" + exp.interpret(ctx));
         }
     
     }