package com.zlwh.yzh.api.common;

public enum MonthEnum {

		
		SMALL(1,"xb_mons_"),
		MIDDL(2,"zb_mons_"),
		LARGE(3,"db_mons_"),
		
		// 接口调用成功
		SUCCESS(0,"成功"),
		UNSUCCESS(1,"操作失败"),
		
		CARD_BOND_SUCCES(9108,"授权卡绑定失败");
		
		private final int value;
		private final String reasonPhrase;

		private MonthEnum(Integer value, String reasonPhrase) {
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
			return reasonPhrase;
		}


}
