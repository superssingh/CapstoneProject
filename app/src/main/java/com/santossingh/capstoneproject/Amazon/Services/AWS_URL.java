package com.santossingh.capstoneproject.Amazon.Services;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by santoshsingh on 17/02/17.
 */

public class AWS_URL {

    // Your AWS Access Key ID, as taken from the AWS Your Account page.
    private static final String AWS_ACCESS_KEY_ID = "AKIAILMCDWNHZ6OBN2QQ";
    // Your AWS Secret Key corresponding to the above ID, as taken from the AWS
    private static final String AWS_SECRET_KEY = "qWESYwVUgn2IcdHOmZZwK1xU3VcK98TxcRlQsEV2";
    //Use the end-point according to the region you are interested in.
    private static final String ENDPOINT = "webservices.amazon.in";

    public String getURLByCategory(String keyword) {

        SignedRequestsHelper helper;

        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        String requestUrl = null;

        Map<String, String> params = new HashMap<String, String>();

        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemSearch");
        params.put("AWSAccessKeyId", "AKIAILMCDWNHZ6OBN2QQ");
        params.put("AssociateTag", "superssingh-21");
        params.put("SearchIndex", "Books");
        params.put("ResponseGroup", "EditorialReview,Images,ItemAttributes,Reviews");
        params.put("Keywords", keyword);

        requestUrl = helper.sign(params);
        return requestUrl;
    }

}

