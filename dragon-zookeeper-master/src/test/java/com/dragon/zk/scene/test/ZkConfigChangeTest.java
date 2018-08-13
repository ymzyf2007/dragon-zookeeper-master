package com.dragon.zk.scene.test;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dragon.zk.scene.ConfigChangeSubscriber;

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
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	

}