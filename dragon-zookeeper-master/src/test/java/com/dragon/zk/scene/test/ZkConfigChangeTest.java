package com.dragon.zk.scene.test;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dragon.zk.scene.ConfigChangeSubscriber;
import com.dragon.zk.scene.ZookeeperUtils;

import junit.framework.TestCase;

public class ZkConfigChangeTest extends TestCase {
	
	private ZkClient zkClient;
	private ConfigChangeSubscriber zkConfig;

	@Override
	protected void setUp() throws Exception {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("application-test-context.xml");
		this.zkClient = ((ZkClient) ctx.getBean("zkClient"));
		this.zkConfig = ((ConfigChangeSubscriber) ctx.getBean("configChangeSubscriber"));
		this.zkClient.deleteRecursive("/zkSample");
		if(!this.zkClient.exists("/zkSample/conf/test1.properties")) {
			ZookeeperUtils.mkPaths(this.zkClient, "/zkSample/conf/test1.properties");
		}
		if(!this.zkClient.exists("/zkSample/conf/test2.properties")) {
			ZookeeperUtils.mkPaths(this.zkClient, "/zkSample/conf/test2.properties");
		}
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testSubscribe() {
		
	}

}