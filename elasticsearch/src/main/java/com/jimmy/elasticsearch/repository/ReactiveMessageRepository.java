package com.jimmy.elasticsearch.repository;

import com.jimmy.elasticsearch.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

/**
 * @Description 通过ReactiveESRepository进行响应式操作
 * 通过Reactive进行Message操作
 * * 返回类型为 Flux<T> 或 Mono<T>
 * * ReactiveSortingRepository 继承了 ReactiveCrudRepository，所以我们直接继承ReactiveSortingRepository
 * * 在写法上除了返回类型不同，其它与MessageRepository类似
 * @Author zhangguoq
 **/
public interface ReactiveMessageRepository extends ReactiveSortingRepository<Message, String> {

    Flux<Message> findBySender(String sender, Pageable pageable);
}