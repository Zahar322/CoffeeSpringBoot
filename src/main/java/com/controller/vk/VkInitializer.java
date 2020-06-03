package com.controller.vk;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.friends.FriendsGetOnlineQuery;
import org.springframework.ui.Model;

import java.util.List;

public class VkInitializer {

    public static final int APP_ID = 7479355;
    public static final String CLIENT_SECRET = "9fDe4s0rxEt4JCDz6n38";
    public static final String REDIRECT_URI = "http://localhost:8081/main";

    public void authorizate(String code, Model model) throws ClientException, ApiException {
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);
        UserAuthResponse authResponse = vk.oauth()
                .userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, code)
                .execute();

        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
        FriendsGetOnlineQuery online = vk.friends().getOnline(actor);
        model.addAttribute("name", vk.users().get(actor).execute().get(0).getFirstName());

        System.out.println(online);
    }
}
