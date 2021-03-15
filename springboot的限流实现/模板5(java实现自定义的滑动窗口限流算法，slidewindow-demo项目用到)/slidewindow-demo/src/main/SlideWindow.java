package main;

import com.sun.org.apache.xpath.internal.Arg;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @ClassName : SlideWindow
 * @Description: 滑动时间窗口限流工具,本限流工具只适用于单机版，如果想要做全局限流，可以按本程序的思想，用redis的List结构去实现 *
 * @Author: mzl
 * @CreateDate: 2020/12/19 14:12
 * @Version: 1.0
 */
public class SlideWindow {

    // 队列id和队列的映射关系，队列里面存储的是每一次通过时候的时间戳，这样可以使得程序里有多个限流队列
    private volatile static Map<String, List<Long>> MAP = new ConcurrentHashMap<>();   //线程并发的HashMap，性能和效率大大提高

    public SlideWindow(){

    }

  /**
   * 滑动时间窗口限流算法, 在指定时间窗口，指定限制次数内，是否允许通过
   * @param listId   队列id
   * @param count   限制次数
   * @param timeWindow  时间窗口大小
   * @return
   */
  public static synchronized boolean isGo(String listId, int count, long timeWindow) {
      //获取当前时间戳
      long nowTime = System.currentTimeMillis();   //时间戳默认单位是毫秒
      //根据队列id取出对应的限流队列，若没有该队列则创建，computeIfAbsent（键， List值），list扩容，动态线性表（模拟队列用）
      List<Long> list = MAP.computeIfAbsent(listId, k -> new ArrayList<>());  //computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)
      //如果队列来没满，就允许通过，并添加当前时间戳到队列开始的位置
      if (list.size() < count){
          list.add(0, nowTime);  //每次添加元素到线性表的起始位置，即0下标，然后其他后面的元素逐一动态向后移动一个位置，方便，性能大大提高
          return true;
      }

      //队列已满（达到限制次数), 则获取队列中最早添加的时间戳
      Long farTime = list.get(count - 1);
      //距离时间戳 = 用当前的时间戳 - 最早的时间戳
      Long gapTime = nowTime - farTime;
      if (gapTime <= timeWindow){
          //距离时间小于等于滑动窗口时间，即在timeWindow之内，不允许通过
          return false;
      }else {
          //距离时间大于滑动窗口时间，及在timeWindow之外，允许通过
          //移除限流队列中的所有的元素
//          list.remove(count - 1);  //移除从队列的最后一个元素，即队列中最早进来的元素，count相当于队列的size
          list.clear();   //异常元素中的所有元素，重新新的一轮滑动窗口计时（这才是真正的逻辑，每10秒内只能允许3次请求，10秒后请求的次数清零，新的一轮10秒，重新计算）
          list.add(0, nowTime);   //添加新的元素到队列的开头，重新新的一轮窗口滑动操作
          return true;
      }

  }

    public static void main(String[] args) throws InterruptedException {
      while (true){  //无限循环
          //任意10秒内，只允许通过3次
          System.out.println(LocalTime.now().toString() + " -----> " + SlideWindow.isGo("ListId", 3, 10000L));
          //线程睡眠0-10秒
          Thread.sleep(1000 * new Random().nextInt(10));
      }
    }


}
