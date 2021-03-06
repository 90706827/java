

package com.example.demo.book2.mtpattern.ch12.ms.example.testdatagen;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class LocationRequestFactory implements RequestFactory {
	private final AtomicInteger seq=new AtomicInteger(0);
	@Override
	public SimulatedRequest newRequest() {
		// TODO Auto-generated method stub
		return new SimulatedRequest(){

			@Override
      public void printLogs(Logger logger) {
				LogEntry entry=new LogEntry();
	      entry.timeStamp=System.currentTimeMillis();
	      entry.recordType="request";
	      
	      entry.interfaceType="REST";
	      entry.interfaceName="Location";
	      entry.operationName="getLocation";
	      entry.srcDevice="OSG";
	      entry.dstDevice="ESB";
	      DecimalFormat df=new DecimalFormat("0000000");
	      
	      int traceId=seq.getAndIncrement();
	      int originalTId=traceId;
	      
	      entry.traceId="0020"+df.format(traceId);
	      
	      logger.printLog(entry);
	      
	      Random rnd = new Random();
	      entry.timeStamp+=rnd.nextInt(20);
	      entry.recordType="request";
	      entry.operationName="getLocation";
	      entry.srcDevice="ESB";
	      entry.dstDevice="NIG";
	      traceId=traceId+1;
	      entry.traceId="0021"+df.format(traceId);
	      logger.printLog(entry);
	      
	      
	      rnd = new Random();
	      entry.timeStamp+=rnd.nextInt(100);
	      entry.recordType="response";
	      entry.operationName="getLocationRsp";
	      entry.srcDevice="NIG";
	      entry.dstDevice="ESB";
	      traceId=traceId+3;
	      entry.traceId="0021"+df.format(traceId);
	      logger.printLog(entry);
	      
	      rnd = new Random();
	      entry.timeStamp+=rnd.nextInt(150);
	      entry.recordType="response";
	      entry.operationName="getLocationRsp";
	      entry.srcDevice="ESB";
	      entry.dstDevice="OSG";
	      entry.traceId="0020"+df.format(originalTId+2);
	      logger.printLog(entry);
	      
      }
			
		};
	}

}
