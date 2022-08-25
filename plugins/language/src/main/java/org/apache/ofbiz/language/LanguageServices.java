/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package org.apache.ofbiz.language;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.Session;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.ServiceUtil;

public class LanguageServices {
    private static final String MODULE = LanguageServices.class.getName();

    public static Map<String, Object> sendLanguagePushNotifications(DispatchContext dctx, Map<String, ? extends Object> context) {
        String languageId = (String) context.get("languageId");
        String message = (String) context.get("message");
        Set<Session> clients = LanguageWebSockets.getClients();
        try {
            synchronized (clients) {
                for (Session client : clients) {
                    client.getBasicRemote().sendText(message + ": " + languageId);
                }
            }
        } catch (IOException e) {
            Debug.logError(e.getMessage(), MODULE);
        }
        return ServiceUtil.returnSuccess();
    }

    /**
     * Finds languageId(s) corresponding to a language reference, languageId or a GoodIdentification idValue
     * @param ctx the dispatch context
     * @param context languageId use to search with languageId or goodIdentification.idValue
     * @return a GenericValue with a languageId and a List of complementary languageId found
     */
    public static Map<String, Object> searchLanguage(DispatchContext ctx, Map<String, Object> context) throws GenericEntityException {
        Delegator delegator = ctx.getDelegator();
        String sourceLanguage = (String) context.get("sourceLanguage");
        String targetLanguage = (String) context.get("targetLanguage");

//        GenericValue language = null;
        List<GenericValue> list = null;
        if (sourceLanguage != null && targetLanguage != null) {
            list = EntityQuery.use(delegator).from("MatisLanguage").where("sourceLanguage", sourceLanguage, "targetLanguage", targetLanguage)
                    .orderBy("languageId").cache(true).queryList();
        } else if (sourceLanguage == null && targetLanguage != null) {
            list = EntityQuery.use(delegator).from("MatisLanguage").where("targetLanguage", targetLanguage).orderBy("languageId").cache(true).queryList();
        } else if (sourceLanguage != null) {
            list = EntityQuery.use(delegator).from("MatisLanguage").where("sourceLanguage", sourceLanguage).orderBy("languageId").cache(true).queryList();
        } else {
            return ServiceUtil.returnError(getMessage());
        }
//        try {
//            list =  EntityQuery.use(delegator).from("MatisLanguage").where("sourceLanguage", sourceLanguage, "targetLanguage", targetLanguage)
//                    .orderBy("languageId").cache(true).queryList();
//        } catch (GenericEntityException e) {
//            Debug.logError(e, MODULE);
//            return ServiceUtil.returnError(e.getMessage());
//        }


//        try {
//            list =  EntityQuery.use(delegator).from("MatisLanguage").where("sourceLanguage", sourceLanguage, "targetLanguage", targetLanguage)
//                    .orderBy("languageId").cache(true).queryList();
//        } catch (GenericEntityException e) {
//            Debug.logError(e, MODULE);
//            return ServiceUtil.returnError(e.getMessage());
//        }

        Map<String, Object> result = ServiceUtil.returnSuccess();

        result.put("list", list);

        return result;
    }

    public static Map<String, Object> searchSystemLanguage(DispatchContext ctx, Map<String, Object> context) throws GenericEntityException {
        Delegator delegator = ctx.getDelegator();
        String systemLanguageCode = (String) context.get("systemLanguageCode");

        List<GenericValue> list = null;
        if (systemLanguageCode != null) {
            list = EntityQuery.use(delegator).from("SystemLanguage").where("systemLanguageCode", systemLanguageCode)
                    .orderBy("systemLanguageId").cache(true).queryList();
        } else {
            return ServiceUtil.returnError(getMessage());
        }

        Map<String, Object> result = ServiceUtil.returnSuccess();

        result.put("list", list);

        return result;
    }

    public static Map<String, Object> languageList(DispatchContext ctx, Map<String, Object> context) throws GenericEntityException {
        Delegator delegator = ctx.getDelegator();
        String entityName = (String) context.get("entityName");

        List<GenericValue> list = null;
        if (entityName.equals("MatisLanguage")) {
            list = EntityQuery.use(delegator).from("MatisLanguage").orderBy("languageId").cache(true).queryList();
        } else if(entityName.equals("SystemLanguage")) {
            list = EntityQuery.use(delegator).from("SystemLanguage").orderBy("systemLanguageId").cache(true).queryList();
        } else {
            return ServiceUtil.returnError(getMessage());
        }

        Map<String, Object> result = ServiceUtil.returnSuccess();

        result.put("list", list);

        return result;
    }

    private static String getMessage() {
        return null;
    }
}
