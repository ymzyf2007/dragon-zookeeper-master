package com.dragon.zk.scene;

import java.util.List;

/**
 * 配置改变的订阅者，在每一个zk文件上订阅一个监听器
 *
 */
public interface ConfigChangeSubscriber {
	
	/**
	 * 获取子节点
	 * @param key
	 * @return
	 */
	public String readData(String key);
	
	/**
	 * 订阅
	 * @param key
	 * @param listener
	 */
	public void subscribe(String key, ConfigChangeListener listener);
	
	/**
	 * 获取ROOT所有的keys
	 * @return
	 */
	public List<String> listKeys();

}