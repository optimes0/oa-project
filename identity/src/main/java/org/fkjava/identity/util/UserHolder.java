package org.fkjava.identity.util;

import org.fkjava.identity.domain.User;

/***
 * 此工具是把User放入线程中方便其他模块使用
 * @author zero
 *
 */
public class UserHolder {

	private static final ThreadLocal<User> THREAD_LOCAL = new ThreadLocal<>();
	
	public static User get() {
		return THREAD_LOCAL.get();
	}
	
	public static void set(User user) {
		THREAD_LOCAL.set(user);
	}
	
	public static void remove() {
		THREAD_LOCAL.remove();
	}
}
