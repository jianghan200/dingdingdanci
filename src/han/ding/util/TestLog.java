package han.ding.util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 

 

public class TestLog
{
    private static Log log = LogFactory.getLog(TestLog.class);
 

    public void test()
    {
        log.debug("111");
        log.info("222");
        log.warn("333");
        log.error("444");
        log.fatal("555");
    }
 

    public static void main(String[] args)
    {
        TestLog testLog = new TestLog();
        testLog.test();
    }
}
