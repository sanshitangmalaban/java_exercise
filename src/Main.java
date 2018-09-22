import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

//        //corePoolSize 表示线程数到达多少后会往缓存队列写入
//        //最后参数为指定大小的缓存队列
//        //队列满了之后，线程池会创建新的线程，maximumPoolSize为最大的线程数量
//        //当队列已满，且线程数量达到maximumPoolSize时，会报出拒绝错误
//        ThreadPoolExecutor threadPoolexecutor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
//        for (int i = 0; i < 16; i++) {
//            MyTask myTask = new MyTask(i);
//            threadPoolexecutor.execute(myTask);
//
//            System.out.println("线程池中线程数目：" + threadPoolexecutor.getPoolSize() + "，队列中等待执行的任务数目：" +
//                    threadPoolexecutor.getQueue().size() + "，已执行玩别的任务数目：" + threadPoolexecutor.getCompletedTaskCount());
//        }
//
//        threadPoolexecutor.shutdown();
        String genHMAC = genHMAC("111", "2222");
        System.out.println(genHMAC.length()); //28
        System.out.println(genHMAC);  // O5fviq3DGCB5NrHcl/JP6+xxF6s=
    }
    public static String genHMAC(String data, String key) {
        byte[] result = null;
        try {
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKeySpec signinKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            //生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            //用给定密钥初始化 Mac 对象
            mac.init(signinKey);
            //完成 Mac 操作
            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = Base64.encodeBase64(rawHmac);

        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        } catch (InvalidKeyException e) {
            System.err.println(e.getMessage());
        }
        if (null != result) {
            return new String(result);
        } else {
            return null;
        }
    }

}

// 实现Runnable接口
class MyTask implements Runnable {
    private int taskNum;

    public MyTask(int num) {
        this.taskNum = num;
    }

    @Override
    public void run() {
        System.out.println("正在执行task "+taskNum);
        try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task "+taskNum+"执行完毕");
    }
}

