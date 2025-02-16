package com.sip_calculator.Sip_Calculator.RateLimiter;

import com.sip_calculator.Sip_Calculator.Exception.RateLimitingException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimitingAspect {

    @Value("${rateLimiter.timeDuration}")
    private Long timeDuration; //time in ms

    @Value("${rateLimiter.rateLimit}")
    private int rateLimit;

    private static final String ERROR_MESSAGE = "To many request at endpoint %s from IP %s! Please try again after %d milliseconds!";

    private final ConcurrentHashMap<String, List<Long>> requestCountMap = new ConcurrentHashMap<>();

    @Before("@annotation(WithRateLimitProtection)")
    public void rateLimitingLogic() {
        final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        final String key = requestAttributes.getRequest().getRemoteAddr();
        final long currentTime = System.currentTimeMillis();
        requestCountMap.putIfAbsent(key, new ArrayList<>());
        requestCountMap.get(key).add(currentTime);
        cleanUpRequestCounts(currentTime);
        if (requestCountMap.get(key).size() > rateLimit)
            throw new RateLimitingException(String.format(ERROR_MESSAGE, requestAttributes.getRequest().getRequestURI(), key, timeDuration));
    }

    /*
    For all the remote address it would clean up timeStamps which are older
     */
    private void cleanUpRequestCounts(Long currentTime) {
        requestCountMap.values().forEach(
                timeStampList -> timeStampList.removeIf(timeStamp -> {
                    return timeTooOld(timeStamp, currentTime);
                })
        );
    }

    private boolean timeTooOld(Long timeStamp, Long currentTime) {
        return currentTime - timeStamp > timeDuration;
    }
}
