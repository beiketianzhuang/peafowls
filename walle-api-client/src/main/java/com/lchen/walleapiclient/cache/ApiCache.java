package com.lchen.walleapiclient.cache;

import com.lchen.walleapiclient.model.ApiInformation;

import java.util.Map;

/**
 * @author : lchen
 * @date : 2019/6/20
 */
public interface ApiCache {

    void addApi(ApiInformation apiInformation);

    ApiInformation apiInformationByGroup(String groupName);

    Map<String, ApiInformation> all();

    void clear();
}
