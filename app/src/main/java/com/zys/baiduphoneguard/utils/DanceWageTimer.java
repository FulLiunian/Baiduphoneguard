package com.zys.baiduphoneguard.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.text.DecimalFormat;

public class DanceWageTimer extends CountDownTimer {

	public static final int INTERVAL_ONE = 20;
	public static final int INTERVAL_TWO = 40;

	private TextView textView;
	private float totalWage;
	private int startNum = 0;//从多少开始累加
	private int increased;//每次加多少
	private int decimals;
	private int decimalFlag = 0;//记录小数部分的累加
	private long totalExecuteTime;
	private long interval;
	public DanceWageTimer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}


	//							总共执行的时间 				间隔 				文本控件 		总分数
	public DanceWageTimer(long millisInFuture, long countDownInterval, TextView textView, float totalWage) {
		super(millisInFuture, countDownInterval);
		this.textView = textView;
		this.totalWage = totalWage;
		this.totalExecuteTime = millisInFuture;
		this.interval = countDownInterval;
		startNum = DanceWageTimer.getStartNum(totalWage);
		decimals = (int) ((totalWage - getIntegerOfWage(totalWage)) * 100);
		increased = DanceWageTimer.getIncreased(startNum);
	}

	@Override
	public void onFinish() {
		DecimalFormat decFormat = new DecimalFormat("##0");
		String result = decFormat.format(totalWage);
		textView.setText(result);
	}

	@Override
	public void onTick(long arg0) {
		startNum += increased;
		if (decimalFlag < decimals) {
			if (totalExecuteTime / interval < decimals) {
				decimalFlag += 2;
			} else {
				decimalFlag++;
			}
		}
		textView.setText(startNum+"");
	}

	/**
	 * @Title getTotalExecuteTime
	 * @Description 得到总共执行的时间
	 * @param totalWage
	 * @return
	 */
	public static int getTotalExecuteTime(float totalWage, int interval) {
		int wage = getIntegerOfWage(totalWage);
		int startNum = getStartNum(totalWage);
		int increased = getIncreased(startNum);
		int result = (wage - startNum) / increased * interval;
		return result;
	}

	/**
	 * @Title getStartNum
	 * @Description 得到从多少开始累加
	 * @param totalWage
	 * @return
	 */
	public static int getStartNum(float totalWage) {
		int wage = getIntegerOfWage(totalWage);
		if (wage / 10000 >= 1) {
			return 10000;
		} else if (wage / 1000 >= 1) {
			return 1000;
		} else if (wage / 100 >= 1) {
			return 100;
		} else if (wage / 10 >= 1) {
			return 10;
		} else {
			return 0;
		}
	}

	/**
	 * @Title getIncreased
	 * @Description 得到每次加多少
	 * @param start
	 * @return
	 */
	private static int getIncreased(int start) {
		int increased = 0;
		if (start >= 10000) {
			increased = 1299;
		} else if (start >= 1000) {
			increased = 99;
		} else if (start >= 100) {
			increased = 7;
		} else if (start >= 10) {
			increased = 1;
		} else {
			increased = 1;
		}
		return increased;
	}

	public static int getIntegerOfWage(float totalWage) {
		return (int) totalWage;
	}

}
