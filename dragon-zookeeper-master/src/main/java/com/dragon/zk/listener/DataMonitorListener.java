package com.dragon.zk.listener;

/**
 * 数据监听
 *
 */
public interface DataMonitorListener {
	
	void exists(byte[] data);
	
	void closing(int rc);

}