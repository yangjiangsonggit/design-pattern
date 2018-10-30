###访问者模式

一、定义

访问者模式：表示一个作用于某对象结构中的各元素的操作。它使你可以在不改变各元素的类的前提下定义作用与这些元素的新操作。

解释：一个对象，如果它的元素是固定的，那么可以使用访问者模式定义它们的操作，使得操作可以自由的增加或者减少而不影响系统的其它部分。

 

二、UML类图及基本代码



基本代码：

复制代码
abstract class Visitor
    {
        public abstract void VisitConcreteElementA(ConcreteElementA concreteElementA);
        public abstract void VisitConcreteElementB(ConcreteElementB concreteElementB);
    }

    class ConcreteVisitor1 : Visitor
    {
        public override void VisitConcreteElementA(ConcreteElementA concreteElementA)
        {
            Console.WriteLine("{0}被{1}访问", concreteElementA.GetType().Name, this.GetType().Name);
        }

        public override void VisitConcreteElementB(ConcreteElementB concreteElementB)
        {
            Console.WriteLine("{0}被{1}访问", concreteElementB.GetType().Name, this.GetType().Name);
        }
    }

    class ConcreteVisitor2: Visitor
    {
        public override void VisitConcreteElementA(ConcreteElementA concreteElementA)
        {
            Console.WriteLine("{0}被{1}访问", concreteElementA.GetType().Name, this.GetType().Name);
        }

        public override void VisitConcreteElementB(ConcreteElementB concreteElementB)
        {
            Console.WriteLine("{0}被{1}访问", concreteElementB.GetType().Name, this.GetType().Name);
        }
    }

    abstract class Element
    {
        public abstract void Accept(Visitor visitor);
    }

    class ConcreteElementA : Element
    {
        public override void Accept(Visitor visitor)
        {
            visitor.VisitConcreteElementA(this);
        }

        public void OperationA()
        { }
    }

    class ConcreteElementB : Element
    {
        public override void Accept(Visitor visitor)
        {
            visitor.VisitConcreteElementB(this);
        }

        public void OperationB()
        { }
    }

    class ObjectStructure
    {
        private IList<Element> elements = new List<Element>();

        public void Attach(Element element)
        {
            elements.Add(element);
        }

        public void Detach(Element element)
        {
            elements.Remove(element);
        }

        public void Accept(Visitor visitor)
        {
            foreach (Element e in elements)
            {
                e.Accept(visitor);
            }
        }
    }
复制代码
 

客户端调用及其允许结果：

 View Code


 

三、实例说明

　　介绍男人和女人之间的区别。现在元素包括男人和女人是固定的，但是它们的区别即操作不多种多样的，例如：男人成功时，背后多半有一个伟大的女人，女人成功时，背后大多有一个不成功的男人；男人失败时，闷头喝酒，谁也不用劝，女人失败时，眼泪汪汪，谁也劝不了，等等。代码说明：

 

首先编写抽象人类和具体的对象男人、女人：

复制代码
abstract class Person
    {
        public abstract void Accept(Action action);
    }

    class Man : Person
    {
        public override void Accept(Action action)
        {
            action.GetManConclusion(this);
        }
    }

    class Woman : Person
    {
        public override void Accept(Action action)
        {
            action.GetWomanConclusion(this);
        }
    }
复制代码
 

然后编写元素的抽象操作类和具体的操作行为：

复制代码
abstract class Action
    {
        public abstract void GetManConclusion(Man concreteElementA);
        public abstract void GetWomanConclusion(Woman concreteElementB);
    }

    class Success : Action
    {
        public override void GetManConclusion(Man concreteElementA)
        {
            Console.WriteLine("{0}{1}时，背后多半有一个伟大的女人", concreteElementA.GetType().Name, this.GetType().Name);
        }

        public override void GetWomanConclusion(Woman concreteElementB)
        {
            Console.WriteLine("{0}{1}时，背后大多有一个不成功的男人", concreteElementB.GetType().Name, this.GetType().Name);
        }
    }

    class Fail : Action
    {
        public override void GetManConclusion(Man concreteElementA)
        {
            Console.WriteLine("{0}{1}时，闷头喝酒，谁也不用劝", concreteElementA.GetType().Name, this.GetType().Name);
        }

        public override void GetWomanConclusion(Woman concreteElementB)
        {
            Console.WriteLine("{0}{1}时，眼泪汪汪，谁也劝不了", concreteElementB.GetType().Name, this.GetType().Name);
        }
    }
复制代码
 

最后编写一个结构类，它枚举元素，提供一个高层的接口以允许访问者访问它的元素

复制代码
class ObjectStructure
    {
        private IList<Person> elements = new List<Person>();

        public void Attach(Person element)
        {
            elements.Add(element);
        }

        public void Detach(Person element)
        {
            elements.Remove(element);
        }

        public void Display(Action action)
        {
            foreach (Person e in elements)
            {
                e.Accept(action);
            }
        }
    }
复制代码
 

客户端调用及其结果：

复制代码
ObjectStructure os = new ObjectStructure();
            os.Attach(new Man());
            os.Attach(new Woman());

            Success s = new Success();
            os.Display(s);

            Fail f = new Fail();
            os.Display(f);
复制代码


 

四、优缺点及适用场景

优点：

访问者模式使得添加新的操作变得容易。如果一些操作依赖于一个复杂的结构对象的话，那么一般而言，添加新的操作会变得很复杂。而使用访问者模式，增加新的操作就意味着添加一个新的访问者类。因此，使得添加新的操作变得容易。

缺点：

增加新的元素类变得困难。每增加一个新的元素意味着要在抽象访问者角色中增加一个新的抽象操作，并在每一个具体访问者类中添加相应的具体操作。

 

适用场景：

说白了，就是能显示其优点回避其缺点的地方都可以适用。