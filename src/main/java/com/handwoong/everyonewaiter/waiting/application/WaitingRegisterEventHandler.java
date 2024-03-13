package com.handwoong.everyonewaiter.waiting.application;

import com.handwoong.everyonewaiter.common.config.client.ClientConfig;
import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.notification.application.port.MessageSender;
import com.handwoong.everyonewaiter.notification.application.port.NotificationRepository;
import com.handwoong.everyonewaiter.notification.domain.Notification;
import com.handwoong.everyonewaiter.notification.domain.Template;
import com.handwoong.everyonewaiter.notification.dto.AlimTalkResponse;
import com.handwoong.everyonewaiter.notification.dto.TemplateButton;
import com.handwoong.everyonewaiter.store.domain.LandlineNumber;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.domain.StoreName;
import com.handwoong.everyonewaiter.waiting.domain.WaitingAdult;
import com.handwoong.everyonewaiter.waiting.domain.WaitingChildren;
import com.handwoong.everyonewaiter.waiting.domain.WaitingNumber;
import com.handwoong.everyonewaiter.waiting.domain.WaitingTurn;
import com.handwoong.everyonewaiter.waiting.domain.event.WaitingRegisterEvent;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class WaitingRegisterEventHandler {

	private final ClientConfig clientConfig;
	private final MessageSender messageSender;
	private final NotificationRepository notificationRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void afterProcess(final WaitingRegisterEvent waitingRegisterEvent) {
		final StoreId storeId = waitingRegisterEvent.storeId();
		final StoreName storeName = waitingRegisterEvent.storeName();
		final WaitingAdult adult = waitingRegisterEvent.adult();
		final WaitingChildren children = waitingRegisterEvent.children();
		final WaitingNumber number = waitingRegisterEvent.number();
		final WaitingTurn turn = waitingRegisterEvent.turn();
		final LandlineNumber landlineNumber = waitingRegisterEvent.landlineNumber();
		final UUID uniqueCode = waitingRegisterEvent.uniqueCode();
		final PhoneNumber to = waitingRegisterEvent.phoneNumber();

		final String code = Template.REGISTER.getCode();
		final String content = Template.REGISTER.content(storeName, adult, children, number, turn, landlineNumber);
		final List<TemplateButton> buttons =
				Template.REGISTER.buttons(storeId, uniqueCode, clientConfig.getClientUrl());

		final AlimTalkResponse result = messageSender.sendAlimTalk(to.toString(), code, content, buttons);
		notificationRepository.save(Notification.create(storeId, Template.REGISTER, result));
	}
}
