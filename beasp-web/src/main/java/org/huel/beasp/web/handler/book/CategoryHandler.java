package org.huel.beasp.web.handler.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.huel.beasp.entity.book.Category;
import org.huel.beasp.entity.common.PageIndex;
import org.huel.beasp.service.book.CategoryService;
import org.huel.beasp.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 书籍类别控制层
 * @author 001
 *
 */
@Controller
@RequestMapping("/admin/book")
public class CategoryHandler {
	@Autowired private CategoryService categoryService;
	
	/**
	 * 书籍类别选择
	 * @param parentId
	 * @param map
	 * @return
	 */
	@RequestMapping("/selectType")
	public String selectType(@RequestParam(value="parentId", required=false) Integer parentId, Map<String, Object> map) {
		List<Category> categories = null;
		if(parentId != null && parentId > 0) {
			categories = categoryService.getByParentId(parentId);
			//导航菜单
			List<Category> cates = new ArrayList<Category>();
			Category category = categoryService.getById(parentId);
			Category parent = category.getParent();
			cates.add(category);
			while(parent != null) {
				cates.add(parent);
				parent = parent.getParent();
			}
			map.put("menucates", cates);
		} else {
			categories = categoryService.getByParentIsNull();
		}
		map.put("types", categories);
		return "admin/book/category/selectType";
	}

	/**
	 * 按书籍类别Id 获取子类别
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ajaxGetCategories", method=RequestMethod.POST)
	public List<Category> getCategoriesById(@RequestParam("id") Integer parentId) {
		return categoryService.getPartByParentId(parentId);
	}
	
	/**
	 * 类别查询界面
	 * @return
	 */
	@RequestMapping(value="/category/find", method=RequestMethod.GET)
	public String find(){
		return "admin/book/category/find";
	}
	
	/**
	 * 批量删除类别
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/category/ids", method=RequestMethod.POST)
	public String batchDelete(@RequestParam(value="param", required=true) String param,
			@RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr,Map<String, Object> map){
		if(param!=null && !"".equals(param)) {
			String[] params = param.split("-");
			List<Integer> ids = new ArrayList<Integer>();
			for(String str : params) {
				ids.add(Integer.parseInt(str));
			}
			categoryService.batchDelete(ids);
		}
		//回到从前页
		int pageNo = getCurrentPage(pageNoStr);
		Page<Category> categories  = categoryService.getPage(pageNo, Constants.PAGE_SIZE_ADMIN);
		//如果当前页没有记录,就需要 页数-1
		if(categories == null || categories.getNumberOfElements() == 0) {
			pageNo = pageNo -1;
			if(pageNo < 1) {
				pageNo = 1;
			}
		}
		map.put("pageNo", pageNo);
		return "redirect:/admin/book/categories";
	}
	
	/**
	 * 删除类别
	 * @param id
	 * @param pageNoStr
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/category/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id, 
			@RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr,
			Map<String, Object> map,
			@RequestParam(value="parentId", required=false) String parentIdStr) {
		categoryService.delete(id);
		//回到从前页
		int pageNo = getCurrentPage(pageNoStr);

		Page<Category> categories = null;
		int parentId = 1;
		if(parentIdStr != null && !"".equals(parentIdStr)) {
			parentId = Integer.parseInt(parentIdStr);
			categories = categoryService.getPageByParentId(pageNo, Constants.PAGE_SIZE_ADMIN, parentId);
		} else {
			categories = categoryService.getPage(pageNo, Constants.PAGE_SIZE_ADMIN);
		}
		//如果当前页没有记录,就需要 页数-1
		if(categories == null || categories.getNumberOfElements() == 0) {
			pageNo = pageNo -1;
			if(pageNo < 1) {
				pageNo = 1;
			}
		}
		System.out.println(parentId);
		map.put("pageNo", pageNo);
		if(parentIdStr != null && !"".equals(parentIdStr)) {
			return "redirect:/admin/book/categories?parentId="+parentId;
		}
		return "redirect:/admin/book/categories";
	}
	/**
	 * 获取当前页
	 * @param pageNoStr
	 * @return
	 */
	private int getCurrentPage(String pageNoStr) {
		int pageNo = 1;
		if(pageNoStr != null && !"".equals(pageNoStr)) {
			try {
				//对 pageNo 的校验
				pageNo = Integer.parseInt(pageNoStr);
				if(pageNo < 1) {
					pageNo = 1;
				}
			} catch (Exception e) {}
		}
		return pageNo;
	}
	
