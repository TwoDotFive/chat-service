package xyz.fanpool.chat_service.adapter.out.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "fanpool-service", url = "${url.main-service}")
public interface FanpoolServiceClient {

    @GetMapping("/admin/fanpool/{fanpoolId}/teams")
    String findGameTeamsOfFanpool(@PathVariable(name = "fanpoolId") long fanpoolId);
}
