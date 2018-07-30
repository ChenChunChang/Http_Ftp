package cn.admin.core;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
public class SchedulerManager {
	private static Scheduler scheduler;
	public synchronized static Scheduler get(){
		if (scheduler==null) {
			try {
				scheduler = StdSchedulerFactory.getDefaultScheduler();
	            scheduler.start();
			} catch (SchedulerException se) {
	            se.printStackTrace();
	        }
		}
		
		return scheduler;
	}
}
