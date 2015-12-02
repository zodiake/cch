package com.by;

import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yagamai on 15-11-27.
 */
@Configuration
@EnableCaching
public class CachingConfig {
    @Bean(destroyMethod = "shutdown")
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

        //couponSummary cache config
        CacheConfiguration couponSummaryCacheConfig = new CacheConfiguration();
        couponSummaryCacheConfig.setMemoryStoreEvictionPolicy("LRU");
        couponSummaryCacheConfig.setMaxEntriesLocalHeap(1000);
        couponSummaryCacheConfig.setName("couponSummary");

        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(cardCacheConfig);
        config.addCache(ruleCacheConfig);
        config.addCache(couponSummaryCacheConfig);

        return net.sf.ehcache.CacheManager.newInstance(config);
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }
}
