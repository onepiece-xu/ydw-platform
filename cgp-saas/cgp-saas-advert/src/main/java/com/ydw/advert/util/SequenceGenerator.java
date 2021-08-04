package com.ydw.advert.util;
import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 分布式id生成器，生成64位ID
 * 以毫秒为单位的时期精度 - 42位。可以使用42位表示的最大时间戳是2^42 - 1，或4398046511103，这就是出来Wednesday, May 15, 2109 7:35:11.103 AM。
 * 机器ID - 10位。这给了我们1024台机器。
 * 每台机器的本地计数器 - 12位。计数器的最大值将是4095.之后，它会翻转并从0再次开始。
 * @author xulh
 *
 */
public final class SequenceGenerator {
	/**
	 * 本地计数器
	 */
    private static final AtomicInteger counter = new AtomicInteger(new SecureRandom().nextInt());

    private static final int TOTAL_BITS = 64;
    private static final int EPOCH_BITS = 42;
    private static final int MACHINE_ID_BITS = 10;

    private static final int MACHINE_ID;
    private static final int LOWER_ORDER_TEN_BITS = 0x3FF;//11 1111 1111 --10位
    private static final int LOWER_ORDER_TWELVE_BITS = 0xFFF;//1111 1111 1111 --12位

    public static String sequence() {
        Long curMs = Instant.now().toEpochMilli();
        Long id = curMs << (TOTAL_BITS - EPOCH_BITS);
        id |= (MACHINE_ID << (TOTAL_BITS - EPOCH_BITS - MACHINE_ID_BITS));
        id |= (getNextCounter() & LOWER_ORDER_TWELVE_BITS);
        return id.toString();
    }

    private static int getNextCounter() {
        return counter.getAndIncrement();
    }

    static {
        MACHINE_ID = createMachineId();
    }

    private static int createMachineId() {
        int machineId;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    for(int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X", mac[i]));
                    }
                }
            }
            machineId = sb.toString().hashCode();
        } catch (Exception ex) {
            machineId = (new SecureRandom().nextInt());
        }
        machineId = machineId & LOWER_ORDER_TEN_BITS;
        return machineId;
    }
    
    public static void main(String[] args) {
		System.out.println(sequence());
	}
}