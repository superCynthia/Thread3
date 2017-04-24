package whr.main;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Thread Specific Storage 模式 (线程特有存储)
 * 强随机数生成器
 * 
 * @author superCynthia
 *
 */
public class ThreadSpecificSecureRandom {

	// 单例模式，只有一个唯一实例
	public static final ThreadSpecificSecureRandom INSTANCE = new ThreadSpecificSecureRandom();

	// SECURE_RANDOM:模式角色为ThreadSpecificStorage.TSObjectProxy
	// SecureRandom：模式角色为ThreadSpecificStorage.TSObject
	private static final ThreadLocal<SecureRandom> SECURE_RANDOM = new ThreadLocal<SecureRandom>() {

		@Override
		protected SecureRandom initialValue() {
			SecureRandom srnd;
			try {
				// 强随机数生成器，待考
				srnd = SecureRandom.getInstance("SHA1PRNG");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				srnd = new SecureRandom();
			}
			return srnd;
		}
	};

	// 私有构造器
	private ThreadSpecificSecureRandom() {
	}

	public int nextInt(int upperBound) {
		SecureRandom secureRnd = SECURE_RANDOM.get();
		return secureRnd.nextInt(upperBound);
	}

	public void setSeed(long seed) {
		SecureRandom secureRnd = SECURE_RANDOM.get();
		secureRnd.setSeed(seed);
	}
}
