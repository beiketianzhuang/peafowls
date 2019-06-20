package com.lchen.walleapiclient.cache;

import com.lchen.walleapiclient.model.ApiInformation;

import java.util.Collections;
import java.util.Map;

import static com.google.common.collect.Maps.newLinkedHashMap;

/**
 * @author : lchen
 * @date : 2019/6/20
 */
public class ApiInformationCache implements ApiCache {
    private Map<String, ApiInformation> apiInformationMap = newLinkedHashMap();

    @Override
    public void addApi(ApiInformation apiInformation) {
        apiInformationMap.put(apiInformation.getGroupName(), apiInformation);
    }

    @Override
    public ApiInformation apiInformationByGroup(String groupName) {
        return apiInformationMap.get(groupName);
    }

    @Override
    public Map<String, ApiInformation> all() {
        return Collections.unmodifiableMap(apiInformationMap);
    }

    @Override
    public void clear() {
        apiInformationMap.clear();
    }
}
