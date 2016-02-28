package org.huel.beasp.service.book;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.huel.beasp.entity.book.Category;
import org.huel.beasp.entity.book.Exchange;
import org.huel.beasp.repository.book.BookRepository;
import org.huel.beasp.repository.book.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 交换业务逻辑层
 * @author 001
 *
 */
@Service("exchangeService")
public class ExchangeService {
	@Autowired private ExchangeRepository exchangeRepository;
	@Autowired private BookRepository bookRepository;

	@Transactional(readOnly=true)
	public boolean getCountByTarget_IdAndResultIn(Integer id) {
		if(exchangeRepository.getCountByTarget_Id(id) > 0) {//说明该书籍被交换过
			if(exchangeRepository.getCountByTarget_IdAndResultIn(id, Arrays.asList(0,1)) > 0) {//说明该书籍被正在交换中，或交换成功
				return true;
			}
			return false;//说明该书籍没有被正在交换，或交换成功，那就是交换失败了
		}
		return false;////说明该书籍没有被交换过
	}

	@Transactional
	public Exchange save(Exchange exchange) {
		return exchangeRepository.save(exchange);
	}

	@Transactional(readOnly=true)
	public Page<Exchange> findByExchangerId(Integer userId, int pageNo,
			int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, Direction.ASC, "applyTime", "id");
		return exchangeRepository.findByExchanger_Id(userId, pageable);
	}
	
	@Transactional(readOnly=true)
	public Page<Exchange> findByUserId(Integer userId, int pageNo,
			int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, Direction.ASC, "applyTime", "id");
		return exchangeRepository.findByUser_Id(userId, pageable);
	}

	@Transactional(readOnly=true)
	public Page<Exchange> findByTargetCategoryIdAndUserId(Integer cId,
			Integer userId, int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, Direction.ASC, "applyTime", "id");
		return exchangeRepository.findByTarget_Category_IdAndUser_Id(cId, userId, pageable);
	}

	public Page<Exchange> findByTargetCategoryIdAndExchangerId(Integer cId,
			Integer exchangeId, int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, Direction.ASC, "applyTime", "id");
		return exchangeRepository.findByTarget_Category_IdAndExchanger_Id(cId, exchangeId, pageable);
	}
	
	@Transactional(readOnly=true)
	public List<Date> getApplyTimeByUserId(Integer userId, int pageNo,
			int pageSize) {
		List<Integer> ids  = exchangeRepository.getIdByUser_Id(userId, (pageNo-1)*pageSize, pageSize);
		if(ids != null && ids.size() > 0) {
			return exchangeRepository.getApplyTimeById(ids);
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	public List<Date> getApplyTimeByExchangerId(Integer exchangerId, int pageNo,
			int pageSize) {
		List<Integer> ids  = exchangeRepository.getIdByExchanger_Id(exchangerId, (pageNo-1)*pageSize, pageSize);
		if(ids != null && ids.size() > 0) {
			return exchangeRepository.getApplyTimeById(ids);
		}
		return null;
	}

	public List<Date> getApplyTimeByTargetCategoryIdAndUserId(Integer userId, Integer cId,
			int pageNo, int pageSize) {
		List<Integer> ids  = exchangeRepository.getIdByTarget_Category_IdAndUser_Id(userId, cId, (pageNo-1)*pageSize, pageSize);
		if(ids != null && ids.size() > 0) {
			return exchangeRepository.getApplyTimeById(ids);
		}
		return null;
	}
	
	public List<Date> getApplyTimeByTargetCategoryIdAndExchangerId(Integer exchangerId,
			Integer cId, int pageNo, int pageSize) {
		List<Integer> ids  = exchangeRepository.getIdByTarget_Category_IdAndExchanger_Id(exchangerId, cId, (pageNo-1)*pageSize, pageSize);
		if(ids != null && ids.size() > 0) {
			return exchangeRepository.getApplyTimeById(ids);
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	public List<Category> getCategoryByUserId(Integer userId) {
		List<Integer> ids = exchangeRepository.getBookIdByUser_Id(userId);
		if(ids != null && ids.size() > 0) {
			return bookRepository.getCategoryByBookIdIn(ids);
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	public List<Category> getCategoryByExchangerId(Integer exchanger) {
		List<Integer> ids = exchangeRepository.getBookIdByExchanger_Id(exchanger);
		if(ids != null && ids.size() > 0) {
			return bookRepository.getCategoryByBookIdIn(ids);
		}
		return null;
	}

	@Transactional(readOnly=true)
	public long getCountByExchangerId(Integer exchangeId) {
		return exchangeRepository.getCountByExchange_Id(exchangeId);
	}
	@Transactional(readOnly=true)
	public long getCountByUserId(Integer userId) {
		return exchangeRepository.getCountByUser_Id(userId);
	}
	@Transactional(readOnly=true)
	public Exchange getById(Integer id) {
		return exchangeRepository.getOne(id);
	}

	
}
