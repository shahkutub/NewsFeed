package com.teamalx.Utils;

import android.annotation.SuppressLint;

import java.util.Vector;

@SuppressLint("DefaultLocale")
public class AllURL {

	private static String getcommonURLWithParamAndAction(String action,
														 Vector<KeyValue> params) {

		if (params == null || params.size() == 0) {
			return BaseURL.HTTP + action;
		} else {
			String pString = "";

			for (final KeyValue obj : params) {

				pString += obj.getKey().trim() + "=" + obj.getValue().trim()
						+ "&";
			}

			if (pString.endsWith("&")) {
				pString = pString.subSequence(0, pString.length() - 1)
						.toString();
			}

			return BaseURL.HTTP + action + "?" + UrlUtils.encode(pString);
		}
	}




	public static String newsUrls(String limit) {
		final Vector<KeyValue> temp = new Vector();
		temp.add(new KeyValue("limit",limit));
		return getcommonURLWithParamAndAction("get_news_json", temp);
	}

	public static String newsDetailsUrls(String id) {
		final Vector<KeyValue> temp = new Vector();
		temp.add(new KeyValue("id",id));
		return getcommonURLWithParamAndAction("get_news_detail_json", temp);

	}

	public static String jelaProsasokDataUrl() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("jayla_prosasok", temp);

	}

	public static String otiriktojelaProsasokDataUrl() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("otirikto_jayla_prosasok", temp);
	}


	public static String contractorRatingUrl() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("review", temp);

	}

	public static String postRegisterUrl() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("register", temp);

	}



	public static String postLoginUrl() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("login", temp);

	}

	public static String phoneNumberUri() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("add-contact", temp);

	}

	public static String forgetPosswordUrl() {
		final Vector<KeyValue> temp = new Vector();

		return getcommonURLWithParamAndAction("reminder", temp);

	}

	public static String updateProfileUrl() {
		final Vector<KeyValue> temp = new Vector();
		//temp.add(new KeyValue("api_token",api_token));
		return getcommonURLWithParamAndAction("profile", temp);

	}

	public static String updatePass() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("updatePassword", temp);

	}

	public static String postSmsValidation() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("sms", temp);

	}

	public static String getCategory() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("categories", temp);

	}
	public static String postOrder() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("orders", temp);

	}


	public static String getChatListUrl() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("conversation", temp);

	}
	public static String sentMessagetUrl() {
		final Vector<KeyValue> temp = new Vector();
		;
		return getcommonURLWithParamAndAction("sendMessage", temp);

	}

	public static String orderHistoryUrl() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("getOrderHistory", temp);

	}

	public static String cancelOrderUrl() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("cancelOrder", temp);

	}

	public static String addPaymentDetailsUrl() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("addCard", temp);

	}


	public static String addPaypalOAuthTokenUrl() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("add-paypal", temp);

	}

	public static String getSettingsUrl() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("get-settings", temp);

	}
	public static String UpdateSettingsUrl() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("settings", temp);

	}
	public static String getDeleteAccountUrl() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("delete-account", temp);

	}

	public static String getInterestListUrl() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("interested-list", temp);

	}
	public static String getEditProfileUrl(String  token) {
		final Vector<KeyValue> temp = new Vector();
		temp.add(new KeyValue("token",token));
		return getcommonURLWithParamAndAction("edit-profile", temp);

	}

	public static String getMessageList(String  token) {
		final Vector<KeyValue> temp = new Vector();
		temp.add(new KeyValue("token",token));
		return getcommonURLWithParamAndAction("msg-list", temp);

	}

	public static String getPromoCodet(String  api_token) {
		final Vector<KeyValue> temp = new Vector();
		temp.add(new KeyValue("api_token",api_token));
		return getcommonURLWithParamAndAction("get-promo", temp);

	}

	public static String addPromoCode() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("promo-add", temp);

	}

	public static String resendSmsCode() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("resend", temp);

	}


}