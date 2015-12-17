package com.by;

import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yagamai on 15-11-27.
 */
@Configuration
@EnableCaching
public class CachingConfig extends CachingConfigurerSupport {
    public net.sf.ehcache.CacheManager ehCacheManager() {
        //card cache config
        CacheConfiguration cardCacheConfig = new CacheConfiguration();
        cardCacheConfig.setMemoryStoreEvictionPolicy("LRU");
        cardCacheConfig.setMaxEntriesLocalHeap(1000);
        cardCacheConfig.setName("card");

        //rule cache config
        CacheConfiguration ruleCacheConfig = new CacheConfiguration();
        ruleCacheConfig.setMemoryStoreEvictionPolicy("LRU");
        ruleCacheConfig.setMaxEntriesLocalHeap(1000);
        ruleCacheConfig.setName("rule");

        //parkingCoupon cache config
        CacheConfiguration couponCacheConfig = new CacheConfiguration();
        couponCacheConfig.setMemoryStoreEvictionPolicy("LRU");
        couponCacheConfig.setMaxEntriesLocalHeap(1000);
        couponCacheConfig.setName("coupon");

        //menu cache config
        CacheConfiguration menuCacheConfig = new CacheConfiguration();
        menuCacheConfig.setMemoryStoreEvictionPolicy("LRU");
        menuCacheConfig.setMaxEntriesLocalHeap(1000);
        menuCacheConfig.setName("menu");

        //rule cache config
        CacheConfiguration cardRuleCacheConfig = new CacheConfiguration();
        cardRuleCacheConfig.setMemoryStoreEvictionPolicy("LRU");
        cardRuleCacheConfig.setMaxEntriesLocalHeap(1000);
        cardRuleCacheConfig.setName("cardRule");

        //shop cache config
        CacheConfiguration shopCacheConfig = new CacheConfiguration();
        shopCacheConfig.setMemoryStoreEvictionPolicy("LRU");
        shopCacheConfig.setMaxEntriesLocalHeap(1000);
        shopCacheConfig.setName("shop");

        CacheConfiguration memberCacheConfig = new CacheConfiguration();
        memberCacheConfig.setMemoryStoreEvictionPolicy("LRU");
        memberCacheConfig.setMaxEntriesLocalHeap(1000);
        memberCacheConfig.setName("member");

        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(cardCacheConfig);
        config.addCache(ruleCacheConfig);
        config.addCache(couponCacheConfig);
        config.addCache(menuCacheConfig);
        config.addCache(cardRuleCacheConfig);
        config.addCache(shopCacheConfig);
        config.addCache(memberCacheConfig);

        return net.sf.ehcache.CacheManager.newInstance(config);
    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }
}
