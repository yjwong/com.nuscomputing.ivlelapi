package com.nuscomputing.ivlelapi;

/**
 * StopWatch class for measuring execution time.
 * @author yjwong
 */
class StopWatch {
	// {{{ properties
	
	long startTime = 0;
	long endTime = 0;
	long total = 0;
	boolean running = false;
	
	// }}}
	// {{{ methods
	
	StopWatch(){
		reset();
	}
	
	void reset(){
		startTime = 0;
		endTime = 0;
		total = 0;
		running = false;
	}
	
	void start(){
		startTime = System.nanoTime();
		running = true;
	}
	
	void stop(){
		endTime = System.nanoTime();
		total += (endTime - startTime);
		running = false;
	}
	
	float getTime(){
		float r = total;
		r /= 1000000000;
		return r;
	}
	
	// }}}
}
