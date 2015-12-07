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

        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(cardCacheConfig);
        config.addCache(ruleCacheConfig);
        config.addCache(parkingCouponCacheConfig);
        config.addCache(preferentialCouponCacheConfig);

        return net.sf.ehcache.CacheManager.newInstance(config);
    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }
}
