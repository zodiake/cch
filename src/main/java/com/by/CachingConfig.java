package com.by;

import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yagamai on 15-11-27.
 */
@Configuration
@EnableCaching
public class CachingConfig {
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
        CacheConfiguration parkingCouponCacheConfig = new CacheConfiguration();
        parkingCouponCacheConfig.setMemoryStoreEvictionPolicy("LRU");
        parkingCouponCacheConfig.setMaxEntriesLocalHeap(1000);
        parkingCouponCacheConfig.setName("parkingCoupon");

        //preferentialCoupon cache config
        CacheConfiguration preferentialCouponCacheConfig = new CacheConfiguration();
        preferentialCouponCacheConfig.setMemoryStoreEvictionPolicy("LRU");
        preferentialCouponCacheConfig.setMaxEntriesLocalHeap(1000);
        preferentialCouponCacheConfig.setName("preferentialCoupon");

        //shopcoupon cache config
        CacheConfiguration shopCouponCacheConfig = new CacheConfiguration();
        shopCouponCacheConfig.setMemoryStoreEvictionPolicy("LRU");
        shopCouponCacheConfig.setMaxEntriesLocalHeap(1000);
        shopCouponCacheConfig.setName("shopCoupon");

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

        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(cardCacheConfig);
        config.addCache(ruleCacheConfig);
        config.addCache(parkingCouponCacheConfig);
        config.addCache(preferentialCouponCacheConfig);
        config.addCache(shopCouponCacheConfig);
        config.addCache(menuCacheConfig);
        config.addCache(cardRuleCacheConfig);
        config.addCache(shopCacheConfig);

        return net.sf.ehcache.CacheManager.newInstance(config);
    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }
}
