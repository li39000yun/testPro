package com.lyq.sellercenter;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * Created by 云强 on 2017/12/4.
 */
public class SellercenterAPI {

    private static final String ScApiHost = "http://api.sellercenter.net/";
    private static final String HASH_ALGORITHM = "HmacSHA256";
    private static final String CHAR_UTF_8 = "UTF-8";
    private static final String CHAR_ASCII = "ASCII";
    public static void main(String[] args) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("UserID", "1376187082@qq.com");
        params.put("Timestamp", getCurrentTimestamp());
        params.put("Version", "1.0");
        params.put("Action", "ProductUpdate");
        final String apiKey = "55f86f79f3b4388507aba8c21a7bfd0d25626551";
        final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><Request><Product><SellerSku>4105382173aaee4</SellerSku><Price>12</Price></Product></Request>";
        final String out = getSellercenterApiResponse(params, apiKey, XML); // provide XML as an empty string when not needed
        System.out.println(out); // print out the XML response
    }

    /**
     * calculates the signature and sends the request
     *
     * @param params Map - request parameters
     * @param apiKey String - user's API Key
     * @param XML String - Request Body
     */
    public static String getSellercenterApiResponse(Map<String, String> params, String apiKey, String XML) {
        String queryString = "";
        String Output = "";
        HttpURLConnection connection = null;
        URL url = null;
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        queryString = toQueryString(sortedParams);
        final String signature = hmacDigest(queryString, apiKey, HASH_ALGORITHM);
        queryString = queryString.concat("&Signature=".concat(signature));
        final String request = ScApiHost.concat("?".concat(queryString));
        try {
            url = new URL(request);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", CHAR_UTF_8);
            connection.setUseCaches(false);
            if (!XML.equals("")) {
                connection.setRequestProperty("Content-Length", "" + Integer.toString(XML.getBytes().length));
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(XML);
                wr.flush();
                wr.close();
            }
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                Output += line + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Output;
    }

    /**
     * generates hash key
     *
     * @param msg
     * @param keyString
     * @param algo
     * @return string
     */
    private static String hmacDigest(String msg, String keyString, String algo) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes(CHAR_UTF_8), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(key);
            final byte[] bytes = mac.doFinal(msg.getBytes(CHAR_ASCII));
            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return digest;
    }

    /**
     * build querystring out of params map
     *
     * @param data map of params
     * @return string
     * @throws UnsupportedEncodingException
     */
    private static String toQueryString(Map<String, String> data) {
        String queryString = "";
        try{
            StringBuffer params = new StringBuffer();
            for (Map.Entry<String, String> pair : data.entrySet()) {
                params.append(URLEncoder.encode((String) pair.getKey(), CHAR_UTF_8) + "=");
                params.append(URLEncoder.encode((String) pair.getValue(), CHAR_UTF_8) + "&");
            }
            if (params.length() > 0) {
                params.deleteCharAt(params.length() - 1);
            }
            queryString = params.toString();
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return queryString;
    }

    /**
     * returns the current timestamp
     * @return current timestamp in ISO 8601 format
     */
    private static String getCurrentTimestamp(){
        final TimeZone tz = TimeZone.getTimeZone("UTC");
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
        df.setTimeZone(tz);
        final String nowAsISO = df.format(new Date());
        return nowAsISO;
    }

}
