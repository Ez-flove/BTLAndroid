package com.example.manager.Event;

import com.example.manager.models.Event;

public interface OnEvent {
    void onEditEvent(Event event,int position);
    void onEventDelete(Event event,int position);
}

