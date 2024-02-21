package com.handwoong.everyonewaiter.store.application;

import com.handwoong.everyonewaiter.store.application.port.StoreRepository;
import com.handwoong.everyonewaiter.store.controller.port.StoreService;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.dto.StoreCreate;
import com.handwoong.everyonewaiter.store.dto.StoreOptionUpdate;
import com.handwoong.everyonewaiter.store.dto.StoreUpdate;
import com.handwoong.everyonewaiter.user.application.port.UserRepository;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {

	private final UserRepository userRepository;
	private final StoreRepository storeRepository;

	@Override
	@Transactional
	public StoreId create(final Username username, final StoreCreate storeCreate) {
		final User user = userRepository.findByUsernameOrElseThrow(username);
		final Store store = Store.create(user.getId(), storeCreate);
		final Store savedStore = storeRepository.save(store);
		return savedStore.getId();
	}

	@Override
	@Transactional
	public void update(final Username username, final StoreUpdate storeUpdate) {
		final Store store = findStoreByIdAndUsername(storeUpdate.id(), username);
		final Store updatedStore = store.update(storeUpdate);
		storeRepository.save(updatedStore);
	}

	@Override
	@Transactional
	public void update(final Username username, final StoreOptionUpdate storeOptionUpdate) {
		final Store store = findStoreByIdAndUsername(storeOptionUpdate.storeId(), username);
		final Store updatedStore = store.update(storeOptionUpdate);
		storeRepository.save(updatedStore);
	}

	@Override
	@Transactional
	public void delete(final Username username, final StoreId storeId) {
		final Store store = findStoreByIdAndUsername(storeId, username);
		storeRepository.delete(store);
	}

	private Store findStoreByIdAndUsername(final StoreId storeId, final Username username) {
		final User user = userRepository.findByUsernameOrElseThrow(username);
		return storeRepository.findByIdAndUserIdOrElseThrow(storeId, user.getId());
	}
}
