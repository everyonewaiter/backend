package com.handwoong.everyonewaiter.waiting.application;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.notification.application.port.MessageSender;
import com.handwoong.everyonewaiter.notification.application.port.NotificationRepository;
import com.handwoong.everyonewaiter.notification.domain.Notification;
import com.handwoong.everyonewaiter.notification.domain.Template;
import com.handwoong.everyonewaiter.notification.dto.AlimTalkResponse;
import com.handwoong.everyonewaiter.store.application.port.StoreRepository;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.waiting.domain.event.WaitingCancelEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class WaitingCancelEventHandler {

	private final MessageSender messageSender;
	private final StoreRepository storeRepository;
	private final NotificationRepository notificationRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void afterProcess(final WaitingCancelEvent waitingCancelEvent) {
		final Store store = storeRepository.findByIdOrElseThrow(waitingCancelEvent.storeId());
		final PhoneNumber to = waitingCancelEvent.phoneNumber();
		final String code = Template.CANCEL.getCode();
		final String content = Template.CANCEL.content(store.getName(), waitingCancelEvent.number());

		final AlimTalkResponse result = messageSender.sendAlimTalk(to.toString(), code, content, List.of());
		notificationRepository.save(Notification.create(store.getId(), Template.CANCEL, result));
	}
}
