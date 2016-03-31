package org.huel.beasp.service.book;

import java.util.Arrays;
import java.util.List;

import org.huel.beasp.entity.book.ApplyBook;
import org.huel.beasp.entity.book.State;
import org.huel.beasp.entity.user.User;
import org.huel.beasp.exception.BeaspException;
import org.huel.beasp.repository.book.ApplyBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 求书籍业务逻辑
 * @author 001
 *
 */
@Service("applyBookService")
public class ApplyBookService {
	
	@Autowired private ApplyBookRepository applyBookRepository;
	
	@Transactional
	public void batchFailBook(List<Integer> ids) {
		for(Integer id : ids) {
			ApplyBook applyBook = applyBookRepository.findOne(id);
			if(applyBook == null) 
				throw new BeaspException();
			if(applyBook.getApplyer() == null || applyBook.getBook() == null)
				throw new BeaspException();
			if(!applyBook.getState().equals(State.WAITVERIFY)) 
				throw new BeaspException();
			applyBookRepository.verifyFail(State.VERIFYPASS, id);
		}
	}
	

	@Transactional
	public void batchPassBook(List<Integer> ids) {
		for(Integer id : ids) {
			ApplyBook applyBook = applyBookRepository.findOne(id);
			if(applyBook == null) 
				throw new BeaspException();
			if(applyBook.getApplyer() == null || applyBook.getBook() == null)
				throw new BeaspException();
			if(!applyBook.getState().equals(State.WAITVERIFY)) 
				throw new BeaspException();
			applyBookRepository.verifyPass(State.VERIFYPASS, id);
		}
	}
	
	/**
	 * 获取本人供应数量
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public long getCountByApplyerId(Integer id) {
		return applyBookRepository.getCountByApplyer_Id(id);
	}

	/**
	 * 获取本人需求数量
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public long getCountByRequirerId(Integer id) {
		return applyBookRepository.getCountByRequirer_Id(id);
	}
	
	/**
	 * 获取需求者数量(去重)
	 * @return
	 */
	@Transactional(readOnly = true)
	public long getDictinctByRequirerId() {
		return applyBookRepository.getDictinctByRequirer_Id();
	}
	
