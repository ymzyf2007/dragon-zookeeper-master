package com.dragon.zk.scene;

import java.util.List;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang.StringUtils;

/**
 * 订阅者实现类，当订阅到zk数据改变时，会触发ConfigChangeListener
 *
 */
public class ZkConfigChangeSubscriberImpl implements ConfigChangeSubscriber {
	
	private ZkClient zkClient;
	private String rootNode;
	
	public ZkConfigChangeSubscriberImpl(ZkClient zkClient, String rootNode) {
		super();
		this.zkClient = zkClient;
		this.rootNode = rootNode;
	}

	@Override
	public String readData(String key) {
		String path = ZookeeperUtils.getZkPath(this.rootNode, key);
		return (String) this.zkClient.readData(path);
	}

	@Override
	public void subscribe(String key, ConfigChangeListener listener) {
		String path = ZookeeperUtils.getZkPath(this.rootNode, key);
		if (!this.zkClient.exists(path)) {
			throw new RuntimeException("配置(" + path + ")不存在, 必须先定义配置才能监听配置的变化, 请检查配置的key是否正确, 如果确认配置key正确, 那么需要保证先使用配置发布命令发布配置! ");
		}
		this.zkClient.subscribeDataChanges(path, new DataListenerAdapter(listener));
	}

	@Override
	public List<String> listKeys() {
		return this.zkClient.getChildren(this.rootNode);
	}
	
	private String getKey(String path) {
		String key = path;
		if (!StringUtils.isEmpty(this.rootNode)) {
			key = path.replaceFirst(this.rootNode, "");
			if (key.startsWith("/")) {
				key = key.substring(1);
			}
		}
		return key;
	}
	
	/**
	 * 数据监听器适配类，当zk数据变化时，触发ConfigChangeListener
	 *
	 */
	private class DataListenerAdapter implements IZkDataListener {
		
		private ConfigChangeListener configListener;
		
		public DataListenerAdapter(ConfigChangeListener configListener) {
			this.configListener = configListener;
		}

		@Override
		public void handleDataChange(String dataPath, Object data) throws Exception {
			configListener.change(getKey(dataPath), data);
		}

		@Override
		public void handleDataDeleted(String dataPath) throws Exception {
		}
	}

}