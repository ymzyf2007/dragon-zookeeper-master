package com.dragon.zk;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import com.dragon.zk.listener.DataMonitor;
import com.dragon.zk.listener.DataMonitorListener;

public class Executor implements Watcher, Runnable, DataMonitorListener {
	
	private String znode;
	private DataMonitor dataMonitor;
	private ZooKeeper zk;
	private String[] exec;
	private Process child;
	
	public Executor(String hostPort, String znode, String[] exec) throws IOException {
		this.exec = exec;
		zk = new ZooKeeper(hostPort, 3000, this);
		dataMonitor = new DataMonitor(zk, znode, null, this);
	}

	@Override
	public void exists(byte[] data) {
	}

	@Override
	public void closing(int rc) {
	}

	@Override
	public void run() {
		try {
            synchronized (this) {
                while (!dataMonitor.isDead()) {
                    System.out.println("===========EXECUTOR START TO WAIT===========");
                    wait();
                    System.out.println("===========EXECUTOR STOP WAIT===========");
                }
            }
        } catch (InterruptedException e) {
        }
	}

	@Override
	public void process(WatchedEvent event) {
	}
	
	public static void main(String[] args) {
		args = new String[] { "192.168.1.209:2181", "/z_test" };
		String hostPort = args[0];
		String znode = args[1];
		String[] exec = new String[] { "date" };
		try {
            new Executor(hostPort, znode, exec).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}