	/**
	 * 按id 获取
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public ApplyBook getById(Integer id) {
		return applyBookRepository.findOne(id);
	}
	
	@Transactional(readOnly = true)
	public Page<ApplyBook> findByApplyerIdAndShareExchange(User user, int shareExchange, 
			int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, new Sort(Direction.DESC, "createTime"));
		if(shareExchange > 0 && shareExchange <3) {
			if(shareExchange == 1 || shareExchange == 2) {
				return applyBookRepository.findByApplyer_IdAndShareExchange(user.getId(), shareExchange, pageable);
			} else 
				return applyBookRepository.findByApplyer_Id(user.getId(), pageable);
		} else 
			return applyBookRepository.findByApplyer_Id(user.getId(), pageable);
	}
	
	/**
	 * 获取所有我的供应
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<ApplyBook> findByApplyerId(User user, int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, new Sort(Direction.DESC, "createTime"));
		return applyBookRepository.findByApplyer_Id(user.getId(), pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<ApplyBook> findAll(int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, new Sort(Direction.DESC, "createTime"));
		return applyBookRepository.findAll(pageable);
	}	
	
	@Transactional(readOnly = true)
	public Page<ApplyBook> findByRequirerIdAndShareExchagne(User user, int shareExchange, 
			int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, new Sort(Direction.DESC, "createTime"));
		if(shareExchange > 0 && shareExchange <3) {
			if(shareExchange == 1 || shareExchange == 2) {
				return applyBookRepository.findByRequirer_IdAndShareExchange(user.getId(), shareExchange, pageable);
			} else 
				return applyBookRepository.findByRequirer_Id(user.getId(), pageable);
		} else 
			return applyBookRepository.findByRequirer_Id(user.getId(), pageable);
	}
	
	/**
	 * 获取我的所有需求
	 * @param user
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<ApplyBook> findByRequirerId(User user, int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, new Sort(Direction.DESC, "createTime"));
		return applyBookRepository.findByRequirer_Id(user.getId(), pageable);
	}
	
	@Transactional
	public void saveOrUpdate(ApplyBook applyBook) {
		applyBookRepository.saveAndFlush(applyBook);
	}
	
	@Transactional(readOnly = true)
	public Page<ApplyBook> findByRequirerIdAndStateInAndShareExchange(User user,int status,
			String soe, String order, int pageNo, int pageSize) {
		Pageable pageable = null;
		Sort sort = null;
		if(order != null && !"".equals(order)) {
			if("time_desc".equals(order)) {
				sort = new Sort(Direction.DESC, "createTime");
			} else if("time_asc".equals(order)) {
				sort = new Sort(Direction.ASC, "createTime");
			}
		} else {
			sort = new Sort(Direction.DESC, "createTime");
		}
		pageable = new PageRequest(pageNo-1, pageSize, sort);
		if(soe != null && !"".equals(soe)) {
			if("share".equals(soe)) {
				return findByRequiredIdAndStateInAndShareExchangeWithSwitch(user, status, 1,  pageable);
			} else if("exchange".equals(soe)) {
				return findByRequiredIdAndStateInAndShareExchangeWithSwitch(user, status, 2,  pageable);
			}
		} else {
			return findByRequiredIdAndStateInWithSwitch(user, status, pageable);
		}
		return applyBookRepository.findByRequirer_Id(user.getId(), pageable);
	}

	public Page<ApplyBook> findByRequiredIdAndStateInWithSwitch(User user, int status,
			Pageable pageable) {
		Page<ApplyBook> applyBooks = null;
		if(status > 0 && status < 6)  {
			switch (status) {
			case 1:
				applyBooks = applyBookRepository.findByRequirer_IdAndStateIn(user.getId(), Arrays.asList(State.WAITUPLOAD), pageable);
				break;
			case 2:
				applyBooks = applyBookRepository.findByRequirer_IdAndStateIn(user.getId(), Arrays.asList(State.UPLOADED), pageable);
				break;
			case 3:
				applyBooks = applyBookRepository.findByRequirer_IdAndStateIn(user.getId(), Arrays.asList(State.WAITSURE), pageable);
				break;
			case 4:
				applyBooks = applyBookRepository.findByRequirer_IdAndStateIn(user.getId(), Arrays.asList(State.WAITVERIFY), pageable);
				break;
			case 5:
				applyBooks = applyBookRepository.findByRequirer_IdAndStateIn(user.getId(), Arrays.asList(State.VERIFYPASS), pageable);
				break;
			default:
				applyBooks = applyBookRepository.findByRequirer_Id(user.getId(), pageable);
				break;
			}
		} else {
			applyBooks = applyBookRepository.findByRequirer_Id(user.getId(),  pageable);
		}
		return applyBooks;
	}

	private Page<ApplyBook> findByRequiredIdAndStateInAndShareExchangeWithSwitch(User user, int status, int shareExchange,
			Pageable pageable) {
		Page<ApplyBook> applyBooks = null;
		if(status > 0 && status < 6)  {
			switch (status) {
			case 1:
				applyBooks = applyBookRepository.findByRequirer_IdAndStateInAndShareExchange(user.getId(), Arrays.asList(State.WAITUPLOAD), shareExchange, pageable);
				break;
			case 2:
				applyBooks = applyBookRepository.findByRequirer_IdAndStateInAndShareExchange(user.getId(), Arrays.asList(State.UPLOADED), shareExchange, pageable);
				break;
			case 3:
				applyBooks = applyBookRepository.findByRequirer_IdAndStateInAndShareExchange(user.getId(), Arrays.asList(State.WAITSURE), shareExchange, pageable);
				break;
			case 4:
				applyBooks = applyBookRepository.findByRequirer_IdAndStateInAndShareExchange(user.getId(), Arrays.asList(State.WAITVERIFY), shareExchange, pageable);
				break;
			case 5:
				applyBooks = applyBookRepository.findByRequirer_IdAndStateInAndShareExchange(user.getId(), Arrays.asList(State.VERIFYPASS), shareExchange, pageable);
				break;
			default:
				applyBooks = applyBookRepository.findByRequirer_IdAndShareExchange(user.getId(),  shareExchange, pageable);
				break;
			}
		} else {
			applyBooks = applyBookRepository.findByRequirer_IdAndShareExchange(user.getId(), shareExchange, pageable);
		}
		return applyBooks;
	}
	
	
	
	@Transactional(readOnly = true)
	public Page<ApplyBook> findByStateInAndShareExchange(String order, String soe, int status, int pageNo, int pageSize) {
		Pageable pageable = null;
		Sort sort = null;
		if(order != null && !"".equals(order)) {
			if("time_desc".equals(order)) {
				sort = new Sort(Direction.DESC, "createTime");
			} else if("time_asc".equals(order)) {
				sort = new Sort(Direction.ASC, "createTime");
			}
		} else {
			sort = new Sort(Direction.DESC, "createTime");
		}
		pageable = new PageRequest(pageNo-1, pageSize, sort);
		if(soe != null && !"".equals(soe)) {
			if("share".equals(soe)) {
				return findByStateInAndShareExchangeWithSwitch(status, 1,  pageable);
			} else if("exchange".equals(soe)) {
				return findByStateInAndShareExchangeWithSwitch(status, 2,  pageable);
			}
		} else {
			return findByStateInWithSwitch(status, pageable);
		}
		return applyBookRepository.findAll(pageable);
	}

	private Page<ApplyBook> findByStateInWithSwitch(int status,
			Pageable pageable) {
		Page<ApplyBook> applyBooks = null;
		if(status > 0 && status < 6) {
			switch (status) {
			case 1:	
				applyBooks = applyBookRepository.findByStateIn(Arrays.asList(State.WAITUPLOAD), pageable);
				break;
			case 2:	
				applyBooks = applyBookRepository.findByStateIn(Arrays.asList(State.UPLOADED), pageable);
				break;
			case 3:	
				applyBooks = applyBookRepository.findByStateIn(Arrays.asList(State.WAITSURE), pageable);
				break;
			case 4:	
				applyBooks = applyBookRepository.findByStateIn(Arrays.asList(State.WAITVERIFY), pageable);
				break;
			case 5:	
				applyBooks = applyBookRepository.findByStateIn(Arrays.asList(State.VERIFYPASS), pageable);
				break;
			default:
				applyBooks = applyBookRepository.findAll(pageable);
				break;
			}
		} else {
			applyBooks = applyBookRepository.findAll(pageable);
		}
		return applyBooks;
	}

	private Page<ApplyBook> findByStateInAndShareExchangeWithSwitch(int status,
			int shareExchange, Pageable pageable) {
		Page<ApplyBook> applyBooks = null;
		if(status > 0 && status < 6) {
			switch (status) {
			case 1:
				applyBooks = applyBookRepository.findByStateInAndShareExchange(Arrays.asList(State.WAITUPLOAD), shareExchange, pageable);
				break;
			case 2:
				applyBooks = applyBookRepository.findByStateInAndShareExchange(Arrays.asList(State.UPLOADED), shareExchange, pageable);
				break;
			case 3:
				applyBooks = applyBookRepository.findByStateInAndShareExchange(Arrays.asList(State.WAITSURE), shareExchange, pageable);
				break;
			case 4:
				applyBooks = applyBookRepository.findByStateInAndShareExchange(Arrays.asList(State.WAITVERIFY), shareExchange, pageable);
				break;
			case 5:
				applyBooks = applyBookRepository.findByStateInAndShareExchange(Arrays.asList(State.VERIFYPASS), shareExchange, pageable);
				break;
			default:
				applyBooks = applyBookRepository.findByShareExchange(shareExchange, pageable);
				break;
			}
		} else {
			applyBooks = applyBookRepository.findByShareExchange(shareExchange, pageable);
		}
		return applyBooks;
	}

}
