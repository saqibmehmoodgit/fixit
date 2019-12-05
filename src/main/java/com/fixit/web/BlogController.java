package com.fixit.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fixit.domain.bo.BlogBo;
import com.fixit.domain.vo.Blog;
import com.fixit.domain.vo.BlogCategory;
import com.fixit.domain.vo.CategoryType;
import com.fixit.domain.vo.ChatUserGroup;
import com.fixit.domain.vo.User;
import com.fixit.service.BlogService;
import com.fixit.service.CategoryTypeService;
import com.fixit.service.ChatUserGroupService;
import com.fixit.service.MessageNotificationService;
import com.fixit.utility.DbConstants;
import com.fixit.utility.FixitException;
import com.fixit.utility.FixitVariables;

@Controller("BlogController")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private MessageNotificationService messageNotificationService;

	@Autowired
	private CategoryTypeService categoryTypeService;

	
	@Autowired
	private ChatUserGroupService chatUserGroupService;
	/*
	 * Blog service for member star here
	 */
	@RequestMapping(value = "member/newBlog")
	public ModelAndView newMemberBlog(HttpSession session)
			throws FixitException {
		ModelAndView modelAndView = new ModelAndView();
		User user = (User) session.getAttribute("user");
		BlogBo blogBo = new BlogBo();
		ObjectMapper objectMapper = new ObjectMapper();
		Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
		modelAndView.addObject("parentCategory", categoryTypeService
				.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
		try {
			List<Integer> userIds=new LinkedList<Integer>();
			userIds.add(user.getUserId());
				ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
				if(chatUserGroup!=null){
					modelAndView.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
					modelAndView.addObject("isGroup","Yes");
					getNotificationCount(modelAndView,user.getUserId());
				
				}else{
					modelAndView.addObject("isGroup","No");
					modelAndView.addObject("groupId", -1);
				}
			modelAndView
					.addObject(
							"allCategory",
							objectMapper.writeValueAsString(categoryTypeService
									.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));
			modelAndView.addObject("catTypeSet",
					objectMapper.writeValueAsString(catTypeSet));
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}

		modelAndView.addObject("myUser", user);
		modelAndView.addObject("blog", blogBo);
		modelAndView.addObject("blogBo", blogBo);

		modelAndView.addObject("currentPage", "blog");
		modelAndView.setViewName("blog/memberBlogs/new.jsp");
		return modelAndView;
	}

	@RequestMapping(value = "member/createBlog", method = RequestMethod.POST)
	public ModelAndView createBlog(
			@Valid @ModelAttribute("blogBo") BlogBo blog, BindingResult result,
			HttpSession session) {

		ModelAndView modelAndView = new ModelAndView();
		try {
			BlogBo blogBo = new BlogBo();

			User user = (User) session.getAttribute("user");
			List<Integer> userIds=new LinkedList<Integer>();
			userIds.add(user.getUserId());
				ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
				if(chatUserGroup!=null){
					modelAndView.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
					modelAndView.addObject("isGroup","Yes");
					getNotificationCount(modelAndView,user.getUserId());
				}else{
					modelAndView.addObject("isGroup","No");
					modelAndView.addObject("groupId", -1);
				}
			if (result.hasErrors()) {
				ObjectMapper objectMapper = new ObjectMapper();
				Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
				modelAndView.addObject("parentCategory", categoryTypeService
						.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
				try {
					modelAndView
							.addObject(
									"allCategory",
									objectMapper
											.writeValueAsString(categoryTypeService
													.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));
					modelAndView.addObject("catTypeSet",
							objectMapper.writeValueAsString(catTypeSet));
				} catch (JsonProcessingException e) {
					
					e.printStackTrace();
				}

				modelAndView.addObject("myUser", user);
				modelAndView.addObject("blog", blogBo);
				modelAndView.addObject("currentPage", "blog");
				modelAndView.setViewName("blog/memberBlogs/new.jsp");
			} else {
				Blog newBlog = blogService.loadBlog(blog, user);
				modelAndView.addObject("blog", newBlog);
				modelAndView.setViewName("redirect:blogList");
			}
			return modelAndView;
		} catch (Exception exception) {
			exception.printStackTrace();
			modelAndView.setViewName("redirect:newBlog");
			return modelAndView;

		}

	}

	/*
	 * Blog service for member ends
	 */

	@RequestMapping(value = "member/blogList")
	public ModelAndView memberBlogList(
			HttpSession session,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag)
			throws FixitException {
		User user = (User) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup","Yes");
				getNotificationCount(mav,user.getUserId());
			}else{
				mav.addObject("isGroup","No");
				mav.addObject("groupId", -1);
			}
		int startIndex;
		if (pageNo == 0) {
			startIndex = 0;
		} else {
			if (flag.equals("left")) {
				startIndex = (pageNo - 2) * DbConstants.PAGE_SIZE;
			} else {
				if (flag.equals("right")) {
					startIndex = (pageNo) * DbConstants.PAGE_SIZE;
				} else {
					startIndex = (pageNo - 1) * DbConstants.PAGE_SIZE;
				}
			}
		}
		List<Blog> blogListCount = blogService.findAllBlogByUserId(
				user.getUserId(), -1, -1);

		int totalPage = (int) Math.ceil((float) blogListCount.size()
				/ DbConstants.PAGE_SIZE);

		blogService.blogPagination(pageNo, flag, mav, totalPage);
		List<Blog> blogList = blogService.findAllBlogByUserId(user.getUserId(),
				startIndex, DbConstants.PAGE_SIZE);
		Iterator<Blog> itr = blogList.iterator();
		Map<Integer, Object> mapCategories = new HashMap<Integer, Object>();
		while (itr.hasNext()) {
			int blogId = itr.next().getBlogId();
			mapCategories.put(blogId, blogService.categoriesByBlogId(blogId));
		}
		mav.addObject("blogCategories", mapCategories);
		
		mav.addObject("blogListSize", blogList.size());
		mav.addObject("totalPage", totalPage);
		mav.addObject("myUser", user);
		mav.addObject("blogList", blogList);
		mav.addObject("currentPage", "blog");
		mav.addObject("createLink", "Yes");
		mav.setViewName("blog/memberBlogs/listAllBlog.jsp");
		return mav;
	}

	@RequestMapping(value = "member/showBlog", method = RequestMethod.GET)
	public ModelAndView showBlog(@RequestParam("blogId") int blogId,
			HttpSession session) throws FixitException {
		ModelAndView mav = new ModelAndView();
		Blog blog = blogService.findAllBlogByBlogId(blogId);
		User user = (User) session.getAttribute("user");
		List<BlogCategory> blogCategories = blogService
				.categoriesByBlogId(blogId);
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup","Yes");
				getNotificationCount(mav,user.getUserId());
			}else{
				mav.addObject("isGroup","No");
				mav.addObject("groupId", -1);
			}
		mav.addObject("blogCategories", blogCategories);
		mav.addObject("blogById", blog);
		mav.addObject("myUser", user);
		mav.addObject("currentPage", "blog");
		mav.addObject("createLink", "Yes");
		mav.setViewName("blog/memberBlogs/displayblog.jsp");
		return mav;
	}

	@RequestMapping(value = "member/publishBlog", method = RequestMethod.GET)
	public ModelAndView publishBlog(@RequestParam("blogId") int blogId,
			HttpSession session) throws FixitException {

		User user = (User) session.getAttribute("user");

		ModelAndView modelAndView = new ModelAndView();
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				modelAndView.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				modelAndView.addObject("isGroup","Yes");
				getNotificationCount(modelAndView,user.getUserId());
			}else{
				modelAndView.addObject("isGroup","No");
				modelAndView.addObject("groupId", -1);
			}
		BlogBo blogBo = new BlogBo();
		Blog blogList = blogService.findAllBlogByBlogId(blogId);
		Set<BlogCategory> blogCategorieSet = blogList.getBlogCategory();
		Iterator<BlogCategory> iterator = blogCategorieSet.iterator();
		Set<CategoryType> categoryTypeSet = new LinkedHashSet<CategoryType>();
		while (iterator.hasNext()) {
			BlogCategory blogCategory = iterator.next();
			categoryTypeSet.add(blogCategory.getCategoryType());
		}
		ObjectMapper objectMapper = new ObjectMapper();
		Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
		modelAndView.addObject("parentCategory", categoryTypeService
				.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
		try {
			modelAndView
					.addObject(
							"allCategory",
							objectMapper.writeValueAsString(categoryTypeService
									.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));

			modelAndView.addObject("catTypeSet",
					objectMapper.writeValueAsString(categoryTypeSet));
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		modelAndView.addObject("myUser", user);
		modelAndView.addObject("blogList", blogList);
		modelAndView.addObject("blog", blogBo);
		modelAndView.addObject("blogBo", blogBo);
		modelAndView.addObject("currentPage", "blog");
		modelAndView.addObject("createLink", "Yes");
		modelAndView.setViewName("blog/memberBlogs/edit.jsp");
		return modelAndView;
	}

	@RequestMapping(value = "member/updateBlog", method = RequestMethod.POST)
	public ModelAndView updateBlog(
			@Valid @ModelAttribute("blogBo") BlogBo blogBo,
			BindingResult result, HttpServletRequest request,
			HttpSession session) throws FixitException {
		User user=(User) session.getAttribute("user");
		
		int blogId = Integer.parseInt(request.getParameter("id"));
		ModelAndView modelAndView = new ModelAndView();
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				modelAndView.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				modelAndView.addObject("isGroup","Yes");
				getNotificationCount(modelAndView,user.getUserId());
			}else{
				modelAndView.addObject("isGroup","No");
				modelAndView.addObject("groupId", -1);
			}
		if (result.hasErrors()) {
			Blog blogList = blogService.findAllBlogByBlogId(blogId);
			Set<BlogCategory> blogCategorieSet = blogList.getBlogCategory();
			Iterator<BlogCategory> iterator = blogCategorieSet.iterator();
			Set<CategoryType> categoryTypeSet = new LinkedHashSet<CategoryType>();
			while (iterator.hasNext()) {
				BlogCategory blogCategory = iterator.next();
				categoryTypeSet.add(blogCategory.getCategoryType());
			}
			ObjectMapper objectMapper = new ObjectMapper();
			Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
			modelAndView.addObject("parentCategory", categoryTypeService
					.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
			try {
				modelAndView
						.addObject(
								"allCategory",
								objectMapper.writeValueAsString(categoryTypeService
										.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));

				modelAndView.addObject("catTypeSet",
						objectMapper.writeValueAsString(categoryTypeSet));
			} catch (JsonProcessingException e) {
			
				e.printStackTrace();
			}

			modelAndView.addObject("blogList", blogList);
			modelAndView.addObject("blog", blogBo);
			modelAndView.addObject("currentPage", "blog");
			modelAndView.addObject("createLink", "Yes");
			modelAndView.setViewName("blog/memberBlogs/edit.jsp");

		} else {
			Blog blog = blogService.updateBlog(blogBo, blogId);
			modelAndView.setViewName("redirect:blogList");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "member/deleteBlog", method = RequestMethod.GET)
	public ModelAndView deleteMemberBlog(@RequestParam int blogId) {
		ModelAndView mav = new ModelAndView();
		int success = 0;
		success = blogService.deleteBlogAndBlogCategoryByBlogId(blogId);
		mav.addObject("deleted", success);
		mav.setViewName("redirect:/member/blogList");
		return mav;
	}
	
	@RequestMapping(value = "member/blogsByCategories")
	public ModelAndView blogsByCategoryIdListMember(
			HttpSession session,
			@RequestParam int catId,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag)
			throws FixitException {
		User user = (User) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup","Yes");
				getNotificationCount(mav,user.getUserId());
			}else{
				mav.addObject("isGroup","No");
				mav.addObject("groupId", -1);
			}
		int startIndex;
		if (pageNo == 0) {
			startIndex = 0;
		} else {
			if (flag.equals("left")) {
				startIndex = (pageNo - 2) * DbConstants.PAGE_SIZE;
			} else {
				if (flag.equals("right")) {
					startIndex = (pageNo) * DbConstants.PAGE_SIZE;
				} else {
					startIndex = (pageNo - 1) * DbConstants.PAGE_SIZE;
				}
			}
		}
		List<Blog> blogListCount = blogService.blogsByUserIdCatId(
				user.getUserId(), catId, -1, -1);

		int totalPage = (int) Math.ceil((float) blogListCount.size()
				/ DbConstants.PAGE_SIZE);

		blogService.blogPagination(pageNo, flag, mav, totalPage);
		List<Blog> blogList = blogService.blogsByUserIdCatId(user.getUserId(),
				catId, startIndex, DbConstants.PAGE_SIZE);
		Iterator<Blog> itr = blogList.iterator();
		Map<Integer, Object> mapCategories = new HashMap<Integer, Object>();
		while (itr.hasNext()) {
			int blogId = itr.next().getBlogId();
			mapCategories.put(blogId, blogService.categoriesByBlogId(blogId));
		}
		mav.addObject("blogCategories", mapCategories);
	
		mav.addObject("blogListSize", blogList.size());
		mav.addObject("totalPage", totalPage);
		mav.addObject("myUser", user);
		mav.addObject("blogList", blogList);
		mav.addObject("currentPage", "blog");
		mav.addObject("createLink", "Yes");
		mav.setViewName("blog/memberBlogs/listAllBlog.jsp");
		return mav;
	}

	@RequestMapping(value = "/member/uploadBlogFile", method = RequestMethod.POST)
	@ResponseBody
	public String uploadBlogFile(HttpSession session,
			@RequestParam MultipartFile upload,
			@RequestParam String CKEditorFuncNum,
			@RequestParam String CKEditor, @RequestParam String langCode) {
		User user = (User) session.getAttribute("user");
		String filePath = null;
		String imageSource = null;
		String html = "";

		try {

			if (!upload.isEmpty()) {

				filePath = blogService.uloadBlogFile(user.getUserName(), upload);
			}
			if (filePath != null) {
				imageSource = DbConstants.AMAZON_S3_URL + DbConstants.SUFFIX
						+ filePath;

			}
			html = "<html><body><script>window.parent.CKEDITOR.tools.callFunction("
					+ CKEditorFuncNum
					+ ", \""
					+ imageSource
					+ "\", \""
					+ ""
					+ "\");</script></body></html>";

		} catch (IOException e) {
			e.printStackTrace();
		} catch (FixitException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return html;
	}

	/*
	 * fixer blog
	 */

	@RequestMapping(value = "fixer/newBlog")
	public ModelAndView newFixerBlog(HttpSession session) throws FixitException {
		ModelAndView modelAndView = new ModelAndView();
		User user = (User) session.getAttribute("user");
		BlogBo blogBo = new BlogBo();
		ObjectMapper objectMapper = new ObjectMapper();
		Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
		modelAndView.addObject("parentCategory", categoryTypeService
				.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
		try {
			List<Integer> userIds=new LinkedList<Integer>();
			userIds.add(user.getUserId());
				ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
				if(chatUserGroup!=null){
					modelAndView.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
					modelAndView.addObject("isGroup","Yes");
					getNotificationCount(modelAndView,user.getUserId());
				}else{
					modelAndView.addObject("isGroup","No");
					modelAndView.addObject("groupId", -1);
				}
			modelAndView
					.addObject(
							"allCategory",
							objectMapper.writeValueAsString(categoryTypeService
									.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));
			modelAndView.addObject("catTypeSet",
					objectMapper.writeValueAsString(catTypeSet));
		} catch (JsonProcessingException e) {
		
			e.printStackTrace();
		}

		modelAndView.addObject("myUser", user);
		modelAndView.addObject("blog", blogBo);
		modelAndView.addObject("blogBo", blogBo);
		modelAndView.addObject("currentPage", "blog");
		modelAndView.setViewName("blog/fixerBlogs/new.jsp");
		return modelAndView;
	}

	@RequestMapping(value = "fixer/createBlog", method = RequestMethod.POST)
	public ModelAndView createFixerBlog(
			@Valid @ModelAttribute("blogBo") BlogBo blogBo,
			BindingResult result, HttpSession session,
			HttpServletRequest request) {

		
		ModelAndView modelAndView = new ModelAndView();
		User user = (User) session.getAttribute("user");
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup = null;
			try {
				chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			} catch (FixitException e1) {
				
				e1.printStackTrace();
			}
			if(chatUserGroup!=null){
				modelAndView.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				modelAndView.addObject("isGroup","Yes");
				getNotificationCount(modelAndView,user.getUserId());
			}else{
				modelAndView.addObject("isGroup","No");
				modelAndView.addObject("groupId", -1);
				
			}
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			if (result.hasErrors()) {
				Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
				modelAndView.addObject("parentCategory", categoryTypeService
						.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
				try {
					modelAndView
							.addObject(
									"allCategory",
									objectMapper
											.writeValueAsString(categoryTypeService
													.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));
					modelAndView.addObject("catTypeSet",
							objectMapper.writeValueAsString(catTypeSet));
				} catch (JsonProcessingException e) {
					
					e.printStackTrace();
				}

				modelAndView.addObject("myUser", user);
				modelAndView.addObject("blog", blogBo);
				modelAndView.addObject("currentPage", "blog");
				modelAndView.setViewName("blog/fixerBlogs/new.jsp");

			} else {
				Blog blog = blogService.loadBlog(blogBo, user);
				modelAndView.addObject("myUser", user);
				modelAndView.addObject("blog", blog);
				modelAndView.setViewName("redirect:blogList");
			}
			return modelAndView;
		} catch (Exception exception) {
			exception.printStackTrace();
			modelAndView.setViewName("redirect:newBlog");
			return modelAndView;

		}

	}

	@RequestMapping(value = "fixer/blogList")
	public ModelAndView fixerBlogList(
			HttpSession session,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag)
			throws FixitException {
		Integer totalPage = -1;
		Integer startPage = null, endPage = null, currentPageNo = null;

		User user = (User) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup","Yes");
				getNotificationCount(mav,user.getUserId());
			}else{
				mav.addObject("isGroup","No");
				mav.addObject("groupId", -1);
			}
		int startIndex;
		if (pageNo == 0) {
			startIndex = 0;
		} else {
			if (flag.equals("left")) {
				startIndex = (pageNo - 2) * DbConstants.PAGE_SIZE;
			} else {
				if (flag.equals("right")) {
					startIndex = (pageNo) * DbConstants.PAGE_SIZE;
				} else {
					startIndex = (pageNo - 1) * DbConstants.PAGE_SIZE;
				}
			}
		}
		List<Blog> blogListCount = blogService.findAllBlogByUserId(
				user.getUserId(), -1, -1);

		totalPage = (int) Math.ceil((float) blogListCount.size()
				/ DbConstants.PAGE_SIZE);

		blogService.blogPagination(pageNo, flag, mav, totalPage);
		List<Blog> blogList = blogService.findAllBlogByUserId(user.getUserId(),
				startIndex, DbConstants.PAGE_SIZE);
		Iterator<Blog> itr = blogList.iterator();
		Map<Integer, Object> mapCategories = new HashMap<Integer, Object>();
		while (itr.hasNext()) {
			int blogId = itr.next().getBlogId();
			mapCategories.put(blogId, blogService.categoriesByBlogId(blogId));
		}
		mav.addObject("blogCategories", mapCategories);
	
		mav.addObject("totalPage", totalPage);
		mav.addObject("myUser", user);
		mav.addObject("blogList", blogList);
		mav.addObject("blogListSize", blogList.size());
		mav.addObject("currentPage", "blog");
		mav.addObject("createLink", "Yes");
		mav.setViewName("blog/fixerBlogs/listAllBlog.jsp");
		return mav;
	}

	@RequestMapping(value = "fixer/showBlog", method = RequestMethod.GET)
	public ModelAndView showFixerBlog(@RequestParam("blogId") int blogId,
			HttpSession session) throws FixitException {
		ModelAndView mav = new ModelAndView();
		Blog blog = blogService.findAllBlogByBlogId(blogId);
		User user = (User) session.getAttribute("user");
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup","Yes");
				getNotificationCount(mav,user.getUserId());
			}else{
				mav.addObject("isGroup","No");
				mav.addObject("groupId", -1);
			}
		List<BlogCategory> blogCategories = blogService
				.categoriesByBlogId(blogId);

		mav.addObject("blogCategories", blogCategories);
		mav.addObject("blogById", blog);
		mav.addObject("myUser", user);
		mav.addObject("currentPage", "blog");
		mav.setViewName("blog/fixerBlogs/displayblog.jsp");
		return mav;
	}

	@RequestMapping(value = "fixer/publishBlog", method = RequestMethod.GET)
	public ModelAndView publishFixerBlog(@RequestParam("blogId") int blogId,
			HttpSession session) throws FixitException {
		User user = (User) session.getAttribute("user");
		ModelAndView modelAndView = new ModelAndView();
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				modelAndView.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				modelAndView.addObject("isGroup","Yes");
				getNotificationCount(modelAndView,user.getUserId());
			}else{
				modelAndView.addObject("isGroup","No");
				modelAndView.addObject("groupId", -1);
			}
		BlogBo blogBo = new BlogBo();
		Blog blogList = blogService.findAllBlogByBlogId(blogId);
		Set<BlogCategory> blogCategorieSet = blogList.getBlogCategory();
		Iterator<BlogCategory> iterator = blogCategorieSet.iterator();
		Set<CategoryType> categoryTypeSet = new LinkedHashSet<CategoryType>();
		while (iterator.hasNext()) {
			BlogCategory blogCategory = iterator.next();
			categoryTypeSet.add(blogCategory.getCategoryType());
		}
		ObjectMapper objectMapper = new ObjectMapper();
		Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
		modelAndView.addObject("parentCategory", categoryTypeService
				.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
		try {
			modelAndView
					.addObject(
							"allCategory",
							objectMapper.writeValueAsString(categoryTypeService
									.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));

			modelAndView.addObject("catTypeSet",
					objectMapper.writeValueAsString(categoryTypeSet));
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		modelAndView.addObject("myUser", user);
		modelAndView.addObject("blogList", blogList);
		modelAndView.addObject("blog", blogBo);
		modelAndView.addObject("currentPage", "blog");
		modelAndView.addObject("createLink", "Yes");
		modelAndView.addObject("blogBo", blogBo);
		modelAndView.setViewName("blog/fixerBlogs/edit.jsp");
		return modelAndView;
	}

	@RequestMapping(value = "fixer/updateBlog", method = RequestMethod.POST)
	public ModelAndView updateFixerBlog(
			@Valid @ModelAttribute("blogBo") BlogBo blogBo,
			BindingResult result, HttpServletRequest request,
			HttpSession session) throws FixitException {
		int blogId = Integer.parseInt(request.getParameter("id"));
		ModelAndView modelAndView = new ModelAndView();
		User user = (User) session.getAttribute("user");
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				modelAndView.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				modelAndView.addObject("isGroup","Yes");
				getNotificationCount(modelAndView,user.getUserId());
			}else{
				modelAndView.addObject("isGroup","No");
				modelAndView.addObject("groupId", -1);
			}
		if (result.hasErrors()) {
			Blog blogList = blogService.findAllBlogByBlogId(blogId);
			Set<BlogCategory> blogCategorieSet = blogList.getBlogCategory();
			Iterator<BlogCategory> iterator = blogCategorieSet.iterator();
			Set<CategoryType> categoryTypeSet = new LinkedHashSet<CategoryType>();
			while (iterator.hasNext()) {
				BlogCategory blogCategory = iterator.next();
				categoryTypeSet.add(blogCategory.getCategoryType());
			}
			ObjectMapper objectMapper = new ObjectMapper();
			Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
			modelAndView.addObject("parentCategory", categoryTypeService
					.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
			try {
				modelAndView
						.addObject(
								"allCategory",
								objectMapper.writeValueAsString(categoryTypeService
										.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));

				modelAndView.addObject("catTypeSet",
						objectMapper.writeValueAsString(categoryTypeSet));
			} catch (JsonProcessingException e) {
				
				e.printStackTrace();
			}
			modelAndView.addObject("blogList", blogList);
			modelAndView.addObject("blog", blogBo);
			modelAndView.addObject("currentPage", "blog");
			modelAndView.addObject("createLink", "Yes");
			modelAndView.setViewName("blog/fixerBlogs/edit.jsp");
		} else {

			Blog blog = blogService.updateBlog(blogBo, blogId);
			
			modelAndView.setViewName("redirect:blogList");
		}
		return modelAndView;
	}

	@RequestMapping(value = "fixer/deleteBlog", method = RequestMethod.GET)
	public ModelAndView deleteFixerBlog(@RequestParam int blogId) {
		ModelAndView mav = new ModelAndView();
		
		int success = 0;
		success = blogService.deleteBlogAndBlogCategoryByBlogId(blogId);
		mav.addObject("deleted", success);
		mav.setViewName("redirect:/fixer/blogList");
		return mav;
	}

	@RequestMapping(value = "fixer/blogsByCategories")
	public ModelAndView blogsByCategoryIdListFixer(
			HttpSession session,
			@RequestParam int catId,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag)
			throws FixitException {
		User user = (User) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup","Yes");
				getNotificationCount(mav,user.getUserId());
			}else{
				mav.addObject("isGroup","No");
				mav.addObject("groupId", -1);
			}
		int startIndex;
		if (pageNo == 0) {
			startIndex = 0;
		} else {
			if (flag.equals("left")) {
				startIndex = (pageNo - 2) * DbConstants.PAGE_SIZE;
			} else {
				if (flag.equals("right")) {
					startIndex = (pageNo) * DbConstants.PAGE_SIZE;
				} else {
					startIndex = (pageNo - 1) * DbConstants.PAGE_SIZE;
				}
			}
		}
		List<Blog> blogListCount = blogService.blogsByUserIdCatId(
				user.getUserId(), catId, -1, -1);

		int totalPage = (int) Math.ceil((float) blogListCount.size()
				/ DbConstants.PAGE_SIZE);

		blogService.blogPagination(pageNo, flag, mav, totalPage);
		List<Blog> blogList = blogService.blogsByUserIdCatId(user.getUserId(),
				catId, startIndex, DbConstants.PAGE_SIZE);
		Iterator<Blog> itr = blogList.iterator();
		Map<Integer, Object> mapCategories = new HashMap<Integer, Object>();
		while (itr.hasNext()) {
			int blogId = itr.next().getBlogId();
			mapCategories.put(blogId, blogService.categoriesByBlogId(blogId));
		}
		mav.addObject("blogCategories", mapCategories);
		
		mav.addObject("blogListSize", blogList.size());
		mav.addObject("totalPage", totalPage);
		mav.addObject("myUser", user);
		mav.addObject("blogList", blogList);
		mav.addObject("currentPage", "blog");
		mav.addObject("createLink", "Yes");
		mav.setViewName("blog/fixerBlogs/listAllBlog.jsp");
		return mav;
	}

	@RequestMapping(value = "/fixer/uploadBlogFile", method = RequestMethod.POST)
	@ResponseBody
	public String uploadBlogFileFixer(HttpSession session,
			@RequestParam MultipartFile upload,
			@RequestParam String CKEditorFuncNum,
			@RequestParam String CKEditor, @RequestParam String langCode) {
		User user = (User) session.getAttribute("user");
		String filePath = null;
		String imageSource = null;
		String html = "";

		try {

			if (!upload.isEmpty()) {

				filePath = blogService.uloadBlogFile(user.getUserName(), upload);
			}
			if (filePath != null) {
				imageSource = DbConstants.AMAZON_S3_URL + DbConstants.SUFFIX
						+ filePath;

			}

			html = "<html><body><script>window.parent.CKEDITOR.tools.callFunction("
					+ CKEditorFuncNum
					+ ", \""
					+ imageSource
					+ "\", \""
					+ ""
					+ "\");</script></body></html>";

		} catch (IOException e) {
			e.printStackTrace();
		} catch (FixitException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return html;
	}
	@RequestMapping(value = "/uploadBlogDropFile", method = RequestMethod.POST)
	@ResponseBody
	public String uploadBlogFileDragDropFixer(HttpSession session,
			@RequestParam MultipartFile upload) {
		User user = (User) session.getAttribute("user");
		String filePath = null;
		String imageSource = null;
	try {

			if (!upload.isEmpty()) {

				filePath = blogService.uloadBlogFile(user.getUserName(), upload);
			}
			if (filePath != null) {
				imageSource = DbConstants.AMAZON_S3_URL + DbConstants.SUFFIX
						+ filePath;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FixitException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return imageSource;
	}
	@RequestMapping(value = "blogList")
	public ModelAndView blogList(
			HttpSession session,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag)
			throws FixitException {
		User user = (User) session.getAttribute("user");
		int userId=-1;
		if(user!=null){
			userId=user.getUserId();
		}
		ModelAndView mav = new ModelAndView();
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(userId);
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup","Yes");
				getNotificationCount(mav,user.getUserId());
			}else{
				mav.addObject("isGroup","No");
				mav.addObject("groupId", -1);
			}
		int startIndex;
		if (pageNo == 0) {
			startIndex = 0;
		} else {
			if (flag.equals("left")) {
				startIndex = (pageNo - 2) * DbConstants.PAGE_SIZE;
			} else {
				if (flag.equals("right")) {
					startIndex = (pageNo) * DbConstants.PAGE_SIZE;
				} else {
					startIndex = (pageNo - 1) * DbConstants.PAGE_SIZE;
				}
			}
		}
		List<Blog> blogListCount = blogService.findBlogDataByStatus(
				"Published", -1, -1);

		int totalPage = (int) Math.ceil((float) blogListCount.size()
				/ DbConstants.PAGE_SIZE);

		blogService.blogPagination(pageNo, flag, mav, totalPage);
		List<Blog> blogList = blogService.findBlogDataByStatus("Published",
				startIndex, DbConstants.PAGE_SIZE);
		Iterator<Blog> itr = blogList.iterator();
		Map<Integer, Object> mapCategories = new HashMap<Integer, Object>();
		while (itr.hasNext()) {
			int blogId = itr.next().getBlogId();
			mapCategories.put(blogId, blogService.categoriesByBlogId(blogId));
		}
		

		mav.addObject("totalPage", totalPage);
		mav.addObject("blogCategories", mapCategories);
		mav.addObject("blogList", blogList);
		mav.addObject("blogListSize", blogList.size());
		mav.addObject("currentPage", "blog");
		mav.addObject("blog", "active");
		mav.setViewName("blog/listAllBlog.jsp");
		return mav;
	}

	@RequestMapping(value = "blogListByCategories")
	public ModelAndView blogListByCategories(
			HttpSession session,
			@RequestParam int catId,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag)
			throws FixitException {
		User user = (User) session.getAttribute("user");
		int userId=-1;
		if(user !=null){
			userId=user.getUserId();
		}
		ModelAndView mav = new ModelAndView();
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(userId);
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup","Yes");
				getNotificationCount(mav,user.getUserId());
			}else{
				mav.addObject("isGroup","No");
				mav.addObject("groupId", -1);
			}
		int startIndex;
		if (pageNo == 0) {
			startIndex = 0;
		} else {
			if (flag.equals("left")) {
				startIndex = (pageNo - 2) * DbConstants.PAGE_SIZE;
			} else {
				if (flag.equals("right")) {
					startIndex = (pageNo) * DbConstants.PAGE_SIZE;
				} else {
					startIndex = (pageNo - 1) * DbConstants.PAGE_SIZE;
				}
			}
		}
		List<Blog> blogListCount = blogService.findBlogByStatusAndCategories(
				catId, "Published", -1, -1);

		int totalPage = (int) Math.ceil((float) blogListCount.size()
				/ DbConstants.PAGE_SIZE);

		blogService.blogPagination(pageNo, flag, mav, totalPage);
		List<Blog> blogList = blogService.findBlogByStatusAndCategories(catId,
				"Published", startIndex, DbConstants.PAGE_SIZE);
		Iterator<Blog> itr = blogList.iterator();
		Map<Integer, Object> mapCategories = new HashMap<Integer, Object>();
		while (itr.hasNext()) {
			int blogId = itr.next().getBlogId();
			mapCategories.put(blogId, blogService.categoriesByBlogId(blogId));
		}
		

		mav.addObject("totalPage", totalPage);
		mav.addObject("blogCategories", mapCategories);
		mav.addObject("blogList", blogList);
		mav.addObject("blogListSize", blogList.size());
		mav.addObject("currentPage", "blog");
		mav.addObject("blog", "active");
		mav.setViewName("blog/listAllBlog.jsp");
		return mav;
	}

	@RequestMapping(value = "showBlog", method = RequestMethod.GET)
	public ModelAndView showBlogWitoutLogin(@RequestParam("blogId") int blogId,HttpSession session)
			throws FixitException {
		User user=(User) session.getAttribute("user");
		int userId=-1;
		if(user!=null){
			userId=user.getUserId();
		}
		ModelAndView mav = new ModelAndView();
		Blog blog = blogService.findAllBlogByBlogId(blogId);
		List<BlogCategory> blogCategories = blogService
				.categoriesByBlogId(blogId);
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(userId);
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup","Yes");
				getNotificationCount(mav,user.getUserId());
			}else{
				mav.addObject("isGroup","No");
				mav.addObject("groupId", -1);
			}
		mav.addObject("blogCategories", blogCategories);
		mav.addObject("blogById", blog);
		mav.addObject("blog", "active");
		mav.setViewName("blog/displayblog.jsp");
		return mav;
	}

	@RequestMapping(value = "/admin/blogList", method = RequestMethod.GET)
	public ModelAndView adminBlogList(
			@RequestParam("status") String status,
			HttpSession session,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag)
			throws FixitException {
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("user");
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup","Yes");
				getNotificationCount(mav,user.getUserId());
			}else{
				mav.addObject("isGroup","No");
				mav.addObject("groupId", -1);
			}
		int startIndex;
		if (pageNo == 0) {
			startIndex = 0;
		} else {
			if (flag.equals("left")) {
				startIndex = (pageNo - 2) * DbConstants.PAGE_SIZE;
			} else {
				if (flag.equals("right")) {
					startIndex = (pageNo) * DbConstants.PAGE_SIZE;
				} else {
					startIndex = (pageNo - 1) * DbConstants.PAGE_SIZE;
				}
			}
		}
		if (status.equals("draft")) {
			List<Blog> blogListCount = blogService.findBlogByStatusAndUserId(
					status, user.getUserId(), -1, -1);

			int totalPage = (int) Math.ceil((float) blogListCount.size()
					/ DbConstants.PAGE_SIZE);

			blogService.blogPagination(pageNo, flag, mav, totalPage);
			List<Blog> blogList = blogService.findBlogByStatusAndUserId(status,
					user.getUserId(), startIndex, DbConstants.PAGE_SIZE);
			Iterator<Blog> itr = blogList.iterator();
			Map<Integer, Object> mapCategories = new HashMap<Integer, Object>();
			while (itr.hasNext()) {
				int blogId = itr.next().getBlogId();
				mapCategories.put(blogId,
						blogService.categoriesByBlogId(blogId));
			}
			mav.addObject("blogCategories", mapCategories);
			
			mav.addObject("blogList", blogList);
			mav.addObject("totalPage", totalPage);
		} else {
			List<Blog> blogListCount = blogService.findBlogDataByStatus(status,
					-1, -1);

			int totalPage = (int) Math.ceil((float) blogListCount.size()
					/ DbConstants.PAGE_SIZE);

			blogService.blogPagination(pageNo, flag, mav, totalPage);
			List<Blog> blogList = blogService.findBlogDataByStatus(status,
					startIndex, DbConstants.PAGE_SIZE);
			Iterator<Blog> itr = blogList.iterator();
			Map<Integer, Object> mapCategories = new HashMap<Integer, Object>();
			while (itr.hasNext()) {
				int blogId = itr.next().getBlogId();
				mapCategories.put(blogId,
						blogService.categoriesByBlogId(blogId));
			}
			mav.addObject("blogCategories", mapCategories);
			
			mav.addObject("blogList", blogList);
			mav.addObject("blogListSize", blogList.size());
			mav.addObject("totalPage", totalPage);

		}

		mav.addObject("status", status);
		mav.addObject("createLink", "Yes");
		mav.addObject("currentPage", "blog");
		mav.setViewName("blog/admin/listAllBlog.jsp");
		return mav;
	}

	@RequestMapping(value = "/admin/updateStatus")
	public ModelAndView updateSatus(@RequestParam("status") String status,
			@RequestParam("id") int id, HttpServletRequest request,HttpSession session)
			throws FixitException {
		User user=(User) session.getAttribute("user");
		int result = 0;
		if (status.equals("declined")) {
			result = blogService.updateStatusWithReasonByBlogId(status,
					request.getParameter("reason"), id);
		} else {
			result = blogService.updateStatusByBlogId(status, id);
		}

		ModelAndView modelAndView = new ModelAndView();
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				modelAndView.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				modelAndView.addObject("isGroup","Yes");
				getNotificationCount(modelAndView,user.getUserId());
			}else{
				modelAndView.addObject("isGroup","No");
				modelAndView.addObject("groupId", -1);
			}
		modelAndView.addObject("status", status);
		modelAndView.setViewName("redirect:/admin/blogList");
		return modelAndView;
	}

	@RequestMapping(value = "admin/newBlog")
	public ModelAndView newAdminBlog(HttpSession session) throws FixitException {
		ModelAndView modelAndView = new ModelAndView();
		User user = (User) session.getAttribute("user");
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				modelAndView.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				modelAndView.addObject("isGroup","Yes");
				getNotificationCount(modelAndView,user.getUserId());
			}else{
				modelAndView.addObject("isGroup","No");
				modelAndView.addObject("groupId", -1);
			}
		BlogBo blogBo = new BlogBo();
		ObjectMapper objectMapper = new ObjectMapper();
		Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
		modelAndView.addObject("parentCategory", categoryTypeService
				.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
		try {
			modelAndView
					.addObject(
							"allCategory",
							objectMapper.writeValueAsString(categoryTypeService
									.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));
			modelAndView.addObject("catTypeSet",
					objectMapper.writeValueAsString(catTypeSet));
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		modelAndView.addObject("myUser", user);
		modelAndView.addObject("blog", blogBo);
		modelAndView.addObject("currentPage", "blog");
		modelAndView.addObject("blogBo", blogBo);

		modelAndView.setViewName("blog/admin/new.jsp");
		return modelAndView;
	}

	@RequestMapping(value = "admin/createBlog", method = RequestMethod.POST)
	public ModelAndView createAdminBlog(
			@Valid @ModelAttribute("blogBo") BlogBo blogBo,
			BindingResult result, HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView modelAndView = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup = null;
			try {
				chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			} catch (FixitException e1) {
			
				e1.printStackTrace();
			}
			if(chatUserGroup!=null){
				modelAndView.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				modelAndView.addObject("isGroup","Yes");
				getNotificationCount(modelAndView,user.getUserId());
			}else{
				modelAndView.addObject("isGroup","No");
				modelAndView.addObject("groupId", -1);
			}
		try {
			if (result.hasErrors()) {
				Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
				modelAndView.addObject("parentCategory", categoryTypeService
				.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
				try {
					modelAndView
							.addObject(
									"allCategory",
									objectMapper
											.writeValueAsString(categoryTypeService
													.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));
					modelAndView.addObject("catTypeSet",
							objectMapper.writeValueAsString(catTypeSet));
				} catch (JsonProcessingException e) {
					
					e.printStackTrace();
				}

				modelAndView.addObject("myUser", user);
				modelAndView.addObject("blog", blogBo);
				modelAndView.addObject("currentPage", "blog");
				modelAndView.setViewName("blog/admin/new.jsp");

			} else {
				Blog blog = blogService.loadBlog(blogBo, user);
				
				modelAndView.addObject("myUser", user);
				modelAndView.addObject("blog", blog);
				modelAndView.setViewName("redirect:blogList?status=pending");
			}
			return modelAndView;
		} catch (Exception exception) {
			exception.printStackTrace();
			modelAndView.setViewName("redirect:newBlog");
			return modelAndView;

		}

	}

	@RequestMapping(value = "admin/showBlog", method = RequestMethod.GET)
	public ModelAndView showAdminBlog(@RequestParam("blogId") int blogId,
			HttpSession session) throws FixitException {
		ModelAndView mav = new ModelAndView();
		Blog blog = blogService.findAllBlogByBlogId(blogId);
		User user = (User) session.getAttribute("user");
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup","Yes");
				getNotificationCount(mav,user.getUserId());
			}else{
				mav.addObject("isGroup","No");
				mav.addObject("groupId", -1);
			}
		List<BlogCategory> blogCategories = blogService
				.categoriesByBlogId(blogId);

		mav.addObject("blogCategories", blogCategories);
		mav.addObject("blogById", blog);
		mav.addObject("myUser", blog.getUser());
		mav.addObject("userName", user.getFirstName());
		mav.addObject("currentPage", "blog");
		mav.addObject("createLink", "Yes");
		mav.setViewName("blog/admin/displayblog.jsp");
		return mav;
	}

	@RequestMapping(value = "admin/publishBlog", method = RequestMethod.GET)
	public ModelAndView publishAdminBlog(@RequestParam("blogId") int blogId,
			HttpSession session) throws FixitException {
		ModelAndView modelAndView = new ModelAndView();
		User user = (User) session.getAttribute("user");
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				modelAndView.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				modelAndView.addObject("isGroup","Yes");
				getNotificationCount(modelAndView,user.getUserId());
			}else{
				modelAndView.addObject("isGroup","No");
				modelAndView.addObject("groupId", -1);
			}
		BlogBo blogBo = new BlogBo();
		Blog blogList = blogService.findAllBlogByBlogId(blogId);
		Set<BlogCategory> blogCategorieSet = blogList.getBlogCategory();
		Iterator<BlogCategory> iterator = blogCategorieSet.iterator();
		Set<CategoryType> categoryTypeSet = new LinkedHashSet<CategoryType>();
		while (iterator.hasNext()) {
			BlogCategory blogCategory = iterator.next();
			categoryTypeSet.add(blogCategory.getCategoryType());
		}
		ObjectMapper objectMapper = new ObjectMapper();
		Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
		modelAndView.addObject("parentCategory", categoryTypeService
				.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
		try {
			modelAndView
					.addObject(
							"allCategory",
							objectMapper.writeValueAsString(categoryTypeService
									.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));

			modelAndView.addObject("catTypeSet",
					objectMapper.writeValueAsString(categoryTypeSet));

		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		modelAndView.addObject("blogList", blogList);
		modelAndView.addObject("blog", blogBo);
		modelAndView.addObject("blogBo", blogBo);
		modelAndView.addObject("currentPage", "blog");
		modelAndView.addObject("createLink", "Yes");
		modelAndView.setViewName("blog/admin/edit.jsp");
		return modelAndView;
	}

	@RequestMapping(value = "admin/updateBlog", method = RequestMethod.POST)
	public ModelAndView updateAdminBlog(
			@Valid @ModelAttribute("blogBo") BlogBo blogBo,
			BindingResult result, HttpServletRequest request,
			HttpSession session) throws FixitException {
		ModelAndView modelAndView = new ModelAndView();
		User user = (User) session.getAttribute("user");
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				modelAndView.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				modelAndView.addObject("isGroup","Yes");
				getNotificationCount(modelAndView,user.getUserId());
			}else{
				modelAndView.addObject("isGroup","No");
				modelAndView.addObject("groupId", -1);
			}
		int blogId = Integer.parseInt(request.getParameter("id"));
		if (result.hasErrors()) {

			Blog blogList = blogService.findAllBlogByBlogId(blogId);
			Set<BlogCategory> blogCategorieSet = blogList.getBlogCategory();
			Iterator<BlogCategory> iterator = blogCategorieSet.iterator();
			Set<CategoryType> categoryTypeSet = new LinkedHashSet<CategoryType>();
			while (iterator.hasNext()) {
				BlogCategory blogCategory = iterator.next();
				categoryTypeSet.add(blogCategory.getCategoryType());
			}
			ObjectMapper objectMapper = new ObjectMapper();
			Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
			modelAndView.addObject("parentCategory", categoryTypeService
					.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
			try {
				modelAndView
						.addObject(
								"allCategory",
								objectMapper.writeValueAsString(categoryTypeService
										.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));

				modelAndView.addObject("catTypeSet",
						objectMapper.writeValueAsString(categoryTypeSet));
			} catch (JsonProcessingException e) {
				
				e.printStackTrace();
			}
			modelAndView.addObject("blogList", blogList);

			modelAndView.addObject("currentPage", "blog");
			modelAndView.addObject("createLink", "Yes");
			modelAndView.setViewName("blog/admin/edit.jsp");
		} else {

			Blog blog = blogService.updateBlog(blogBo, blogId);
			String status = "";
			if (blogBo.getButton().equals("Save")) {
				status = "draft";
			} else {
				status = "pending";
			}
			modelAndView.setViewName("redirect:blogList?status=" + status);
		}
		return modelAndView;
	}

	@RequestMapping(value = "admin/deleteBlog", method = RequestMethod.GET)
	public ModelAndView deleteAdminBlog(@RequestParam int blogId,
			@RequestParam String status,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user=(User) session.getAttribute("user");
		int success = 0;
		success = blogService.deleteBlogAndBlogCategoryByBlogId(blogId);
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup = null;
			try {
				chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			} catch (FixitException e) {
				
				e.printStackTrace();
			}
			if(chatUserGroup!=null){
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup","Yes");
				getNotificationCount(mav,user.getUserId());
			}else{
				mav.addObject("isGroup","No");
				mav.addObject("groupId", -1);
			}
		mav.addObject("deleted", success);
		mav.addObject("status", status);
		mav.setViewName("redirect:/admin/blogList");
		return mav;
	}


	

	@RequestMapping(value = "/admin/uploadBlogFile", method = RequestMethod.POST)
	@ResponseBody
	public String uploadAdminBlogFile(HttpSession session,
			@RequestParam MultipartFile upload,
			@RequestParam String CKEditorFuncNum,
			@RequestParam String CKEditor, @RequestParam String langCode) {
		User user = (User) session.getAttribute("user");
		String filePath = null;
		String imageSource = null;
		String html = "";

		try {

			if (!upload.isEmpty()) {

				filePath = blogService.uloadBlogFile(user.getUserName(), upload);
			}
			if (filePath != null) {
				imageSource = DbConstants.AMAZON_S3_URL + DbConstants.SUFFIX
						+ filePath;

			}
			html = "<html><body><script>window.parent.CKEDITOR.tools.callFunction("
					+ CKEditorFuncNum
					+ ", \""
					+ imageSource
					+ "\", \""
					+ ""
					+ "\");</script></body></html>";

		} catch (IOException e) {
			e.printStackTrace();
		} catch (FixitException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return html;
	}

	@RequestMapping(value = "/member/uploadBlogFilePath", method = RequestMethod.POST)
	@ResponseBody
	public List<String> uploadBlogImage(@RequestParam MultipartFile upload,
			@RequestParam String CKEditorFuncNum,
			@RequestParam String CKEditor, @RequestParam String langCode) {
		String filePath = null;
		String imageSource = null;
		String html = "";
		List<String> list = new ArrayList<String>();
		if (!upload.isEmpty()) {
			filePath = upload.getOriginalFilename();
		}
		
		String url = "https://s3-us-west-2.amazonaws.com/erpfixersdocument/blogfiles/Screenshot (3).png";

		html = "<html><body><script>window.parent.CKEDITOR.tools.callFunction("
				+ CKEditorFuncNum + ", \"" + url + "\", \"" + ""
				+ "\");</script></body></html>";
		
		list.add(html);
		return list;
	}



	@RequestMapping(value = "admin/blogsByCategories", method = RequestMethod.GET)
	public ModelAndView adminblogsByCategories(
			@RequestParam("status") String status,
			@RequestParam("catId") int catId,
			HttpSession session,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag)
			throws FixitException {
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("user");
		List<Integer> userIds=new LinkedList<Integer>();
		userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup=	chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if(chatUserGroup!=null){
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup","Yes");
				getNotificationCount(mav,user.getUserId());
			}else{
				mav.addObject("isGroup","No");
				mav.addObject("groupId", -1);
			}
		int startIndex;
		if (pageNo == 0) {
			startIndex = 0;
		} else {
			if (flag.equals("left")) {
				startIndex = (pageNo - 2) * DbConstants.PAGE_SIZE;
			} else {
				if (flag.equals("right")) {
					startIndex = (pageNo) * DbConstants.PAGE_SIZE;
				} else {
					startIndex = (pageNo - 1) * DbConstants.PAGE_SIZE;
				}
			}
		}
		if (status.equals("draft")) {
			List<Blog> blogListCount = blogService
					.findBlogByStatusUserIdAndCategories(catId, status,
							user.getUserId(), -1, -1);

			int totalPage = (int) Math.ceil((float) blogListCount.size()
					/ DbConstants.PAGE_SIZE);

			blogService.blogPagination(pageNo, flag, mav, totalPage);
			List<Blog> blogList = blogService
					.findBlogByStatusUserIdAndCategories(catId, status,
							user.getUserId(), startIndex, DbConstants.PAGE_SIZE);
			Iterator<Blog> itr = blogList.iterator();
			Map<Integer, Object> mapCategories = new HashMap<Integer, Object>();
			while (itr.hasNext()) {
				int blogId = itr.next().getBlogId();
				mapCategories.put(blogId,
						blogService.categoriesByBlogId(blogId));
			}
			mav.addObject("blogCategories", mapCategories);
			
			mav.addObject("blogList", blogList);
			mav.addObject("totalPage", totalPage);
		} else {
			List<Blog> blogListCount = blogService
					.findBlogByStatusAndCategories(catId, status, -1, -1);

			int totalPage = (int) Math.ceil((float) blogListCount.size()
					/ DbConstants.PAGE_SIZE);

			blogService.blogPagination(pageNo, flag, mav, totalPage);
			List<Blog> blogList = blogService.findBlogByStatusAndCategories(
					catId, status, startIndex, DbConstants.PAGE_SIZE);
			Iterator<Blog> itr = blogList.iterator();
			Map<Integer, Object> mapCategories = new HashMap<Integer, Object>();
			while (itr.hasNext()) {
				int blogId = itr.next().getBlogId();
				mapCategories.put(blogId,
						blogService.categoriesByBlogId(blogId));
			}
			mav.addObject("blogCategories", mapCategories);
			
			mav.addObject("blogList", blogList);
			mav.addObject("blogListSize", blogList.size());
			mav.addObject("totalPage", totalPage);

		}

		mav.addObject("status", status);
		mav.addObject("createLink", "Yes");
		mav.addObject("currentPage", "blog");
		mav.setViewName("blog/admin/listAllBlog.jsp");
		return mav;
	}


	@RequestMapping(value = "member/deleteMyBlog" , method = RequestMethod.GET)
	@ResponseBody
	public Integer deleteBlogByUserId(HttpSession session){
		User user = (User) session.getAttribute("user");
		
        int deleted =  blogService.deleteBlogByUserId(user.getUserId());		
		return deleted;
	}
	
	
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {

		ModelAndView model = new ModelAndView();

		model.setViewName("redirect:/member/exception?e=" + ex);
		return model;

	}
	
	private void getNotificationCount(ModelAndView modelAndView,int userId)
	{
		long countMsg=0;
		try {
			countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
		} catch (FixitException e) {
			
			e.printStackTrace();
		}
		modelAndView.addObject("countMsg", countMsg);
	}

}
