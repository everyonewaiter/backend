package com.handwoong.everyonewaiter.notification.domain;

import com.handwoong.everyonewaiter.notification.dto.TemplateButton;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import java.util.List;
import java.util.UUID;

public interface TemplateContent {

	String content(Object... args);

	List<TemplateButton> buttons(StoreId storeId, UUID uniqueCode, String clientUrl);
}
