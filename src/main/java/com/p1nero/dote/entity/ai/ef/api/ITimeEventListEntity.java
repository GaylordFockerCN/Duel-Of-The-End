package com.p1nero.dote.entity.ai.ef.api;

import java.util.List;

/**
 * 用于在升级的时候加血量
 */
public interface ITimeEventListEntity {

    List<TimeStampedEvent> getTimeEventList();

    void addEvent(TimeStampedEvent event);
    default void clearEvents(){
        getTimeEventList().clear();
    };

}