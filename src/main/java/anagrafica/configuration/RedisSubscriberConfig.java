package anagrafica.configuration;

import anagrafica.subscriber.AgentZoneSubscriber;
import anagrafica.subscriber.CompanyZoneSubscriber;
import anagrafica.subscriber.LoginSubscriber;
import anagrafica.subscriber.PlacerZoneSubscriber;
import anagrafica.utils.RedisTopics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisSubscriberConfig {

    @Bean
    public RedisMessageListenerContainer redisContainer(
            RedisConnectionFactory factory,
            LoginSubscriber loginSubscriber,
            AgentZoneSubscriber agentZoneSubscribe,
            CompanyZoneSubscriber companyZoneSubscriber,
            PlacerZoneSubscriber placerZoneSubscriber) {

        RedisMessageListenerContainer container =
                new RedisMessageListenerContainer();

        container.setConnectionFactory(factory);

        container.addMessageListener(
                loginSubscriber,
                new ChannelTopic(RedisTopics.LOGIN)
        );

        container.addMessageListener(
                agentZoneSubscribe,
                new ChannelTopic(RedisTopics.AGENT_ZONE)
        );

        container.addMessageListener(
                companyZoneSubscriber,
                new ChannelTopic(RedisTopics.COMPANY_ZONE)
        );

        container.addMessageListener(
                placerZoneSubscriber,
                new ChannelTopic(RedisTopics.PLACER_ZONE)
        );
        return container;
    }
}
