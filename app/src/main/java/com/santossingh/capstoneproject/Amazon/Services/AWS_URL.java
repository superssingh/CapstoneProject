package com.santossingh.capstoneproject.Amazon.Services;

import java.util.HashMap;
import java.util.Map;

public class AWS_URL {

    // Your AWS Access Key ID, as taken from the AWS Your Account page.
    private static final String AWS_ACCESS_KEY_ID = "AKIAILMCDWNHZ6OBN2QQ";
    // Your AWS Secret Key corresponding to the above ID, as taken from the AWS
    private static final String AWS_SECRET_KEY = "qWESYwVUgn2IcdHOmZZwK1xU3VcK98TxcRlQsEV2";
    //Use the end-point according to the region you are interested in.
    private static final String ENDPOINT = "webservices.amazon.in";
    private static final String ASSOCIATE_TAG = "superssingh-21";
    private static final String RESPONSE_GROUP = "EditorialReview,Images,ItemAttributes,Reviews";


    public String getURLByCategory(String keyword) {

        SignedRequestsHelper helper;

        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        String requestUrl;

        Map<String, String> params = new HashMap<String, String>();

        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemSearch");
        params.put("AWSAccessKeyId", AWS_ACCESS_KEY_ID);
        params.put("AssociateTag", ASSOCIATE_TAG);
        params.put("SearchIndex", "Books");
        params.put("ResponseGroup", RESPONSE_GROUP);
        params.put("Keywords", keyword);

        requestUrl = helper.sign(params);
        return requestUrl;
    }

}




