package czertainly.ip.discovery.service.impl;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.connector.discovery.DiscoveryRequestDto;
import com.czertainly.api.model.core.discovery.DiscoveryStatus;
import czertainly.ip.discovery.dao.DiscoveryHistory;
import czertainly.ip.discovery.repository.DiscoveryHistoryRepository;
import czertainly.ip.discovery.service.DiscoveryHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class DiscoveryHistoryServiceImpl implements DiscoveryHistoryService {

	private static final Logger logger = LoggerFactory.getLogger(DiscoveryHistoryServiceImpl.class);

	@Autowired
	private DiscoveryHistoryRepository discoveryHistoryRepository;

	@Override
	public DiscoveryHistory addHistory(DiscoveryRequestDto request) {
		logger.debug("Adding a new entry to the database for the discovery with name {}", request.getName());
		DiscoveryHistory modal = new DiscoveryHistory();
		modal.setUuid(UUID.randomUUID().toString());
		modal.setName(request.getName());
		modal.setStatus(DiscoveryStatus.IN_PROGRESS);
		discoveryHistoryRepository.save(modal);
		return modal;
	}

	@Override
	public DiscoveryHistory getHistoryById(Long id) throws NotFoundException {
		logger.info("Finding the discovery history record for ID {}", id);
		return discoveryHistoryRepository.findById(id).orElseThrow(() -> new NotFoundException(DiscoveryHistoryServiceImpl.class, id));
	}

	@Override
	public DiscoveryHistory getHistoryByUuid(String uuid) throws NotFoundException {
		logger.info("Finding the discovery history record for uuid {}", uuid);
		return discoveryHistoryRepository.findByUuid(uuid).orElseThrow(() -> new NotFoundException(DiscoveryHistoryServiceImpl.class, uuid));
	}
	
	public void setHistory(DiscoveryHistory history) {
		discoveryHistoryRepository.save(history);
	}
}
