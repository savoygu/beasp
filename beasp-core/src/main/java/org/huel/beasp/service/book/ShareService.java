package org.huel.beasp.service.book;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.huel.beasp.entity.book.Category;
import org.huel.beasp.entity.book.Share;
import org.huel.beasp.entity.book.State;
import org.huel.beasp.repository.book.BookRepository;
import org.huel.beasp.repository.book.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 分享业务逻辑层
 * @author 001
 *
 */
@Service("shareService")
public class ShareService {
	@Autowired private ShareRepository shareRepository;
	@Autowired private BookRepository bookRepository;
	
	@Transactional(readOnly=true)
	public Page<Share> findAll(int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, Direction.DESC, "applyTime", "id");
		return shareRepository.findAll(pageable);
	}
	
	@Transactional(readOnly=true)
	public Share getByTargetId(Integer bookId) {
		return shareRepository.getByTarget_Id(bookId);
	}
	
	@Transactional(readOnly=true)
	public long getCountByUserId(Integer userId) {
		return shareRepository.getCountByUser_Id(userId);
	}
	
	@Transactional(readOnly=true)
	public List<Category> getCategoryByTargetUserId(Integer userId) {
		List<Integer> ids = shareRepository.getBookIdByTarget_User_Id(userId);
		if(ids != null && ids.size() > 0) {
			return bookRepository.getCategoryByBookIdIn(ids);
		}
		return null;
	}
	
	/**
	 * 获取分享书籍类别
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Category> getCategoryByUserId(Integer userId) {
		List<Integer> ids = shareRepository.getBookIdByUser_Id(userId);
		if(ids != null && ids.size() > 0) {
			return bookRepository.getCategoryByBookIdIn(ids);
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	public List<Date> getApplyTimeByTargetUserIdAndCategoryId(Integer userId, Integer cId, int pageNo, int pageSize) {
		List<Integer> ids = shareRepository.getByTarget_User_IdAndCategory_Id(userId, cId, (pageNo-1)*pageSize, pageSize);
		if(ids != null && ids.size() > 0) {
			return shareRepository.getApplyTimeById(ids);
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	public List<Date> getApplyTimeByTargetUserId(Integer userId, int pageNo, int pageSize) {
		List<Integer> ids = shareRepository.getIdByTarget_User_Id(userId, (pageNo-1)*pageSize, pageSize);
		if(ids != null && ids.size() > 0) {
			return shareRepository.getApplyTimeById(ids);
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	public List<Date> getApplyTimeByUserId(Integer userId,
			int pageNo, int pageSize) {
		List<Integer> ids = shareRepository.getIdByUser_Id(userId, (pageNo-1)*pageSize, pageSize);//基于原生SQL分页
		if(ids != null && ids.size() > 0) {
			return shareRepository.getApplyTimeById(ids);
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	public List<Date> getApplyTimeByUserIdAndCategoryId(Integer userId, Integer cId, 
			int pageNo, int pageSize) {
		List<Integer> ids = shareRepository.getIdByUser_IdAndCategory_Id(userId, cId, (pageNo-1)*pageSize, pageSize);
		if(ids != null && ids.size() > 0) {
			return shareRepository.getApplyTimeById(ids);
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	public Page<Share> findByTargetCategoryIdAndTargetUserId(Integer cId, Integer userId, int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, Direction.ASC, "applyTime", "id");
		return shareRepository.findByTarget_Category_IdAndTarget_User_Id(cId, userId, pageable);
	}
	
	
	@Transactional(readOnly=true)
	public Page<Share> findByTargetUserId(Integer userId, int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, Direction.ASC, "applyTime", "id");
		return shareRepository.findByTarget_User_Id(userId, pageable);
	}
	
	@Transactional(readOnly=true)
	public Page<Share> findByTargetCategoryIdAndUserIdAndState(Integer cId, Integer userId, State state, int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, Direction.ASC, "applyTime", "id");
		return shareRepository.findByTarget_Category_IdAndUser_IdAndState(cId, userId, state, pageable);
	}
	
	@Transactional(readOnly=true)
	public Page<Share> findByTargetCategoryIdAndUserId(Integer cId, Integer userId, int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, Direction.ASC, "applyTime", "id");
		return shareRepository.findByTarget_Category_IdAndUser_Id(cId, userId, pageable);
	}
	
	@Transactional(readOnly=true)
	public Page<Share> findByTargetIdAndUserIdAndState(Integer bookId, Integer userId, State state, int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, Direction.ASC, "applyTime", "id");
		return shareRepository.findByTarget_IdAndUser_IdAndState(bookId, userId, state, pageable);
	}
	
	@Transactional(readOnly=true)
	public Page<Share> findByUserIdAndState(Integer userId, State state, int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, Direction.ASC, "applyTime", "id");
		return shareRepository.findByUser_IdAndState(userId, state, pageable);
	}
	
	@Transactional(readOnly=true)
	public Page<Share> findByTargetIdAndUserId(Integer bookId, Integer userId, int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, Direction.ASC, "applyTime", "id");
		return shareRepository.findByTarget_IdAndUser_Id(bookId, userId, pageable);
	}
	
	@Transactional(readOnly=true)
	public Page<Share> findByUserId(Integer userId, int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, Direction.ASC, "applyTime", "id");
		return shareRepository.findByUser_Id(userId, pageable);
	}
	
	
	@Transactional(readOnly=true)
	public boolean getCountByTarget_IdAndResultIn(Integer bookId) {
		if(shareRepository.getCountByTarget_Id(bookId) > 0) {//说明该书籍被分享过
			//判断是否分享成功，或正在分享
			if(shareRepository.getCountByTarget_IdAndResultIn(bookId, Arrays.asList(0,1)) > 0) {//说明该书籍被正在分享中，或分享成功
				return true;
			}
			return false;//说明该书籍没有被正在分享，或分享成功，那就是分享失败了。
		}
		return false;//说明该书籍没有被分享过
	}
	
	@Transactional(readOnly=true)
	public boolean getCountByTarget_Id(Integer bookId) {
		return shareRepository.getCountByTarget_Id(bookId) > 0 ? true : false;
	}
	
	@Transactional(readOnly=true)
	public Share getByBook_IdAndByUser_Id(Integer bookId, Integer userId) {
		return shareRepository.getByUser_IdAndTarget_Id(userId, bookId);
	}
	
	/**
	 * 分享保存
	 * @param share
	 * @return 保存结果
	 */
	@Transactional
	public Share save(Share share) {
		return shareRepository.save(share);
	}

}