	/**
	 * 类别修改
	 * @param category
	 * @return
	 */
	@RequestMapping(value="/category/{id}", method=RequestMethod.PUT)
	public String update(Category category, 
			Map<String, Object> map) {
		categoryService.save(category);
		Category parent = category.getParent();
		if(parent != null) {
			return "redirect:/admin/book/categories?parentId="+parent.getId();
		}
		return "redirect:/admin/book/categories";
	}
	
	/**
	 * 类别修改界面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/category/{id}", method=RequestMethod.GET)
	public String input(@PathVariable("id") Integer id, Map<String, Object> map) {
		Category category = categoryService.getById(id);
		map.put("category", category);
		return "admin/book/category/input";
	}
	
	/**
	 * 类别添加
	 * @param category
	 * @return
	 */
	@RequestMapping(value="/category", method=RequestMethod.POST)
	public String save(Category category) {
		categoryService.save(category);
		Category parent = category.getParent();
		if(parent != null) {
			return "redirect:/admin/book/categories?parentId="+parent.getId();
		}
		return "redirect:/admin/book/categories";
	}
	
	/**
	 * ajax 校验类别名
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ajaxValidateName", method=RequestMethod.POST)
	public String validateName(@RequestParam(value="name", required=true) String name) {
		Category category = categoryService.getByName(name);
		if(category == null) {
			return "0";
		} else 
			return "1";
	}
	
	/**
	 * 类别添加界面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="category", method=RequestMethod.GET)
	public String input(Map<String, Object> map, @RequestParam(name="parentId", required=false) String parentIdStr) {
		Category category = new Category();
		if(parentIdStr != null && !"".equals(parentIdStr)) {
//			map.put("parentId", Integer.parseInt(parentIdStr));
			category.setParent(categoryService.getById(Integer.parseInt(parentIdStr)));
		}
		map.put("category", category);
		return "admin/book/category/input";
	}
	
	/**
	 * 类别列表
	 * @param pageNoStr
	 * @param map
	 * @return
	 */
	@RequestMapping("/categories")
	public String list(@RequestParam(name="pageNo", required=false, defaultValue="1") String pageNoStr , 
			Map<String, Object> map,
			@RequestParam(value="parentId", required=false) String parentIdStr,
			@RequestParam(value="name", required=false) String name, @RequestParam(value="query", required=false) String query) {
		Page<Category> categories = null;
		if(query!=null && "true".equals(query)) {
			categories = categoryService.getPageByName(getCurrentPage(pageNoStr), Constants.PAGE_SIZE_ADMIN, name);
			map.put("query", "true");
			map.put("name", name);
		}else {
			categories = categoryService.getPage(getCurrentPage(pageNoStr), Constants.PAGE_SIZE_ADMIN);
			if(parentIdStr != null && !"".equals(parentIdStr)) {
				categories = categoryService.getPageByParentId(getCurrentPage(pageNoStr), Constants.PAGE_SIZE_ADMIN, Integer.parseInt(parentIdStr));
			}
		}
		map.put("page", categories);
		map.put("pageIndex", PageIndex.getPageIndex(Constants.VIEW_PAGE_COUNT, 
				Integer.parseInt(pageNoStr), 
				Long.parseLong(String.valueOf(categories.getTotalPages()))));
		return "admin/book/category/list";
	}
}
