package com.ebupt.justholdon.server.image;

import java.util.ArrayList;
import java.util.List;

import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.apache.log4j.Logger;

public class IosPusher {
	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * 
	 * 这是一个比较简单的推送方法，
	 * 
	 * apple的推送方法
	 * 
	 * @param tokens
	 *            iphone手机获取的token
	 * 
	 * @param path
	 *            这里是一个.p12格式的文件路径，需要去apple官网申请一个
	 * 
	 * @param password
	 *            p12的密码 此处注意导出的证书密码不能为空因为空密码会报错
	 * 
	 * @param message
	 *            推送消息的内容
	 * 
	 * @param count
	 *            应用图标上小红圈上的数值
	 * 
	 * @param sendCount
	 *            单发还是群发 true：单发 false：群发
	 */

	public void sendpush(List<String> tokens, String path, String password,
			String message, Integer count, boolean sendCount,boolean production) {
		try {
			PushNotificationPayload payLoad = PushNotificationPayload
					.fromJSON(message);
			// payLoad.addAlert(message); // 消息内容
			payLoad.addBadge(count); // iphone应用图标上小红圈上的数值
			payLoad.addSound("default"); // 铃音 默认
			PushNotificationManager pushManager = new PushNotificationManager();
			// true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
			pushManager
					.initializeConnection(new AppleNotificationServerBasicImpl(
							path, password, production));
			List<PushedNotification> notifications = new ArrayList<PushedNotification>();
			// 发送push消息
			if (sendCount) {
				log.debug("--------------------------apple 推送 单-------");
				Device device = new BasicDevice();
				device.setToken(tokens.get(0));
				PushedNotification notification = pushManager.sendNotification(
						device, payLoad, true);
				notifications.add(notification);
			} else {
				log.debug("--------------------------apple 推送 群-------");
				List<Device> device = new ArrayList<Device>();
				for (String token : tokens) {
					device.add(new BasicDevice(token));
				}
				notifications = pushManager.sendNotifications(payLoad, device);
			}
			List<PushedNotification> failedNotifications = PushedNotification
					.findFailedNotifications(notifications);
			List<PushedNotification> successfulNotifications = PushedNotification
					.findSuccessfulNotifications(notifications);
			int failed = failedNotifications.size();
			int successful = successfulNotifications.size();
			if (successful > 0 && failed == 0) {
				log.debug("-----All notifications pushed 成功 ("
						+ successfulNotifications.size() + "):");
			} else if (successful == 0 && failed > 0) {
				log.debug("-----All notifications 失败 ("
						+ failedNotifications.size() + "):");
			} else if (successful == 0 && failed == 0) {
				System.out
						.println("No notifications could be sent, probably because of a critical error");
			} else {
				log.debug("------Some notifications 失败 ("
						+ failedNotifications.size() + "):");
				log.debug("------Others 成功 (" + successfulNotifications.size()
						+ "):");
			}
			// pushManager.stopConnection();
		} catch (Exception e) {
			log.warn("Pusher Exception",e);
		}

	}
	public static void main(String args[])
	{
		IosPusher pusher = new IosPusher();
		List<String> tokens = new ArrayList<String>();
		tokens.add("6c102c5c9e7dfed9bcb19355ff02ba44cf2efed280ddc9fdd4a7f555709bd53e");
		for(int i = 0 ;i<10;i++)
		pusher.sendpush(tokens, "/Users/xiaoqinliu/Downloads/Key.p12", "1082325588", "{'aps':{'alert':'iphone推送测试 www.baidu.com'}}", 10, true,false);
	}
}
