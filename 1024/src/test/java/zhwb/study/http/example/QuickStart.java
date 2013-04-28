/*
 * ====================================================================
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package zhwb.study.http.example;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class QuickStart
{

    public static void main(final String[] args)
        throws Exception
    {
        //        HttpGet httpGet = new HttpGet("http://hc.apache.org/httpcomponents-client-ga/");
        //
        //        HttpResponse response1 = httpclient.execute(httpGet);
        //
        //        // The underlying HTTP connection is still held by the response object 
        //        // to allow the response content to be streamed directly from the network socket. 
        //        // In order to ensure correct deallocation of system resources 
        //        // the user MUST either fully consume the response content  or abort request 
        //        // execution by calling HttpGet#releaseConnection().
        //
        //        try
        //        {
        //            System.out.println(response1.getStatusLine());
        //            HttpEntity entity1 = response1.getEntity();
        //            // do something useful with the response body
        //            // and ensure it is fully consumed
        //            EntityUtils.consume(entity1);
        //        }
        //        finally
        //        {
        //            httpGet.releaseConnection();
        //        }
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpHost proxy = new HttpHost("web-proxy.rose.hp.com", 8080, "http");
        httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

        HttpPost httpPost = new HttpPost("http://184.154.128.246/register.php?");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("regname", "LongCock"));
        nvps.add(new BasicNameValuePair("regpwd", "longcock"));
        nvps.add(new BasicNameValuePair("regpwdrepeat", "longcock"));
        nvps.add(new BasicNameValuePair("regemail", "kid_zhwb@163.com"));
        nvps.add(new BasicNameValuePair("invcode", "ddsdf23fdfdsfasfg"));
        nvps.add(new BasicNameValuePair("forward", ""));
        nvps.add(new BasicNameValuePair("step", "2"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "gbk"));

        try
        {
            HttpResponse response = httpclient.execute(httpPost);
            Header[] headers = response.getAllHeaders();
            for (Header header : headers)
            {
                System.out.println(header.getName() + ": " + header.getValue());
            }
            HttpEntity entity = response.getEntity();
            if (EntityUtils.toString(entity, "gbk").contains("邀請碼錯誤!"))
            {
                System.out.println("Fail");
            }
            else
            {
                System.out.println("Success");
            }
            EntityUtils.consume(entity);
        }
        finally
        {

            httpPost.releaseConnection();
        }
    }

}