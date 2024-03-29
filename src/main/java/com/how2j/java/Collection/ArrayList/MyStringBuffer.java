package com.how2j.java.Collection.ArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class MyStringBuffer implements IStringBuffer {
    /**
     * The value is used for character storage.
     */
//    char[] value;
    ArrayList<Character> value;

    /**
     * The count is the number of characters used.
     */
    int count;

    /**
     * The maximum size of array to allocate (unless necessary).
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    public MyStringBuffer() {
        this(16);
    }

    public MyStringBuffer(int capacity) {
//        value = new char[capacity];
        value = new ArrayList<>();
    }

    public MyStringBuffer(String str) {
        this(16);
        this.append(str);
    }


    /**
     * 把str添加到本对象的字符序列中
     * @param str 待添加的String对象
     */
    @Override
    public void append(String str) {
        if ( str == null ) {
            appendNUll();
            return;
        }
        int len = str.length();
//        ensureCapacityInternal(count + len);
//        str.getChars(0, len, value, count);
        char[] temp = str.toCharArray();
        for(int i = 0; i < temp.length; i++) {
            value.add(temp[i]);
        }
        this.count += len;
    }

    private void appendNUll() {
        int c = this.count;
//        ensureCapacityInternal(c + 4);
        this.value.add(c++, 'n');
        this.value.add(c++, 'u');
        this.value.add(c++, 'l');
        this.value.add(c++, 'l');
        this.count += 4;
    }

    /**
     * ArrayList不用扩展
     */
//    private void ensureCapacityInternal(int minimumCapacity) {
//        if ( minimumCapacity - this.value.size() > 0 ) {
////            value = Arrays.copyOf(value,
////                    newCapacity(minimumCapacity));
//            //todo
//        }
//    }
//
//    /**
//     * 返回至少大于等于minimumCapacity的长度
//     * @param minimumCapacity value数组需要的最小容量
//     * @return value数组扩容之后的容量大小
//     */
//    private int newCapacity(int minimumCapacity) {
//        int newCapacity = (value.size() << 1) + 2; // value的长度左移一位，再加上2【左移是否可能产生负数？可能产生Overflow导致负数】
//        if ( newCapacity - minimumCapacity < 0 ) { // 考虑newCapacity小于0【due to overflow】，minimumCapacity大于0
//            newCapacity = minimumCapacity;
//        }
//        if ( newCapacity <= 0 ) return hugeCapacity(minimumCapacity); // overflow
//        if ( MAX_ARRAY_SIZE - newCapacity < 0 ) return hugeCapacity(minimumCapacity); // 考虑value数组的长度可能会overflow
//        return newCapacity;
//    }

    /**
     * If {@code minimumCapacity} is non positive due to numeric
     * overflow, this method throws {@code OutOfMemoryError}.
     * @param minimumCapacity value数组需要的最小容量
     * @return value数组扩容之后的容量大小
     */
    private int hugeCapacity(int minimumCapacity) {
        if ( Integer.MAX_VALUE - minimumCapacity < 0) {
            throw new OutOfMemoryError();
        }
        return Math.max(minimumCapacity, MAX_ARRAY_SIZE);
    }

    /**
     * 把字符c添加到本对象的字符序列中
     * @param c
     */
    @Override
    public void append(char c) {
        // 不用考虑c==null
//        ensureCapacityInternal(count+1);
        this.value.add(count, c); // 不要写成count++！！！！！
        this.count++;
    }

    /**
     * 先把pos至count这一段的内容往后移动1位，再往pos处插入字符b
     * @param pos 待插入的位置
     * @param b 待插入的字符
     */
    @Override
    public void insert(int pos, char b) {
//        ensureCapacityInternal(this.count + 1);
//        System.arraycopy(this.value, pos, this.value, pos + 1, count - pos); // 把这一段向后移动一位
        int dest = pos + 1;
        int src = pos;
        for (int i = count - pos; i > 0; i--) {
            value.add(dest++, value.get(src++));
        }
        this.value.add(pos, b);
        this.count += 1;
    }

    /**
     * 先把pos至count这一段的内容往后移动b的长度length，再往pos处插入字符b
     * @param pos 待插入的位置
     * @param b 待插入的字符
     */
    @Override
    public void insert(int pos, String b) {
        if ( (pos < 0) || (pos > this.count) ) {
            return;
        }
        if ( b == null ) {
            b = "null";
        }
        int len = b.length();
//        ensureCapacityInternal(this.count + len);
//        System.arraycopy(this.value, pos, value, pos + len, count - pos); // 把count-pos个元素向后移动len位
        //todo
        int dest = pos + len;
        int src = pos;
        for (int i = count - pos; i > 0; i--) {
            value.add(dest++, value.get(src++));
        }
//        b.getChars(0, len, value, pos);
        char[] temp = b.toCharArray();
        int start = pos; // 应该从start这个位置，开始复制
        for (char c : temp) {
            value.add(start++, c);
        }
        this.count += len;
    }

    /**
     * 删除从start开始的所有字符序列
     * @param start 开始删除的位置
     */
    @Override
    public void delete(int start) {
        delete(start, this.count - 1);
    }

    /**
     * 删除从start开始，到end结束的所有字符序列
     * @param start 开始删除的位置
     * @param end 删除终止的位置
     */
    @Override
    public void delete(int start, int end) {
        if (start < 0) throw new StringIndexOutOfBoundsException(start);
        if (end >= this.count) end = this.count - 1;
        if (start > end) throw new StringIndexOutOfBoundsException(); // 不会停止运行？
        int len = end - start;
        if (len > 0) {
//            System.arraycopy(value, start, value, end - len, len);
            // todo
            for (;start < end ;start++) {
                value.remove(start);
            }
            this.count -= len;
        }
    }

    /**
     * 反转整个MyStringBuffer中存储的元素顺序
     */
    @Override
    public void reverse() {
        int n = count - 1;
//        for (int j = (n-1) >> 1; j >= 0; j--) {
//            int k = n - j;
//            char cj = value[j];
//            char ck = value[k];
//            value[j] = ck;
//            value[k] = cj;
//        }
        for(int i = 0; i < count/2; i++) { // 反转只需要执行一半的数组长度
            char cj = value.get(i);
            char ck = value.get(n - i);
            value.set(i, ck);
            value.set(n - i, cj);
        }
    }

    /**
     * 返回MyStringBuffer中字符的个数
     * @return MyStringBuffer中字符的个数
     */
    @Override
    public int length() {
        return this.count;
    }

    @Override
    public String toString() {
//        return Arrays.toString(value);
//        char[] temp = Arrays.copyOfRange(value, 0, count);
//        return new String(temp);

//        return value.toString(); //输出格式有问题

        Iterator<Character> it = this.value.iterator();
        StringBuilder sb = new StringBuilder();
//        sb.append('[');
        for (;;) {
            char e = it.next();
            sb.append(e);
            if (! it.hasNext())
//                return sb.append(']').toString();
                return sb.toString();
//            sb.append(',').append(' ');
        }
    }

    public static void main(String[] args) {
        MyStringBuffer sb = new MyStringBuffer("there light");
        System.out.println(sb);
        sb.insert(0, "let ");
        System.out.println(sb);

        sb.insert(10, "be ");
        System.out.println(sb);
        sb.insert(0, "God Say:");
        System.out.println(sb);
        sb.append("!");
        System.out.println(sb);
        sb.append('?');
        System.out.println(sb);
        sb.reverse();
        System.out.println(sb);

        sb.delete(0,4);
        System.out.println(sb);
        sb.delete(4);
        System.out.println(sb);
    }
}
