package com.bt.ngoss.helper;

public class UserTypeHelper {

	/**
	 * 
	 * @param operatorId
	 * @return
	 */
	public String getUserTypeAccordinhToOperatorId(String operatorId) {
		if (operatorId.length() == 9) {
			return "BT user";
		}
		return "Non BT user";
	}
}
