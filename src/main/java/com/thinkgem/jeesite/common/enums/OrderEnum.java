package com.thinkgem.jeesite.common.enums;

public enum OrderEnum {
	
		XB_ONE(30,"小班一个月课程"),
		XB_THREE(90,"小班三个月课程"),
		XB_SIX(180,"小班六个月课程"),
		XB_TWE(360,"小班十二个月课程");
		
		private final int value;
		private final String reasonPhrase;

		private OrderEnum(Integer value, String reasonPhrase) {
			this.value = value;
			this.reasonPhrase = reasonPhrase;
		}
		

		/**
		 * Return the integer value of this status code.
		 */
		public Integer value() {
			return this.value;
		}

		/**
		 * Return the reason phrase of this status code.
		 */
		public String getReasonPhrase() {
			return reasonPhrase;
		}


		/**
		 * Return a string representation of this status code.
		 */
		@Override
		public String toString() {
			return Integer.toString(value);
		}

	}
