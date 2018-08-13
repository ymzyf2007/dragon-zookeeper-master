package com.dragon.zk.scene;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZookeeperUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(ZookeeperUtils.class);
	
	public static String getZkPath(String rootNode, String key) {
		if(!StringUtils.isEmpty(rootNode)) {
			if(key.startsWith("/")) {
				key = key.substring(1);
			}
			if(rootNode.endsWith("/")) {
				return rootNode + key;
			}
			
			return rootNode + "/" + key;
		}
		
		return key;
	}
	
	public static void mkPaths(ZkClient client, String path) {
		String[] subs = path.split("\\/");
		if(subs.length < 2) {
			return;
		}
		String curPath = "";
		for(int i = 0; i < subs.length; i++) {
			curPath = curPath + "/" + subs[i];
			if(!client.exists(curPath)) {
				if(logger.isDebugEnabled()) {
					logger.debug("Trying to create zk node: " + curPath);
				}
				client.createPersistent(curPath);
				if(logger.isDebugEnabled()) {
					logger.debug("Zk node created successfully: " + curPath);
				}
			}
		}
	}
	
	public static String formatAsMonthDate(Date requestTime) {
		return new SimpleDateFormat("MMdd").format(requestTime);
	}

}