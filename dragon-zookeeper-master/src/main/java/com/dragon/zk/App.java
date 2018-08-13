package com.dragon.zk;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class App {
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		// 创建一个Zookeeper实例，第一个参数为目标服务器地址和端口，第二个参数为Session超时时间，第三个为节点变化时的回调方法
		ZooKeeper zk = new ZooKeeper("192.168.1.209:2181", 3000, new Watcher() {
			// 监控所有被触发的事件
			@Override
			public void process(WatchedEvent event) {
				System.out.println("process:" + event);
			}
		});
		// 创建一个节点 参数说明	1、目录名称 2、目录文本信息  3、文件夹权限，Ids.OPEN_ACL_UNSAFE表示所有权限 4、目录类型，CreateMode.EPHEMERAL_SEQUENTIAL表示创建临时目录，session断开连接则目录自动删除
//		zk.create("/z_test", "mydata".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		Stat e = zk.exists("/z_test", null);
		System.out.println("exists " + e);
		
		byte[] bs = zk.getData("/z_test", new Watcher() {
			// 监控所有被触发的事件
			@Override
			public void process(WatchedEvent event) {
				System.out.println("process111:" + event);
			}
		}, e);
		System.out.println(new String(bs));
		
		
//		zk.setData("/z_test", "Data of node 3".getBytes(), -1);
	}

}