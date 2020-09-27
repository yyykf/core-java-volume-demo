package cn.ykf.model;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-27
 */
public class Outer {

    private String name;

    // 这里会有一个synthetic桥方法，用于给内部类提供访问字段的途径
    // static String access$000(Outer outer);

    public Inner newInner() {
        // 直接new Inner() 就好了
        return this.new Inner();
    }

    private class Inner {
        // 编译后这里会有一个synthetic字段，就是外部类对象的引用
        // final Outer this$0;

        // 只能定义static常量，不能定义static变量
        // 因为static成员和方法是随着类加载而存在的，而非static内部类只有在new的时候才会被加载，new外部类的时候是不会加载内部类的
        // 所以如果可以直接 Outer.Inner.静态字段/方法，那么说明内部类已经被加载了，这就出现矛盾了
        // 而static 常量是保存在常量池中的，不需要
        private static final int ahe = 10;

        // 内部类不允许定义static方法
        // private static void main(){
        //     System.out.println("内部类的静态方法不被允许");
        // }

        public void test() {
            // 这里其实就是通过 Outer.access$000(this$0) 来获取外部类的字段值的
            System.out.println(name);
            // 也可以显式使用外部类引用表达式，编译后的指令完全等价
            System.out.println(Outer.this.name);
        }
    }

    public static void main(String[] args) {
        // 非static内部类必须先实例化外部类再实例化内部类
        Inner inner = new Outer().new Inner();
        // 'cn.ykf.model.Outer.this' cannot be referenced from a static context Alt+Shift+Enter Alt+Enter
        // Inner inner1 = new Inner();
    }
}
